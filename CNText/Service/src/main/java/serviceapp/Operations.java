package serviceapp;

import CnText.*;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import io.grpc.stub.StreamObserver;
import serviceapp.compute.VMManagement;
import serviceapp.dao.User;
import serviceapp.observer.CheckRequestObserver;
import serviceapp.observer.UploadRequestObserver;
import serviceapp.util.SessionManager;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import static utils.Output.OutputType.ERROR;

import static utils.Output.log;

public class Operations extends CnTextGrpc.CnTextImplBase {
    public static final String PROJECT_ID = "g01-li61n";
    private final String USERS_COLLECTION_NAME = "Users";
    private final String IMAGES_BUCKET_NAME = "image-store-cng01";
    private final String FIRESTORE_COLLECTION_NAME = "TextOfImages";
    private SessionManager sessionManager;
    private Firestore db;
    private Storage storage;

    public Operations(Firestore db, Storage storage, int pollingTime) {
        this.sessionManager = new SessionManager();
        this.db = db;
        this.storage = storage;
        new VMManagement(sessionManager, pollingTime).start();
    }

    @Override
    public void start(Login request, StreamObserver<CnText.Session> responseObserver) {
        log("Login called.");

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
            if (!doc.exists()) {
                log(ERROR, String.format("Unknown user '%s'.", usernameReq));
                loginStatus = LoginStatus.LOGIN_UNKNOWN_USER;
            } else {
                User userFound = doc.toObject(User.class);
                username = userFound.username;
                premium = userFound.premium;
                if (userFound.password.equals(passwordReq))
                    loginStatus = LoginStatus.LOGIN_SUCCESS;
                else {
                    log(ERROR, "Wrong password.");
                    loginStatus = LoginStatus.LOGIN_WRONG_PASSWORD;
                }
            }

        } catch (InterruptedException | ExecutionException e) {
            loginStatus = LoginStatus.LOGIN_COMMUNICATION_ERROR;
        }

        //Build response
        CnText.Session.Builder sessionBuilder = CnText.Session.newBuilder().setStatus(loginStatus).setUser(usernameReq);
        if (loginStatus == LoginStatus.LOGIN_SUCCESS) {
            String sessionID = sessionManager.newSession(username, premium);
            sessionBuilder.setSessionId(sessionID);
        }
        CnText.Session response = sessionBuilder.build();

