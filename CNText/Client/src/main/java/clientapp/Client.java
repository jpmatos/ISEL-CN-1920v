package clientapp;

import CnText.LoginStatus;
import CnText.LogoutStatus;
import clientapp.utils.IUploadRequest;
import clientapp.utils.ScanUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Client {
    private static String svcIP = "localhost";
    private static int svcPort = 8000;

    public static void main(String[] args) {
        try {
            if (args.length == 2) {
                svcIP = args[0];
                svcPort = Integer.parseInt(args[1]);
            }

            Operations operations = new Operations(svcIP, svcPort);
            boolean cont = true;
            while (cont) {
                if(operations.isLogged())
                    System.out.println("\nLogged in as '" + operations.getUser() + "'");
                int oper = ScanUtils.getInputInt(
                            "------------------\n" +
                                "Pick an option:\n" +
                                "[1] - Login\n" +
                                "[2] - Upload\n" +
                                "[3] - Translate\n" +
                                "[4] - View\n" +
                                "[5] - Logout\n" +
                                "[0] - Quit");
                switch (oper) {
                    case 1:
                        login(operations);
                        break;
                    case 2:
                        upload(operations);
                        break;
                    case 3:
                        translate(operations);
                        break;
                    case 4:
                        view(operations);
                        break;
                    case 5:
                        logout(operations);
                    case 0:
                        cont = false;
                    default:
                        System.out.println("Invalid option");
                }
            }

            if(operations.isLogged())
                logout(operations);

            System.exit(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void login(Operations operations) {
        if(operations.isLogged()){
            System.out.println("Already logged in!");
            return;
        }

        String username = ScanUtils.getInputString("Username:");
        String password = ScanUtils.getInputString("Password:");
        LoginStatus res = operations.login(username, password);
        if(res == LoginStatus.LOGIN_SUCCESS)
            System.out.println("Successfully logged in as '" + operations.getUser() + "'");
        else
            System.out.println("Failed to login! Reason: '" + res + "'");
    }

    private static void upload(Operations operations) throws IOException {
        if(!operations.isLogged()){
            System.out.println("Log in first!");
            return;
        }

        Path path = Paths.get(ScanUtils.getInputString("File Path:"));
        operations.upload(path);
    }

    private static void translate(Operations operations) {
        if(!operations.isLogged()){
            System.out.println("Log in first!");
            return;
        }

        ArrayList<IUploadRequest> uploadSuccesses = operations.getUploadSuccesses();
        for (int i = 0; i < uploadSuccesses.size(); i++) {
            System.out.println(String.format("[%d] - '%s'", i + 1, uploadSuccesses.get(i).getFilename()));
        }
        System.out.println("[0] - Cancel");
        int option = ScanUtils.getInputInt("Pick a file:");

        if(option == 0)
            return;
        if(option < 0 || option > uploadSuccesses.size()){
            System.out.println("Invalid option");
            return;
        }
        String language = ScanUtils.getInputString("Language to Translate:");
        IUploadRequest req = uploadSuccesses.get(option - 1);
        operations.translate(req.getUploadToken(), req.getFilename(), language);
    }

    private static void logout(Operations operations) {
        if(!operations.isLogged()){
            System.out.println("Log in first!");
            return;
        }

        LogoutStatus status = operations.logout();
        System.out.println("Logged out with status '" + status + "'");
    }

    private static void view(Operations operations) {
        if(!operations.isLogged()){
            System.out.println("Log in first!");
            return;
        }
        boolean cont = true;
        while(cont){
            int oper = ScanUtils.getInputInt(
                    "------------------\n" +
                            "Pick an option:\n" +
                            "[1] - Translations: Successes\n" +
                            "[2] - Translations: Ongoing\n" +
                            "[3] - Translations: All\n" +
                            "[4] - Upload: Successes\n" +
                            "[5] - Upload: Ongoing" +
                            "[6] - Upload: All\n" +
                            "[0] - Back");
            switch (oper){
                case 1:
                    View.printTranslationSuccesses(operations);
                    break;
                case 2:
                    View.printTranslationOngoing(operations);
                    break;
                case 3:
                    View.printTranslationAllRequests(operations);
                    break;
                case 4:
                    View.printUploadSuccesses(operations);
                    break;
                case 5:
                    View.printUploadOngoing(operations);
                    break;
                case 6:
                    View.printUploadAllRequests(operations);
                    break;
                case 0:
                    cont = false;
                default:
                    System.out.println("Invalid option");
            }
        }
    }
}
