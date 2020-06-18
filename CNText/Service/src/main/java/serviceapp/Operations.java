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
        Query query = db.collection(USERS_COLLECTION_NAME).whereEqualTo("username", usernameReq);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        //See if the User is valid, matches password, and if it is premium or not
        LoginStatus loginStatus;
        String username = "";
        boolean premium = false;
        try {
            List<QueryDocumentSnapshot> docs = querySnapshot.get().getDocuments();
            if(docs.size() == 0)
                loginStatus = LoginStatus.LOGIN_UNKNOWN_USER;
            else {
                DocumentSnapshot doc = docs.get(0);
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

        return new StreamObserver<UploadRequest>() {
            private String blobName;// = getAlphaNumericString(16);
            private UploadStatus uploadStatus = UploadStatus.UPLOAD_STARTING;
            private WriteChannel writer = null;
            private RestorableState<WriteChannel> capture;
            private final String[] supportedMIMETypes =
                    {"image/bmp", "image/jpeg", "image/png", "image/svg+xml", "image/tiff"};
            private final String[] supportedExtensions =
                    {".bmp", ".jpeg", ".jpg", ".png", ".svg", ".tiff", ".tif"};

            @Override
            public void onNext(UploadRequest uploadRequest) {

                if(uploadStatus != UploadStatus.UPLOADING_IMAGE && uploadStatus != UploadStatus.UPLOAD_STARTING)
                    return;

                //Validate Session
                String sessionID = uploadRequest.getSessionId();
                if(!sessionManager.isValid(sessionID)){
                    uploadStatus = UploadStatus.UPLOAD_INVALID_SESSION;
                    responseObserver.onNext(UploadRequestResponse.newBuilder().setStatus(uploadStatus).build());
                    responseObserver.onError(null);
                    return;
                }

                try{
                    byte[] data = uploadRequest.getImage().toByteArray();
                    if(capture == null) {

                        String mimeType = uploadRequest.getMime();
                        String filename = uploadRequest.getFilename();
                        String extension = "";
                        int i = filename.lastIndexOf('.');
                        if (i > 0)
                            extension = filename.substring(i+1);

                        //Validate MIME and Extension
                        if(!Arrays.asList(supportedMIMETypes).contains(mimeType)
                            && !Arrays.asList(supportedExtensions).contains(extension)){
                            uploadStatus = UploadStatus.UNSUPPORTED_FORMAT;
                            responseObserver.onNext(UploadRequestResponse.newBuilder().setStatus(uploadStatus).build());
                            responseObserver.onError(null);
                            return;
                        }

                        //Create blob and open WriteChannel
                        blobName = buildBlobname(sessionID, sessionManager.getUsername(sessionID), filename);
                        BlobId blobId = BlobId.of(IMAGES_BUCKET_NAME, blobName);
                        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(mimeType).build();
                        writer = storage.writer(blobInfo);
                    }
                    else {
                        writer = capture.restore();
                    }

                    //Write to Google Cloud and save WriteChannel
                    writer.write(ByteBuffer.wrap(data, 0, data.length));
                    capture = writer.capture();

                    //Update Status
                    uploadStatus = UploadStatus.UPLOADING_IMAGE;
                    responseObserver.onNext(UploadRequestResponse.newBuilder().setStatus(uploadStatus).build());
                } catch (Exception e) {
                    e.printStackTrace();

                    //Attempt to close write stream
                    closeWriteStream(writer, capture);

                    //Delete blob
                    deleteBlob();

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

                //Delete blob
                deleteBlob();

                //Update Status
                uploadStatus = UploadStatus.UPLOAD_USER_ERROR;
                responseObserver.onNext(UploadRequestResponse.newBuilder().setStatus(uploadStatus).build());
                responseObserver.onError(throwable);
            }

            @Override
            public void onCompleted() {

                //Attempt to close write stream
                closeWriteStream(writer, capture);

                if(uploadStatus != UploadStatus.UPLOADING_IMAGE)
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
                } catch (Exception ignored) { }
            }

            private void deleteBlob() {
                try {
                    BlobId blobId = BlobId.of(IMAGES_BUCKET_NAME, blobName);
                    storage.delete(blobId);
                } catch (Exception ignored){ }
            }


            private String buildBlobname(String sessionID, String username, String filename) {
                return sessionID + "-" +
                        username + "-" +
                        java.time.Clock.systemUTC().instant().toString()
                                .replace(".", "-")
                                .replace(":", "-") + "-" +
                        filename;
            }
        };
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
        return new StreamObserver<CheckRequest>() {
            ArrayList<String> infos = new ArrayList<>();

            @Override
            public void onNext(CheckRequest checkRequest) {
                String sessionID = checkRequest.getSessionId();
                String uploadToken = checkRequest.getUploadToken();

                //Validate Session
                if(!sessionManager.isValid(sessionID)){
                    infos.add(String.format("[%s] Invalid Session", uploadToken));
                    return;
                }

                try {
                    DocumentReference docRef = db.collection(FIRESTORE_COLLECTION_NAME).document(uploadToken);
                    DocumentSnapshot doc = docRef.get().get();
                    TextOfImage textOfImage = doc.toObject(TextOfImage.class);
                    if (textOfImage != null)
                        infos.add(String.format("[%s] " + textOfImage.toString(), uploadToken));
                    else
                        infos.add(String.format("[%s] Failed to check upload token in DB", uploadToken));
                } catch (InterruptedException | ExecutionException e) {
                    infos.add(String.format("[%s] Failed to check upload token in DB", uploadToken));
                }
            }

            @Override
            public void onError(Throwable throwable) {
                responseObserver.onCompleted();
            }

            @Override
            public void onCompleted() {
                CheckResponse checkResponse = CheckResponse.newBuilder()
                        .addAllResponse(infos)
                        .build();
                responseObserver.onNext(checkResponse);
                responseObserver.onCompleted();
            }
        };
    }

    public List<SessionManager.Session> getActiveSessions() {
        return sessionManager.getActiveSessions();
    }
}
