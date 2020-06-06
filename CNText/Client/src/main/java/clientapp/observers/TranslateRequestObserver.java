package clientapp.observers;

import CnText.TranslateResponse;
import clientapp.utils.IOpers;
import io.grpc.stub.StreamObserver;

public class TranslateRequestObserver implements StreamObserver<TranslateResponse> {
    private final IOpers operations;
    private final String uploadToken;
    private final String language;
    private String translation;
    private String status;

    public TranslateRequestObserver(IOpers operations, String uploadToken, String language) {
        this.operations = operations;
        this.uploadToken = uploadToken;
        this.language = language;
    }

    public String getUploadToken() {
        return uploadToken;
    }

    public String getLanguage() {
        return language;
    }

    public String getTranslation() {
        return translation;
    }

    @Override
    public void onNext(TranslateResponse translateResponse) {
        String translation = translateResponse.getTranslation();
        if(translation != null)
            this.translation = translation;

        this.status = translateResponse.getStatus();

        System.out.println(String.format("[%s] - %s", this.uploadToken, this.status));
    }

    @Override
    public void onError(Throwable throwable) {
        this.status = "Failed";

        System.out.println(String.format("[%s] - %s", this.uploadToken, this.status));

    }

    @Override
    public void onCompleted() {
        operations.addToCompletedTranslationsList(this);
        this.status = "Success";

        System.out.println(String.format("[%s] - %s", this.uploadToken, this.status));
    }
}
