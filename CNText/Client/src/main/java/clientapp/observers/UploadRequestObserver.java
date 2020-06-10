package clientapp.observers;

import CnText.UploadRequestResponse;
import CnText.UploadStatus;
import clientapp.interfaces.IUploadRequest;
import io.grpc.stub.StreamObserver;

public class UploadRequestObserver implements StreamObserver<UploadRequestResponse>, IUploadRequest {
    private String fileName;
    private String uploadToken;
    private UploadStatus status;
    private boolean completed = false;

    public UploadRequestObserver(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getUploadToken(){
        return uploadToken;
    }

    @Override
    public UploadStatus getStatus() {
        return status;
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public String getFilename() {
        return this.fileName;
    }

    @Override
    public void onNext(UploadRequestResponse uploadRequestResponse) {
        String token = uploadRequestResponse.getUploadToken();
        if(token != null && !token.equals(""))
            this.uploadToken = uploadRequestResponse.getUploadToken();
        this.status = uploadRequestResponse.getStatus();

        System.out.println(String.format("[%s] Update - %s", this.uploadToken, this.status));
    }

    @Override
    public void onError(Throwable throwable) {
        completed = true;
        System.out.println(String.format("[%s] Failure - %s", this.uploadToken, this.status));
    }

    @Override
    public void onCompleted() {
        completed = true;
        System.out.println(String.format("[%s] Completed - %s", this.uploadToken, this.status));
    }
}
