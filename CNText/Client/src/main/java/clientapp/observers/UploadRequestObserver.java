package clientapp.observers;

import CnText.UploadRequestResponse;
import clientapp.utils.IOpers;
import io.grpc.stub.StreamObserver;

public class UploadRequestObserver implements StreamObserver<UploadRequestResponse> {
    private IOpers operations;
    private String fileName;
    private String uploadToken;
    private String status;

    public UploadRequestObserver(IOpers operations, String fileName) {
        this.operations = operations;
        this.fileName = fileName;
    }

    public String getUploadToken(){
        return uploadToken;
    }

    public String getFilename() {
        return this.fileName;
    }

    @Override
    public void onNext(UploadRequestResponse uploadRequestResponse) {
        String token = uploadRequestResponse.getUploadToken();
        if(token != null && !token.equals(""))
            this.uploadToken = uploadRequestResponse.getUploadToken();
        this.status = uploadRequestResponse.getStatus();

        System.out.println(String.format("[%s] - %s", this.uploadToken, this.status));
    }

    @Override
    public void onError(Throwable throwable) {
        this.status = "Failed";

        System.out.println(String.format("[%s] - %s", this.uploadToken, this.status));
    }

    @Override
    public void onCompleted() {
        operations.addToCompletedObserversList(this);
        this.status = "Success";

        System.out.println(String.format("[%s] - %s", this.uploadToken, this.status));
    }
}
