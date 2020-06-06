package clientapp;

import CnText.LoginStatus;
import clientapp.utils.CompletedTranslations;
import clientapp.utils.CompletedUploads;
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

            //TODO Add ability to manually check on image uploads and translations in process
            Operations operations = new Operations(svcIP, svcPort);
            while (true) {
                if(operations.isLogged())
                    System.out.println("\nLogged in as '" + operations.getUser() + "'");
                int oper = ScanUtils.getInputInt(
                            "------------------\n" +
                                "Pick an operation:\n" +
                                "[1] - Login\n" +
                                "[2] - Upload\n" +
                                "[3] - Translate\n" +
                                "[4] - Check Done Translations\n" +
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
                        checkDoneTranslations(operations);
                        break;
                    case 5:
                        logout(operations);
                    case 0:
                        System.exit(0);
                    default:
                        System.out.println("Unknown operation");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void logout(Operations operations) {
        if(!operations.isLogged()){
            System.out.println("Log in first!");
            return;
        }

        operations.logout();
    }

    private static void checkDoneTranslations(Operations operations) {
        if(!operations.isLogged()){
            System.out.println("Log in first!");
            return;
        }

        ArrayList<CompletedTranslations> completedTranslations = operations.getCompletedTranslations();
        for (int i = 1; i < completedTranslations.size() + 1; i++) {
            System.out.println(String.format("[%d] - '%s' to '%s'", i, completedTranslations.get(i).getLanguage(), completedTranslations.get(i).getLanguage()));
        }
        System.out.println("[0] - Cancel");
        int option = ScanUtils.getInputInt("Pick a translation:");

        if(option == 0)
            return;
        if(option < 0 || option > completedTranslations.size() + 1){
            System.out.println("Invalid option");
            return;
        }

        System.out.println(completedTranslations.get(option).getTranslation());
    }

    private static void translate(Operations operations) {
        if(!operations.isLogged()){
            System.out.println("Log in first!");
            return;
        }

        ArrayList<CompletedUploads> completedUploads = operations.getCompletedUploads();
        for (int i = 1; i < completedUploads.size() + 1; i++) {
            System.out.println(String.format("[%d] - '%s'", i, completedUploads.get(i).getFilename()));
        }
        System.out.println("[0] - Cancel");
        int option = ScanUtils.getInputInt("Pick a file:");

        if(option == 0)
            return;
        if(option < 0 || option > completedUploads.size() + 1){
            System.out.println("Invalid option");
            return;
        }

        //TODO Make languages an enum in grpc
        String language = ScanUtils.getInputString("Select language:");

        operations.translate(completedUploads.get(option - 1).getUploadToken(), language);
    }

    private static void upload(Operations operations) throws IOException {
        if(!operations.isLogged()){
            System.out.println("Log in first!");
            return;
        }

        Path path = Paths.get(ScanUtils.getInputString("File Path:"));
        operations.upload(path);
    }

    private static void login(Operations operations) {
        if(operations.isLogged()){
            System.out.println("Already logged in!");
            return;
        }

        String username = ScanUtils.getInputString("Username:");
        String password = ScanUtils.getInputString("Password:");
        LoginStatus res = operations.login(username, password);
        if(res == LoginStatus.SUCCESS)
            System.out.println("Successfully logged in as '" + operations.getUser() + "'");
        else
            System.out.println("Failed to login! Reason: '" + res + "'");
    }
}
