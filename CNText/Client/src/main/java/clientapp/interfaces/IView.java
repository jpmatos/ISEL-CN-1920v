package clientapp.interfaces;

import java.util.ArrayList;

public interface IView {
    void printProcessSuccesses(ArrayList<IProcessRequest> requests);

    void printProcessOngoing(ArrayList<IProcessRequest> requests);

    void printProcessAllRequests(ArrayList<IProcessRequest> requests);

    void printUploadSuccesses(ArrayList<IUploadRequest> requests);

    void printUploadOngoing(ArrayList<IUploadRequest> requests);

    void printUploadAllRequests(ArrayList<IUploadRequest> requests);

    String printSelectLanguage();

    void printNotLoggedIn();

    String printUsernameInput();

    String printPasswordInput();

    void printSuccessfulLogin(String user);

    void printFailedLogin(String name);

    String printPathInput();

    void printSuccessfulLogout(String name);

    int printViewMenuSelection();

    int printMainMenuSelection(IOperations operations);

    void printInvalidOption();

    String printUploadTokenInput();

    void printWelcomeMessage();

    String printIPInput(String svcIP);

    int printPort(int svcPort);

    void printAlreadyLoggedIn();
}
