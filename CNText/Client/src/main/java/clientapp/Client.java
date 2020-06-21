package clientapp;

import CnText.CheckRequest;
import CnText.LoginStatus;
import CnText.LogoutStatus;
import clientapp.interfaces.IOperations;
import clientapp.interfaces.IView;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Client {
    private static String svcIP = "localhost";
    private static int svcPort = 8000;

    public static void main(String[] args) {
        try {
            IView view = new View();
            if (args.length == 2) {
                svcIP = args[0];
                svcPort = Integer.parseInt(args[1]);
            } else {
                svcIP = view.printIPInput(svcIP);
                svcPort = view.printPort(svcPort);
            }

            IOperations operations = new Operations(svcIP, svcPort);
            view.printWelcomeMessage();
            boolean cont = true;
            while (cont) {
                int oper = view.printMainMenuSelection(operations);
                switch (oper) {
                    case 1:
                        login(operations, view);
                        break;
                    case 2:
                        upload(operations, view);
                        break;
                    case 3:
                        check(operations, view);
                        break;
                    case 4:
                        views(operations, view);
                        break;
                    case 5:
                        logout(operations, view);
                        break;
                    case 0:
                        cont = false;
                        break;
                    default:
                        view.printInvalidOption();
                }
            }

            if (operations.isLogged())
                logout(operations, view);

            System.exit(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void login(IOperations operations, IView view) {
        if (operations.isLogged()) {
            view.printAlreadyLoggedIn();
            return;
        }

        String username = view.printUsernameInput();
        if (username.equals(""))
            return;

        String password = view.printPasswordInput();
        LoginStatus res = operations.login(username, password);
        if (res == LoginStatus.LOGIN_SUCCESS)
            view.printSuccessfulLogin(operations.getUser());
        else
            view.printFailedLogin(res.name());
    }

    private static void upload(IOperations operations, IView view) throws IOException {
        if (!operations.isLogged()) {
            view.printNotLoggedIn();
            return;
        }

        String pathStr = view.printPathInput();
        if (pathStr.equals(""))
            return;
        Path path = Paths.get(pathStr);

        String languages = view.printSelectLanguage();
        operations.upload(path, languages);
    }

    private static void check(IOperations operations, IView view) {
        if (!operations.isLogged()) {
            view.printNotLoggedIn();
            return;
        }

        StreamObserver<CheckRequest> check = operations.check();
        while (true) {
            String uploadToken = view.printUploadTokenInput();
            if (uploadToken.equals(""))
                break;
            operations.sendCheckRequest(check, uploadToken);
        }
        check.onCompleted();
    }

    private static void logout(IOperations operations, IView view) {
        if (!operations.isLogged()) {
            view.printNotLoggedIn();
            return;
        }

        LogoutStatus status = operations.logout();
        view.printSuccessfulLogout(status.name());
    }

    private static void views(IOperations operations, IView view) {
        if (!operations.isLogged()) {
            view.printNotLoggedIn();
            return;
        }
        boolean cont = true;
        while (cont) {
            int oper = view.printViewMenuSelection();
            switch (oper) {
                case 1:
                    view.printProcessSuccesses(operations.getProcessRequests());
                    break;
                case 2:
                    view.printProcessOngoing(operations.getProcessRequests());
                    break;
                case 3:
                    view.printProcessAllRequests(operations.getProcessRequests());
                    break;
                case 4:
                    view.printUploadSuccesses(operations.getUploadRequests());
                    break;
                case 5:
                    view.printUploadOngoing(operations.getUploadRequests());
                    break;
                case 6:
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
