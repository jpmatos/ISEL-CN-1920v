package clientapp.interfaces;

import CnText.ProcessStatus;

public interface IProcessRequest {
    String getLanguage();
    String getFilename();
    String getTranslation();
    String getUploadToken();
    ProcessStatus getStatus();
    boolean isCompleted();
}
