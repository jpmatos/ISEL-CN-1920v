package clientapp.interfaces;

import CnText.UploadStatus;

public interface IUploadRequest {
    String getFilename();
    String getUploadToken();
    String getTranslation();
    UploadStatus getStatus();
    boolean isCompleted();
}
