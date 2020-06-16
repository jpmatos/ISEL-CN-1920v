package serviceapp;

import CnText.*;
import com.google.api.core.ApiFuture;
import com.google.cloud.RestorableState;
import com.google.cloud.WriteChannel;
import com.google.cloud.firestore.*;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;

import java.io.*;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class Operations extends CnTextGrpc.CnTextImplBase  {
    private String USERS_COLLECTION_NAME = "Users";
    private String IMAGES_BUCKET_NAME = "images-cn";
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
        String username = request.getUser();
        String password = request.getPassword();
        Query query = db.collection(USERS_COLLECTION_NAME).whereEqualTo("username", username);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        LoginStatus loginStatus;
        boolean premium = false;
        try {
            List<QueryDocumentSnapshot> docs = querySnapshot.get().getDocuments();
            if(docs.size() == 0)
                loginStatus = LoginStatus.UNKNOWN_USER;
            else {
                DocumentSnapshot doc = docs.get(0);
                User userFound = doc.toObject(User.class);
                premium = userFound.premium;
                if (userFound.password.equals(password))
                    loginStatus = LoginStatus.LOGIN_SUCCESS;
                else
                    loginStatus = LoginStatus.WRONG_PASSWORD;
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            loginStatus = LoginStatus.LOGIN_COMMUNICATION_ERROR;
        }

        CnText.Session.Builder sessionBuilder = CnText.Session.newBuilder().setStatus(loginStatus).setUser(username);
        if(loginStatus == LoginStatus.LOGIN_SUCCESS) {
            String sessionID = sessionManager.newSession(premium);
            sessionBuilder.setSessionId(sessionID);
            //TODO Call VMManagement
        }
        else
            sessionBuilder.setSessionId("");

        CnText.Session response = sessionBuilder.build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void close(CnText.Session request, StreamObserver<CloseResponse> responseObserver) {
        System.out.println("Logout Called");
        String sessionID = request.getSessionId();
        LogoutStatus logoutStatus;

        //TODO Call VMManagement
        if(sessionManager.closeSession(sessionID))
            logoutStatus = LogoutStatus.LOGOUT_SUCCESS;
        else
            logoutStatus = LogoutStatus.LOGOUT_INVALID_SESSION;

        responseObserver.onNext(CloseResponse.newBuilder().setStatus(logoutStatus).build());
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<UploadRequest> upload(StreamObserver<UploadRequestResponse> responseObserver) {
        System.out.println("Called upload");
        //TODO Verify SessionID

        //Generate random string for blob name
        final String[] blobName = {getAlphaNumericString(16)};
        final UploadStatus[] uploadStatus = {UploadStatus.UNRECOGNIZED};


        return new StreamObserver<UploadRequest>() {
            RestorableState<WriteChannel> capture;

            @Override
            public void onNext(UploadRequest uploadRequest) {

                //TODO Verify if valid mime/extension

                byte[] data = uploadRequest.getImage().toByteArray();
                try{
                    WriteChannel writer;
                    if(capture == null) {
                        blobName[0] += "." + uploadRequest.getExtension();
                        BlobId blobId = BlobId.of(IMAGES_BUCKET_NAME, blobName[0]);
                        String contentType = uploadRequest.getMime();
                        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(contentType).build();
                        writer = storage.writer(blobInfo);
                    }
                    else {
                        writer = capture.restore();
                    }
                    writer.write(ByteBuffer.wrap(data, 0, data.length));
                    capture = writer.capture();

                    uploadStatus[0] = UploadStatus.UPLOAD_SUCCESS;
                } catch (Exception e) {
                    e.printStackTrace();

                    uploadStatus[0] = UploadStatus.IMAGE_CORRUPTED;
                    responseObserver.onNext(UploadRequestResponse.newBuilder().setStatus(uploadStatus[0]).build());
                    responseObserver.onError(e);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                //TODO Delete partial file upload
            }

            @Override
            public void onCompleted() {
                try {
                    if(capture != null){
                        WriteChannel cap = capture.restore();
                        if(cap.isOpen())
                            cap.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                responseObserver.onNext(UploadRequestResponse.newBuilder().setUploadToken(blobName[0]).setStatus(uploadStatus[0]).build());
                System.out.println("Blob access URL: " + "https://storage.googleapis.com/" + IMAGES_BUCKET_NAME + "/" + blobName[0]);
                //TODO Publish
                //TODO Set Firestore Listener for updates
                responseObserver.onCompleted();
            }
        };


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
