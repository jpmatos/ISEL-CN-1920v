package clientapp.utils;

import CnText.TranslateStatus;

public interface ITranslationRequest {
    String getLanguage();
    String getFilename();
    String getTranslation();
    String getUploadToken();
    TranslateStatus getStatus();
    boolean isCompleted();
}
