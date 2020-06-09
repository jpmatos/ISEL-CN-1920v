package clientapp;

import clientapp.utils.ITranslationRequest;
import clientapp.utils.IUploadRequest;
import clientapp.utils.ScanUtils;

import java.util.ArrayList;

public class View {
    public static void printTranslationSuccesses(Operations operations) {
        ArrayList<ITranslationRequest> translationSuccesses = operations.getTranslationSuccesses();
        for (int i = 0; i < translationSuccesses.size(); i++) {
            System.out.println(String.format("[%d] - '%s' to '%s'",
                    i + 1, translationSuccesses.get(i).getFilename(), translationSuccesses.get(i).getLanguage()));
        }
        System.out.println("[0] - Cancel");
        int option = ScanUtils.getInputInt("Pick a translation:");

        if(option == 0)
            return;
        if(option < 0 || option > translationSuccesses.size()){
            System.out.println("Invalid option");
            return;
        }
        ITranslationRequest req = translationSuccesses.get(option - 1);
        System.out.println(String.format("[%s] - '%s'", req.getUploadToken(),  req.getTranslation()));
    }

    public static void printTranslationOngoing(Operations operations) {
        ArrayList<ITranslationRequest> translationRequests = operations.getTranslationOngoing();
        for (ITranslationRequest req : translationRequests) {
            System.out.println(String.format("[%s] - '%s'; Status: '%s'",
                    req.getUploadToken(), req.getFilename(), req.getStatus()));
        }
    }

    public static void printTranslationAllRequests(Operations operations) {
        ArrayList<ITranslationRequest> translationRequests = operations.getTranslationAllRequests();
        for (ITranslationRequest req : translationRequests) {
            System.out.println(String.format("[%s] - '%s'; Status: '%s'",
                    req.getUploadToken(), req.getFilename(), req.getStatus()));
        }
    }

    public static void printUploadSuccesses(Operations operations) {
        ArrayList<IUploadRequest> uploadSuccesses = operations.getUploadSuccesses();
        for (IUploadRequest req : uploadSuccesses) {
            System.out.println(String.format("[%s] - '%s'", req.getUploadToken(), req.getFilename()));
        }
    }

    public static void printUploadOngoing(Operations operations) {
        ArrayList<IUploadRequest> uploadRequests = operations.getUploadOngoing();
        for (IUploadRequest req : uploadRequests) {
            System.out.println(String.format("[%s] - '%s'; Status: '%s'",
                    req.getUploadToken(), req.getFilename(), req.getStatus()));
        }
    }

    public static void printUploadAllRequests(Operations operations) {
        ArrayList<IUploadRequest> uploadRequests = operations.getUploadAllRequests();
        for (IUploadRequest req : uploadRequests) {
            System.out.println(String.format("[%s] - '%s'; Status: '%s'",
                    req.getUploadToken(), req.getFilename(), req.getStatus()));
        }
    }
}
