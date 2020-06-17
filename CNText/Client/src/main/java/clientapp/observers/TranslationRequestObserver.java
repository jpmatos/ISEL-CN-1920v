package clientapp.observers;

import CnText.TranslateResponse;
import CnText.TranslateStatus;
import clientapp.interfaces.ITranslationRequest;
import io.grpc.stub.StreamObserver;

public class TranslationRequestObserver implements StreamObserver<TranslateResponse>, ITranslationRequest {
    private final String uploadToken;
    private final String filename;
    private final String language;
    private String translation;
    private TranslateStatus status;
    private boolean completed = false;

    public TranslationRequestObserver(String uploadToken, String filename, String language) {
        this.uploadToken = uploadToken;
        this.filename = filename;
        this.language = language;
    }

    @Override
    public String getLanguage() {
        return language;
    }

    @Override
    public String getFilename() {
        return filename;
    }

    @Override
    public String getTranslation() {
        return translation;
    }

    @Override
    public String getUploadToken() {
        return uploadToken;
    }

    @Override
    public TranslateStatus getStatus() {
        return status;
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public void onNext(TranslateResponse translateResponse) {
        String translation = translateResponse.getTranslation();
        if(translation != null)
            this.translation = translation;

        status = translateResponse.getStatus();

        System.out.println(String.format("[%s][%s] Update - '%s'", filename, uploadToken, status));
    }

    @Override
    public void onError(Throwable throwable) {
        completed = true;
        System.out.println(String.format("[%s][%s] Failed Status - '%s'", filename, uploadToken, status));
    }

    @Override
    public void onCompleted() {
        completed = true;
        System.out.println(String.format("[%s][%s] Completed Status - '%s'", filename, uploadToken, status));
    }
}
