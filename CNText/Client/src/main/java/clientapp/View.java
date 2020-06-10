package clientapp;

import clientapp.interfaces.ITranslationRequest;
import clientapp.interfaces.IUploadRequest;
import clientapp.interfaces.IView;
import clientapp.utils.ScanUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static CnText.TranslateStatus.TRANSLATE_SUCCESS;
import static CnText.UploadStatus.UPLOAD_SUCCESS;

public class View implements IView {
    @Override
    public void printTranslationSuccesses(ArrayList<ITranslationRequest> requests) {
        ArrayList<ITranslationRequest> translationSuccesses = new ArrayList<>();
        for (ITranslationRequest req : requests) {
            if(req.getStatus() == TRANSLATE_SUCCESS)
                translationSuccesses.add(req);
        }

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

    @Override
    public void printTranslationOngoing(ArrayList<ITranslationRequest> requests) {
        for (ITranslationRequest req : requests) {
            if(!req.isCompleted())
                System.out.println(String.format("[%s] - '%s'; Status: '%s'",
                        req.getUploadToken(), req.getFilename(), req.getStatus()));
        }
    }

    @Override
    public void printTranslationAllRequests(ArrayList<ITranslationRequest> requests) {
        for (ITranslationRequest req : requests) {
            System.out.println(String.format("[%s] - '%s'; Status: '%s'",
                    req.getUploadToken(), req.getFilename(), req.getStatus()));
        }
    }

    @Override
    public void printUploadSuccesses(ArrayList<IUploadRequest> requests) {
        for (IUploadRequest req : requests) {
            if(req.getStatus() == UPLOAD_SUCCESS)
                System.out.println(String.format("[%s] - '%s'", req.getUploadToken(), req.getFilename()));
        }
    }

    @Override
    public void printUploadOngoing(ArrayList<IUploadRequest> requests) {
        for (IUploadRequest req : requests) {
            if(!req.isCompleted())
                System.out.println(String.format("[%s] - '%s'; Status: '%s'",
                        req.getUploadToken(), req.getFilename(), req.getStatus()));
        }
    }

    @Override
    public void printUploadAllRequests(ArrayList<IUploadRequest> requests) {
        for (IUploadRequest req : requests) {
            System.out.println(String.format("[%s] - '%s'; Status: '%s'",
                    req.getUploadToken(), req.getFilename(), req.getStatus()));
        }
    }

    @Override
    public IUploadRequest printUploadSuccessesPicker(ArrayList<IUploadRequest> requests) {
        ArrayList<IUploadRequest> uploadSuccesses = new ArrayList<>();
        for (IUploadRequest req : requests) {
            if(req.getStatus() == UPLOAD_SUCCESS)
                uploadSuccesses.add(req);
        }
        while (true) {
            for (int i = 0; i < uploadSuccesses.size(); i++) {
                System.out.println(String.format("[%d] - '%s'", i + 1, uploadSuccesses.get(i).getFilename()));
            }
            System.out.println("[0] - Cancel");
            int option = ScanUtils.getInputInt("Pick a file:");

            if (option == 0)
                return null;
            if (option < 0 || option > uploadSuccesses.size()) {
                System.out.println("Invalid option");
                continue;
            }
            return uploadSuccesses.get(option - 1);
        }
    }

    @Override
    public String printSelectLanguage() {
        return ScanUtils.getInputString("Language to Translate:");
    }

    @Override
    public void printNotLoggedIn() {
        System.out.println("Log in first!");
    }

    @Override
    public String printUsernameInput() {
        return ScanUtils.getInputString("Username:");
    }

    @Override
    public String printPasswordInput() {
        return ScanUtils.getInputString("Password:");
    }

    @Override
    public void printSuccessfulLogin(String user) {
        System.out.println("Successfully logged in as '" + user + "'");
    }

    @Override
    public void printFailedLogin(String reason) {
        System.out.println("Failed to login! Reason: '" + reason + "'");
    }

    @Override
    public Path printPathInput() {
        return Paths.get(ScanUtils.getInputString("File Path:"));
    }

    @Override
    public void printSuccessfulLogout(String status) {
        System.out.println("Logged out with status '" + status + "'");
    }

    @Override
    public int printViewMenuSelection() {
        return ScanUtils.getInputInt(
            "------------------\n" +
                "Pick an option:\n" +
                "[1] - Translations: Successes\n" +
                "[2] - Translations: Ongoing\n" +
                "[3] - Translations: All\n" +
                "[4] - Upload: Successes\n" +
                "[5] - Upload: Ongoing" +
                "[6] - Upload: All\n" +
                "[0] - Back");
    }

    @Override
    public int PrintMainMenuSelection() {
        return ScanUtils.getInputInt(
            "------------------\n" +
                "Pick an option:\n" +
                "[1] - Login\n" +
                "[2] - Upload\n" +
                "[3] - Translate\n" +
                "[4] - View\n" +
                "[5] - Logout\n" +
                "[0] - Quit");
    }

    @Override
    public void printInvalidOption() {
        System.out.println("Invalid option");
    }
}
