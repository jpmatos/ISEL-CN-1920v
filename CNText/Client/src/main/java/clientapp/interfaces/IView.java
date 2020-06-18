package clientapp.interfaces;

import java.nio.file.Path;
import java.util.ArrayList;

public interface IView {
    void printProcessSuccesses(ArrayList<IProcessRequest> requests);
    void printProcessOngoing(ArrayList<IProcessRequest> requests);
    void printProcessAllRequests(ArrayList<IProcessRequest> requests);
    void printUploadSuccesses(ArrayList<IUploadRequest> requests);
    void printUploadOngoing(ArrayList<IUploadRequest> requests);
    void printUploadAllRequests(ArrayList<IUploadRequest> requests);
//    IUploadRequest printUploadSuccessesPicker(ArrayList<IUploadRequest> uploadRequests);
    String printSelectLanguage();
    void printNotLoggedIn();
    String printUsernameInput();
    String printPasswordInput();
    void printSuccessfulLogin(String user);
    void printFailedLogin(String name);
    Path printPathInput();
    void printSuccessfulLogout(String name);
    int printViewMenuSelection();
    int PrintMainMenuSelection();
    void printInvalidOption();
    String printUploadTokenInput();
}
