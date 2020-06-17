package serviceapp;

import CnText.*;
import com.google.api.core.ApiFuture;
import com.google.cloud.RestorableState;
import com.google.cloud.WriteChannel;
import com.google.cloud.firestore.*;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Operations extends CnTextGrpc.CnTextImplBase  {
    private final String PROJECT_ID = "g01-li61n";
    private final String USERS_COLLECTION_NAME = "Users";
    private final String IMAGES_BUCKET_NAME = "images-cn";
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
        String username = request.getUser();
        String password = request.getPassword();
        Query query = db.collection(USERS_COLLECTION_NAME).whereEqualTo("username", username);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        //See if the User is valid, matches password, and if it is premium or not
        LoginStatus loginStatus;
        boolean premium = false;
        try {
            List<QueryDocumentSnapshot> docs = querySnapshot.get().getDocuments();
            if(docs.size() == 0)
                loginStatus = LoginStatus.LOGIN_UNKNOWN_USER;
            else {
                DocumentSnapshot doc = docs.get(0);
                User userFound = doc.toObject(User.class);
                premium = userFound.premium;
                if (userFound.password.equals(password))
                    loginStatus = LoginStatus.LOGIN_SUCCESS;
                else
                    loginStatus = LoginStatus.LOGIN_WRONG_PASSWORD;
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            loginStatus = LoginStatus.LOGIN_COMMUNICATION_ERROR;
        }

        //Build response
        CnText.Session.Builder sessionBuilder = CnText.Session.newBuilder().setStatus(loginStatus).setUser(username);
        if(loginStatus == LoginStatus.LOGIN_SUCCESS) {
            String sessionID = sessionManager.newSession(premium);
            sessionBuilder.setSessionId(sessionID);
            //TODO Call VMManagement
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

        return new StreamObserver<UploadRequest>() {
            private String blobName = getAlphaNumericString(16);
            private UploadStatus uploadStatus = UploadStatus.UPLOAD_STARTING;
            private WriteChannel writer = null;
            private RestorableState<WriteChannel> capture;

            @Override
            public void onNext(UploadRequest uploadRequest) {

                if(uploadStatus != UploadStatus.UPLOADING && uploadStatus != UploadStatus.UPLOAD_STARTING)
                    return;

                //Validate Session
                if(!sessionManager.isValid(uploadRequest.getSessionId())){
                    uploadStatus = UploadStatus.UPLOAD_INVALID_SESSION;
                    responseObserver.onNext(UploadRequestResponse.newBuilder().setStatus(uploadStatus).build());
                    responseObserver.onError(null);
                    return;
                }

                try{
                    byte[] data = uploadRequest.getImage().toByteArray();
                    if(capture == null) {
                        //TODO Validate mime/extension

                        //Create blob and open WriteChannel
                        blobName += "." + uploadRequest.getExtension();
                        BlobId blobId = BlobId.of(IMAGES_BUCKET_NAME, blobName);
                        String contentType = uploadRequest.getMime();
                        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(contentType).build();
                        writer = storage.writer(blobInfo);
                    }
                    else {
                        writer = capture.restore();
                    }

                    //Write to Google Cloud and save WriteChannel
                    writer.write(ByteBuffer.wrap(data, 0, data.length));
                    capture = writer.capture();

                    //Update Status
                    uploadStatus = UploadStatus.UPLOADING;
                    responseObserver.onNext(UploadRequestResponse.newBuilder().setStatus(uploadStatus).build());
                } catch (Exception e) {
                    e.printStackTrace();

                    //Attempt to close write stream
                    closeWriteStream(writer, capture);

                    //TODO Delete partial file upload

                    //Update Status
                    uploadStatus = UploadStatus.UPLOAD_ERROR;
                    responseObserver.onNext(UploadRequestResponse.newBuilder().setStatus(uploadStatus).build());
                    responseObserver.onError(e);
                }
            }

            @Override
            public void onError(Throwable throwable) {

                //Attempt to close write stream
                closeWriteStream(writer, capture);

                //TODO Delete partial file upload

                //Update Status
                uploadStatus = UploadStatus.UPLOAD_USER_ERROR;
                responseObserver.onNext(UploadRequestResponse.newBuilder().setStatus(uploadStatus).build());
                responseObserver.onError(throwable);
            }

            @Override
            public void onCompleted() {

                //Attempt to close write stream
                closeWriteStream(writer, capture);

                if(uploadStatus != UploadStatus.UPLOADING)
                    return;

                //Update Status and call onComplete()
                uploadStatus = UploadStatus.UPLOAD_SUCCESS;
                responseObserver.onNext(UploadRequestResponse.newBuilder()
                        .setUploadToken(blobName)
                        .setStatus(uploadStatus)
                        .build());
                responseObserver.onCompleted();
                System.out.println("Blob access URL: " + "https://storage.googleapis.com/" + IMAGES_BUCKET_NAME + "/" + blobName);
            }

            private void closeWriteStream(WriteChannel writer, RestorableState<WriteChannel> capture) {
                try {
                    if(writer != null && writer.isOpen())
                        writer.close();
                    if(capture != null) {
                        writer = capture.restore();
                        if(writer != null && writer.isOpen())
                            writer.close();
                    }
                } catch (IOException ignored) { }
            }
        };
    }

    @Override
    public void translate(TranslateRequest translateRequest, StreamObserver<TranslateResponse> responseObserver) {
        System.out.println("Called Translate");
        String sessionID = translateRequest.getSessionId();

        //Validate Session
        if(!sessionManager.isValid(sessionID)){
            responseObserver.onNext(TranslateResponse.newBuilder().setStatus(TranslateStatus.TRANSLATE_INVALID_SESSION).build());
            return;
        }

        //TODO Validate blob/uploadToken

        //Load information
        String blobName = translateRequest.getUploadToken();
        String language = translateRequest.getLanguage();
        String topicName;
        if(sessionManager.isPremium(sessionID))
            topicName = "premium-ocr";
        else
            topicName = "free-ocr";

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
        responseObserver.onNext(TranslateResponse.newBuilder().setStatus(TranslateStatus.TRANSLATING).build());

        //TODO Set Firestore Listener for updates
    }

    private String getAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    public List<SessionManager.Session> getActiveSessions() {
        return sessionManager.getActiveSessions();
    }
}
