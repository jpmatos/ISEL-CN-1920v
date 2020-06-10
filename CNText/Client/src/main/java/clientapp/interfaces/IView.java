package clientapp.interfaces;

import java.nio.file.Path;
import java.util.ArrayList;

public interface IView {
    void printTranslationSuccesses(ArrayList<ITranslationRequest> requests);
    void printTranslationOngoing(ArrayList<ITranslationRequest> requests);
    void printTranslationAllRequests(ArrayList<ITranslationRequest> requests);
    void printUploadSuccesses(ArrayList<IUploadRequest> requests);
    void printUploadOngoing(ArrayList<IUploadRequest> requests);
    void printUploadAllRequests(ArrayList<IUploadRequest> requests);
    IUploadRequest printUploadSuccessesPicker(ArrayList<IUploadRequest> uploadRequests);
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
}
