package clientapp.observer;

import CnText.ProcessResponse;
import CnText.ProcessStatus;
import clientapp.interfaces.IProcessRequest;
import io.grpc.stub.StreamObserver;

public class ProcessRequestObserver implements StreamObserver<ProcessResponse>, IProcessRequest {
    private final String uploadToken;
    private final String filename;
    private final String language;
    private String translation;
    private ProcessStatus status;
    private boolean completed = false;
    private String text;
    private String error;

    public ProcessRequestObserver(String uploadToken, String filename, String language) {
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
    public ProcessStatus getStatus() {
        return status;
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getError() {
        return error;
    }

    @Override
    public void onNext(ProcessResponse processResponse) {
        String error = processResponse.getError();
        status = processResponse.getStatus();

        if(error != null && !error.isEmpty()) {
            this.error = error;
            return;
        }

        String text = processResponse.getText();
        if (text != null)
            this.text = text;

        String translation = processResponse.getTranslation();
        if (translation != null)
            this.translation = translation;

        if (text != null && !text.isEmpty())
            System.out.println(String.format("[%s][%s] Update - '%s'; Text - %s.",
                    filename, uploadToken, status, text));
        else
            System.out.println(String.format("[%s][%s] Update - '%s'.",
                    filename, uploadToken, status));
    }

    @Override
    public void onError(Throwable throwable) {
        completed = true;
        if(error != null)
            System.out.println(String.format("[%s][%s] Failed Status - '%s.' Error: '%s'.",
                    filename, uploadToken, status, error));
        else
            System.out.println(String.format("[%s][%s] Failed Status - '%s.'",
                    filename, uploadToken, status));
    }

    @Override
    public void onCompleted() {
        completed = true;
        if (translation != null && !translation.isEmpty())
            System.out.println(String.format("[%s][%s] Completed Status - '%s'. Translation: '%s'.",
                    filename, uploadToken, status, translation));
        else
            System.out.println(String.format("[%s][%s] Completed Status - '%s'.",
                    filename, uploadToken, status));
    }
}
