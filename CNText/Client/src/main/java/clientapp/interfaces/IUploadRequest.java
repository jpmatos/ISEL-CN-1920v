package clientapp.interfaces;

import CnText.UploadStatus;

public interface IUploadRequest {
    String getFilename();

    String getUploadToken();

    UploadStatus getStatus();

    boolean isCompleted();
}
