package clientapp;

import clientapp.interfaces.IProcessRequest;
import clientapp.interfaces.IUploadRequest;
import clientapp.interfaces.IView;
import clientapp.utils.ScanUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static CnText.ProcessStatus.TRANSLATE_SUCCESS;
import static CnText.UploadStatus.UPLOAD_SUCCESS;

public class View implements IView {
    @Override
    public void printProcessSuccesses(ArrayList<IProcessRequest> requests) {
        ArrayList<IProcessRequest> processSuccesses = new ArrayList<>();
        for (IProcessRequest req : requests) {
            if(req.getStatus() == TRANSLATE_SUCCESS)
                processSuccesses.add(req);
        }

        for (int i = 0; i < processSuccesses.size(); i++) {
            System.out.println(String.format("[%d] - '%s' to '%s'",
                    i + 1, processSuccesses.get(i).getFilename(), processSuccesses.get(i).getLanguage()));
        }
        System.out.println("[0] - Cancel");
        int option = ScanUtils.getInputInt("Pick a process:");

        if(option == 0)
            return;
        if(option < 0 || option > processSuccesses.size()){
            System.out.println("Invalid option");
            return;
        }
        IProcessRequest req = processSuccesses.get(option - 1);
        System.out.println(String.format("[%s] - '%s'", req.getUploadToken(),  req.getTranslation()));
    }

    @Override
    public void printProcessOngoing(ArrayList<IProcessRequest> requests) {
        for (IProcessRequest req : requests) {
            if(!req.isCompleted())
                System.out.println(String.format("[%s] - '%s'; Status: '%s'",
                        req.getUploadToken(), req.getFilename(), req.getStatus()));
        }
    }

    @Override
    public void printProcessAllRequests(ArrayList<IProcessRequest> requests) {
        for (IProcessRequest req : requests) {
            System.out.println(String.format("[%s] - '%s'; Status: '%s'",
                    req.getUploadToken(), req.getFilename(), req.getStatus()));
        }
    }

    @Override
    public void printUploadSuccesses(ArrayList<IUploadRequest> requests) {
        for (IUploadRequest req : requests) {
            if(req.isCompleted() && req.getStatus() == UPLOAD_SUCCESS)
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

//    @Override
//    public IUploadRequest printUploadSuccessesPicker(ArrayList<IUploadRequest> requests) {
//        ArrayList<IUploadRequest> uploadSuccesses = new ArrayList<>();
//        for (IUploadRequest req : requests) {
//            if(req.getStatus() == UPLOAD_SUCCESS)
//                uploadSuccesses.add(req);
//        }
//        while (true) {
//            for (int i = 0; i < uploadSuccesses.size(); i++) {
//                System.out.println(String.format("[%d] - '%s'", i + 1, uploadSuccesses.get(i).getFilename()));
//            }
//            System.out.println("[0] - Cancel");
//            int option = ScanUtils.getInputInt("Pick a file:");
//
//            if (option == 0)
//                return null;
//            if (option < 0 || option > uploadSuccesses.size()) {
//                System.out.println("Invalid option");
//                continue;
//            }
//            return uploadSuccesses.get(option - 1);
//        }
//    }

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
                "[1] - Process: Successes\n" +
                "[2] - Process: Ongoing\n" +
                "[3] - Process: All\n" +
                "[4] - Upload: Successes\n" +
                "[5] - Upload: Ongoing\n" +
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
                "[3] - Check\n" +
                "[4] - Views\n" +
                "[5] - Logout\n" +
                "[0] - Quit");
    }

    @Override
    public void printInvalidOption() {
        System.out.println("Invalid option");
    }

    @Override
    public String printUploadTokenInput() {
        return ScanUtils.getInputString("Upload Token:");
    }
}
