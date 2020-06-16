package clientapp;

import CnText.LoginStatus;
import CnText.LogoutStatus;
import clientapp.interfaces.IOperations;
import clientapp.interfaces.IUploadRequest;
import clientapp.interfaces.IView;
import java.io.IOException;
import java.nio.file.Path;

public class Client {
    private static String svcIP = "localhost";
    private static int svcPort = 8000;

    public static void main(String[] args) {
        try {
//            if (args.length == 2) {
//                svcIP = args[0];
//                svcPort = Integer.parseInt(args[1]);
//            }

            IOperations operations = new Operations(svcIP, svcPort);
            IView view = new View();
            boolean cont = true;
            while (cont) {
                if(operations.isLogged())
                    System.out.println("\nLogged in as '" + operations.getUser() + "'");
                int oper = view.PrintMainMenuSelection();
                switch (oper) {
                    case 1:
                        login(operations, view);
                        break;
                    case 2:
                        upload(operations, view);
                        break;
//                    case 3:
//                        translate(operations, view);
//                        break;
                    case 3:
                        view(operations, view);
                        break;
                    case 4:
                        logout(operations, view);
                        break;
                    case 0:
                        cont = false;
                        break;
                    default:
                        view.printInvalidOption();
                }
            }

            if(operations.isLogged())
                logout(operations, view);

            System.exit(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void login(IOperations operations, IView view) {
        if(operations.isLogged()){
            view.printNotLoggedIn();
            return;
        }

        String username = view.printUsernameInput();
        String password = view.printPasswordInput();
        LoginStatus res = operations.login(username, password);
        if(res == LoginStatus.LOGIN_SUCCESS)
            view.printSuccessfulLogin(operations.getUser());
        else
            view.printFailedLogin(res.name());
    }

    private static void upload(IOperations operations, IView view) throws IOException {
        if(!operations.isLogged()){
            view.printNotLoggedIn();
            return;
        }

        Path path = view.printPathInput();
        String languages = view.printSelectLanguage();
        operations.upload(path, languages);
    }

//    private static void translate(IOperations operations, IView view) {
//        if(!operations.isLogged()){
//            view.printNotLoggedIn();
//            return;
//        }
//
//        IUploadRequest req = view.printUploadSuccessesPicker(operations.getUploadRequests());
//        String language = view.printSelectLanguage();
//
//        operations.translate(req.getUploadToken(), req.getFilename(), language);
//    }

    private static void logout(IOperations operations, IView view) {
        if(!operations.isLogged()){
            view.printNotLoggedIn();
            return;
        }

        LogoutStatus status = operations.logout();
        view.printSuccessfulLogout(status.name());
    }

    private static void view(IOperations operations, IView view) {
        if(!operations.isLogged()){
            view.printNotLoggedIn();
            return;
        }
        boolean cont = true;
        while(cont){
            int oper = view.printViewMenuSelection();
            switch (oper){
//                case 1:
//                    view.printTranslationSuccesses(operations.getTranslationRequests());
//                    break;
//                case 2:
//                    view.printTranslationOngoing(operations.getTranslationRequests());
//                    break;
//                case 3:
//                    view.printTranslationAllRequests(operations.getTranslationRequests());
//                    break;
                case 1:
                    view.printUploadSuccesses(operations.getUploadRequests());
                    break;
                case 2:
                    view.printUploadOngoing(operations.getUploadRequests());
                    break;
                case 3:
                    view.printUploadAllRequests(operations.getUploadRequests());
                    break;
                case 0:
                    cont = false;
                    break;
                default:
                    view.printInvalidOption();
            }
        }
    }
}
