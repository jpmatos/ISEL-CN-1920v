package serviceapp;

import CnText.*;
import com.google.api.core.ApiFuture;
import com.google.cloud.RestorableState;
import com.google.cloud.WriteChannel;
import com.google.cloud.firestore.*;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import dao.TextOfImage;
import dao.User;
import gcloud.compute.VMManagement;
import io.grpc.stub.StreamObserver;
import observer.CheckRequestObserver;
import observer.UploadRequestObserver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Operations extends CnTextGrpc.CnTextImplBase  {
    private final String PROJECT_ID = "g01-li61n";
    private final String USERS_COLLECTION_NAME = "Users";
    private final String IMAGES_BUCKET_NAME = "images-cn";
    private final String FIRESTORE_COLLECTION_NAME = "TextOfImages";
    private SessionManager sessionManager;
    private Firestore db;
    private Storage storage;

    public Operations(Firestore db, Storage storage) {
        this.sessionManager = new SessionManager();
        this.db = db;
        this.storage = storage;
    }

    @Override
    public void start(Login request, StreamObserver<CnText.Session> responseObserver) {
        System.out.println("Login called");

        //Load User from Firestore
        String usernameReq = request.getUser();
        String passwordReq = request.getPassword();

        //See if the User is valid, matches password, and if it is premium or not
        LoginStatus loginStatus;
        String username = "";
        boolean premium = false;
        try {
            DocumentReference docRef = db.collection(USERS_COLLECTION_NAME).document(usernameReq);
            DocumentSnapshot doc = docRef.get().get();
            if(!doc.exists())
                loginStatus = LoginStatus.LOGIN_UNKNOWN_USER;
            else {
                User userFound = doc.toObject(User.class);
                username = userFound.username;
                premium = userFound.premium;
                if (userFound.password.equals(passwordReq))
                    loginStatus = LoginStatus.LOGIN_SUCCESS;
                else
                    loginStatus = LoginStatus.LOGIN_WRONG_PASSWORD;
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            loginStatus = LoginStatus.LOGIN_COMMUNICATION_ERROR;
        }

        //Build response
        CnText.Session.Builder sessionBuilder = CnText.Session.newBuilder().setStatus(loginStatus).setUser(usernameReq);
        if(loginStatus == LoginStatus.LOGIN_SUCCESS) {
            String sessionID = sessionManager.newSession(username, premium);
            sessionBuilder.setSessionId(sessionID);
            VMManagement.updateVMInstances(sessionManager.getFreeSessionsCount(), sessionManager.getPremiumSessionsCount());
        }
        CnText.Session response = sessionBuilder.build();

        //Send response and call onCompleted()
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void close(CnText.Session request, StreamObserver<CloseResponse> responseObserver) {
        System.out.println("Logout Called");

        //Attempt to clear user from SessionManager
        String sessionID = request.getSessionId();
        LogoutStatus logoutStatus;
        if(sessionManager.closeSession(sessionID))
            logoutStatus = LogoutStatus.LOGOUT_SUCCESS;
        else
            logoutStatus = LogoutStatus.LOGOUT_INVALID_SESSION;

        //TODO Call VMManagement

        //Send response and call onComplete()
        responseObserver.onNext(CloseResponse.newBuilder().setStatus(logoutStatus).build());
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<UploadRequest> upload(StreamObserver<UploadRequestResponse> responseObserver) {
        System.out.println("Called upload");
        responseObserver.onNext(UploadRequestResponse.newBuilder().setStatus(UploadStatus.UPLOAD_STARTING).build());
        return new UploadRequestObserver(responseObserver, storage, sessionManager, IMAGES_BUCKET_NAME);
    }

    @Override
    public void process(ProcessRequest processRequest, StreamObserver<ProcessResponse> responseObserver) {
        System.out.println("Called Translate");
        String sessionID = processRequest.getSessionId();

        //Validate Session
        if(!sessionManager.isValid(sessionID)){
            responseObserver.onNext(ProcessResponse.newBuilder().setStatus(ProcessStatus.PROCESS_INVALID_SESSION).build());
            return;
        }

        //Load information
        String blobName = processRequest.getUploadToken();
        String language = processRequest.getLanguage();
        String topicName;
        if(sessionManager.isPremium(sessionID))
            topicName = "premium-ocr";
        else
            topicName = "free-ocr";

        //Validate if Blob exists
        BlobId blobId = BlobId.of(IMAGES_BUCKET_NAME, blobName);
        Blob blob = storage.get(blobId);
        if(!blob.exists()){
            responseObserver.onNext(ProcessResponse.newBuilder().setStatus(ProcessStatus.PROCESS_INVALID_TOKEN).build());
            responseObserver.onError(null);
            return;
        }

        //TODO Validate language

        //Publish to free/premium topic
        TopicName tName=TopicName.ofProjectTopicName(PROJECT_ID, topicName);
        try {
            Publisher publisher = Publisher.newBuilder(tName).build();

            ByteString msgData = ByteString.copyFromUtf8("placeholder-message");
            System.out.println(blobName + " " + language + " " + sessionID + " " + PROJECT_ID + " " + topicName);
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
                    .setData(msgData)
                    .putAttributes("blobName", blobName)
                    .putAttributes("language", language)
                    .putAttributes("sessionID", sessionID)
                    .build();
            ApiFuture<String> future = publisher.publish(pubsubMessage);
            String msgID = future.get();
            publisher.shutdown();

            System.out.println("Message Published with ID=" + msgID);
        } catch (IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
            responseObserver.onError(e);
            return;
        }

        //Update Status
        responseObserver.onNext(ProcessResponse.newBuilder().setStatus(ProcessStatus.READING_TEXT).build());

        //Set Firestore Listener
        DocumentReference docRef = db.collection(FIRESTORE_COLLECTION_NAME).document(blobName);
        System.out.println(blobName);
        docRef.addSnapshotListener((snapshot, e) -> {
            ProcessResponse.Builder response = ProcessResponse.newBuilder();
            
            if (e != null) {
                System.err.println("Listen failed: " + e);
                
                response.setStatus(ProcessStatus.PROCESS_ERROR);
                responseObserver.onNext(response.build());
                responseObserver.onError(e);
                return;
            }

            if (snapshot != null && snapshot.exists()) {
                System.out.println("Current data: " + snapshot.getData());

                String ocrResult = snapshot.getString("ocrResult");
                if(ocrResult != null){
                    response.setText(ocrResult);
                    if(language.equals("")) {
                        response.setStatus(ProcessStatus.READING_SUCCESS);
                        responseObserver.onNext(response.build());
                        responseObserver.onCompleted();
                        return;
                    }
                    
                    String translation = snapshot.getString("translationResult");
                    if(translation != null){
                        response.setTranslation(translation);
                        response.setStatus(ProcessStatus.TRANSLATE_SUCCESS);
                        responseObserver.onNext(response.build());
                        responseObserver.onCompleted();
                        return;
                    }
                    
                    response.setStatus(ProcessStatus.TRANSLATING_TEXT);
                    responseObserver.onNext(response.build());
                }
            } else {
                System.out.println("Current data: null");
            }
        });
    }

    @Override
    public StreamObserver<CheckRequest> check(StreamObserver<CheckResponse> responseObserver) {
        return new CheckRequestObserver(responseObserver, sessionManager, db, FIRESTORE_COLLECTION_NAME);
    }

    public List<SessionManager.Session> getActiveSessions() {
        return sessionManager.getActiveSessions();
    }
}
