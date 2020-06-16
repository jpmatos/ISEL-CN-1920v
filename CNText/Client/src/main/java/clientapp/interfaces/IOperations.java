package clientapp.interfaces;

import CnText.LogoutStatus;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public interface IOperations {
    CnText.LoginStatus login(String username, String password);
    void upload(Path path, String languages) throws IOException;
//    void translate(String uploadToken, String filename, String language);
    LogoutStatus logout();
    boolean isLogged();
    String getUser();
//    ArrayList<ITranslationRequest> getTranslationRequests();
    ArrayList<IUploadRequest> getUploadRequests();
}