        //Send response and call onCompleted()
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void close(CnText.Session request, StreamObserver<CloseResponse> responseObserver) {
        String sessionID = request.getSessionId();
        log(String.format("Logout called with session '%s'.", sessionID));

        //Attempt to clear user from SessionManager
        LogoutStatus logoutStatus;
        if (sessionManager.closeSession(sessionID))
            logoutStatus = LogoutStatus.LOGOUT_SUCCESS;
        else
            logoutStatus = LogoutStatus.LOGOUT_INVALID_SESSION;

        //Send response and call onComplete()
        responseObserver.onNext(CloseResponse.newBuilder().setStatus(logoutStatus).build());
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<UploadRequest> upload(StreamObserver<UploadRequestResponse> responseObserver) {
        log("Upload called.");
        responseObserver.onNext(UploadRequestResponse.newBuilder().setStatus(UploadStatus.UPLOAD_STARTING).build());
        return new UploadRequestObserver(responseObserver, storage, sessionManager, IMAGES_BUCKET_NAME);
    }

    @Override
    public void process(ProcessRequest processRequest, StreamObserver<ProcessResponse> responseObserver) {
        String sessionID = processRequest.getSessionId();
        log(String.format("Process called with session '%s'.", sessionID));

        //Validate Session
        if (!sessionManager.isValid(sessionID)) {
            log(ERROR, String.format("Invalid session '%s'.", sessionID));
            responseObserver.onNext(ProcessResponse.newBuilder().setStatus(ProcessStatus.PROCESS_INVALID_SESSION).build());
            return;
        }

        //Load information
        String blobName = processRequest.getUploadToken();
        String language = processRequest.getLanguage();
        String topicName;
        if (sessionManager.isPremium(sessionID))
            topicName = "premium-ocr";
        else
            topicName = "free-ocr";

        //Validate if Blob exists
        BlobId blobId = BlobId.of(IMAGES_BUCKET_NAME, blobName);
        Blob blob = storage.get(blobId);
        if (!blob.exists()) {
            log(ERROR, String.format("Blob '%s' does not exist.", blobId.getName()));
            responseObserver.onNext(ProcessResponse.newBuilder().setStatus(ProcessStatus.PROCESS_INVALID_TOKEN).build());
            responseObserver.onError(new Exception(String.format("Blob '%s' does not exist.", blobId.getName())));
            return;
        }

        log(String.format("Publishing blob '%s' on topic '%s'...", blobName, topicName));

        //Publish to free/premium topic
        TopicName tName = TopicName.ofProjectTopicName(PROJECT_ID, topicName);
        try {
            Publisher publisher = Publisher.newBuilder(tName).build();
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
                    .putAttributes("blobName", blobName)
                    .putAttributes("language", language)
                    .build();
            ApiFuture<String> future = publisher.publish(pubsubMessage);
            future.get();
            publisher.shutdown();
            log(String.format("Successfully published blob '%s' on topic '%s'.", blobName, topicName));
        } catch (IOException | ExecutionException | InterruptedException e) {
            log(ERROR, String.format("Failed to publish blob '%s' on topic '%s'.", blobName, topicName));
            responseObserver.onError(e);
            return;
        }

        //Update Status
        responseObserver.onNext(ProcessResponse.newBuilder().setStatus(ProcessStatus.READING_TEXT).build());

        //Set Firestore Listener
        log(String.format("Set Firestore listener for collection '%s' on document '%s'.", FIRESTORE_COLLECTION_NAME, blobName));
        DocumentReference docRef = db.collection(FIRESTORE_COLLECTION_NAME).document(blobName);
        docRef.addSnapshotListener((snapshot, e) -> {
            ProcessResponse.Builder response = ProcessResponse.newBuilder();
            if (e != null) {
                log(ERROR, String.format("Listen failed for collection '%s' on document '%s'.", FIRESTORE_COLLECTION_NAME, blobName));
                response.setStatus(ProcessStatus.PROCESS_ERROR);
                response.setError(e.getMessage());
                responseObserver.onNext(response.build());
                responseObserver.onError(e);
                return;
            }
            if (snapshot != null && snapshot.exists()) {
                String error = snapshot.getString("error");
                if(error != null){
                    log(ERROR, String.format("Error on collection '%s' on document '%s'. Error: '%s'.",
                            FIRESTORE_COLLECTION_NAME, blobName, error));
                    response.setStatus(ProcessStatus.PROCESS_ERROR);
                    response.setError(error);
                    responseObserver.onNext(response.build());
                    responseObserver.onError(new Exception(error));
                    return;
                }

                String ocrResult = snapshot.getString("text");
                if (ocrResult != null) {
                    response.setText(ocrResult);
                    if (language.equals("")) {
                        log(String.format("Update for collection '%s' on document '%s'. OcrResult: '%s'",
                                FIRESTORE_COLLECTION_NAME, blobName, ocrResult));
                        log(String.format("Finished listening for collection '%s' on document '%s'.", FIRESTORE_COLLECTION_NAME, blobName));

                        response.setStatus(ProcessStatus.READING_SUCCESS);
                        responseObserver.onNext(response.build());
                        responseObserver.onCompleted();
                        return;
                    }
                    String translation = snapshot.getString("translationResult");
                    if (translation != null) {
                        log(String.format("Update for collection '%s' on document '%s'. Translation: '%s'",
                                FIRESTORE_COLLECTION_NAME, blobName, translation));
                        log(String.format("Finished listening for collection '%s' on document '%s'.", FIRESTORE_COLLECTION_NAME, blobName));

                        response.setTranslation(translation);
                        response.setStatus(ProcessStatus.TRANSLATE_SUCCESS);
                        responseObserver.onNext(response.build());
                        responseObserver.onCompleted();
                        return;
                    }
                    log(String.format("Update for collection '%s' on document '%s'. OcrResult: '%s'",
                            FIRESTORE_COLLECTION_NAME, blobName, ocrResult));
                    response.setStatus(ProcessStatus.TRANSLATING_TEXT);
                    responseObserver.onNext(response.build());
                }
            }
        });
    }

    @Override
    public StreamObserver<CheckRequest> check(StreamObserver<CheckResponse> responseObserver) {
        log("Check called.");
        return new CheckRequestObserver(responseObserver, sessionManager, db, FIRESTORE_COLLECTION_NAME);
    }
}
