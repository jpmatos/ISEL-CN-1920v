package clientapp.observers;

import CnText.UploadRequestResponse;
import CnText.UploadStatus;
import clientapp.interfaces.IOperations;
import clientapp.interfaces.IUploadRequest;
import io.grpc.stub.StreamObserver;

public class UploadRequestObserver implements StreamObserver<UploadRequestResponse>, IUploadRequest {
    private final IOperations operations;
    private String fileName;
    private String languages;
    private String uploadToken;
    private UploadStatus status;
    private boolean completed = false;

    public UploadRequestObserver(String fileName, String languages, IOperations operations) {
        this.fileName = fileName;
        this.operations = operations;
        this.languages = languages;
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
            uploadToken = token;
        status = uploadRequestResponse.getStatus();

        System.out.println(String.format("[%s] Status - '%s'", fileName, status));
    }

    @Override
    public void onError(Throwable throwable) {
        completed = true;
        System.out.println(String.format("[%s] Failed Status - '%s'", fileName, status));
    }

    @Override
    public void onCompleted() {
        completed = true;
        System.out.println(String.format("[%s] Completed Status - '%s' Token - '%s'", fileName, status, uploadToken));
        operations.process(uploadToken, fileName, languages);
    }
}
