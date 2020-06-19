package serviceapp.observer;

import CnText.UploadRequest;
import CnText.UploadRequestResponse;
import CnText.UploadStatus;
import com.google.cloud.RestorableState;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import io.grpc.stub.StreamObserver;
import serviceapp.util.Logger;
import serviceapp.util.SessionManager;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class UploadRequestObserver implements StreamObserver<UploadRequest> {
    private final StreamObserver<UploadRequestResponse> responseObserver;
    private final SessionManager sessionManager;
    private final Storage storage;
    private final String imagesBucketName;
    private String blobName;
    private UploadStatus uploadStatus = UploadStatus.UPLOAD_STARTING;
    private WriteChannel writer = null;
    private RestorableState<WriteChannel> capture;
    private final String[] supportedMIMETypes =
            {"image/jpeg", "image/png", "image/gif", "image/bmp", "image/webp", "image/x-dcraw",
                    "image/vnd.microsoft.icon", "application/pdf", "image/tiff"};
    private final String[] supportedExtensions =
            {"jpg", "jpeg", "png", "gif", "bmp", "webp", "raw", "ico", "pdf", "tif", "tiff"};

    public UploadRequestObserver(StreamObserver<UploadRequestResponse> responseObserver, Storage storage, SessionManager sessionManager, String imagesBucketName) {
        this.responseObserver = responseObserver;
        this.storage = storage;
        this.sessionManager = sessionManager;
        this.imagesBucketName = imagesBucketName;
    }

    @Override
    public void onNext(UploadRequest uploadRequest) {

        if(uploadStatus != UploadStatus.UPLOADING_IMAGE && uploadStatus != UploadStatus.UPLOAD_STARTING)
            return;

        //Validate Session
        String sessionID = uploadRequest.getSessionId();
        Logger.log(String.format("Upload onNext() called with session '%s'.", sessionID));
        if(!sessionManager.isValid(sessionID)){
            Logger.log(String.format("Invalid session '%s'.", sessionID));

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
                    Logger.log(String.format("Invalid image from session '%s'.", sessionID));

                    uploadStatus = UploadStatus.UNSUPPORTED_FORMAT;
                    responseObserver.onNext(UploadRequestResponse.newBuilder().setStatus(uploadStatus).build());
                    responseObserver.onError(null);
                    return;
                }

                //Create blob and open WriteChannel
                blobName = buildBlobName(sessionID, sessionManager.getUsername(sessionID), filename);
                BlobId blobId = BlobId.of(imagesBucketName, blobName);
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
            Logger.log(String.format("Exception writing chunk from session '%s'.", sessionID));

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
        Logger.log("Upload client error.");

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

        Logger.log(String.format("Upload Complete. Blob access URL: https://storage.googleapis.com/%s/%s", imagesBucketName, blobName));
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
            BlobId blobId = BlobId.of(imagesBucketName, blobName);
            storage.delete(blobId);
        } catch (Exception ignored){ }
    }


    private String buildBlobName(String sessionID, String username, String filename) {
        return sessionID + "-" +
                username + "-" +
                java.time.Clock.systemUTC().instant().toString()
                        .replace(".", "-")
                        .replace(":", "-") + "-" +
                filename;
    }
}
