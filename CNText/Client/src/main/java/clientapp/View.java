package clientapp;

import clientapp.interfaces.IProcessRequest;
import clientapp.interfaces.IUploadRequest;
import clientapp.interfaces.IView;
import clientapp.utils.ScanUtils;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;

import static CnText.ProcessStatus.READING_SUCCESS;
import static CnText.ProcessStatus.TRANSLATE_SUCCESS;
import static CnText.UploadStatus.UPLOAD_SUCCESS;

public class View implements IView {
    @Override
    public void printProcessSuccesses(ArrayList<IProcessRequest> requests) {
        for (IProcessRequest req : requests) {
            if (req.isCompleted() && req.getStatus() == READING_SUCCESS)
                System.out.println(String.format("[%s] File: '%s'. Text: '%s'.",
                        req.getUploadToken(), req.getFilename(), req.getText()));
            if (req.isCompleted() && req.getStatus() == TRANSLATE_SUCCESS)
                System.out.println(String.format("[%s] File: '%s'. Text: '%s'. To Language: '%s'. Translation: '%s'.",
                        req.getUploadToken(), req.getFilename(), req.getText(), req.getTranslation(), req.getTranslation()));
        }
    }

    @Override
    public void printProcessOngoing(ArrayList<IProcessRequest> requests) {
        for (IProcessRequest req : requests) {
            if (!req.isCompleted())
                System.out.println(String.format("[%s] File: '%s'. Status: '%s'.",
                        req.getUploadToken(), req.getFilename(), req.getStatus()));
        }
    }

    @Override
    public void printProcessAllRequests(ArrayList<IProcessRequest> requests) {
        for (IProcessRequest req : requests) {
            System.out.println(String.format("[%s] File: '%s'. Status: '%s'.",
                    req.getUploadToken(), req.getFilename(), req.getStatus()));
        }
    }

    @Override
    public void printUploadSuccesses(ArrayList<IUploadRequest> requests) {
        for (IUploadRequest req : requests) {
            if (req.isCompleted() && req.getStatus() == UPLOAD_SUCCESS)
                System.out.println(String.format("[%s] File: '%s'.",
                        req.getUploadToken(), req.getFilename()));
        }
    }

    @Override
    public void printUploadOngoing(ArrayList<IUploadRequest> requests) {
        for (IUploadRequest req : requests) {
            if (!req.isCompleted())
                System.out.println(String.format("[%s] File: '%s'. Status: '%s'.",
                        req.getUploadToken(), req.getFilename(), req.getStatus()));
        }
    }

    @Override
    public void printUploadAllRequests(ArrayList<IUploadRequest> requests) {
        for (IUploadRequest req : requests) {
            System.out.println(String.format("[%s] File: '%s'. Status: '%s'.",
                    req.getUploadToken(), req.getFilename(), req.getStatus()));
        }
    }

    @Override
    public String printSelectLanguage() {
        return ScanUtils.getInputString("Language to translate to:");
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
        System.out.println("Successfully logged in as '" + user + "'.");
    }

    @Override
    public void printFailedLogin(String reason) {
        System.out.println("Failed to login! Reason: '" + reason + "'.");
    }

    @Override
    public String printPathInput() {
        return ScanUtils.getInputString("File Path:");
    }

    @Override
    public void printSuccessfulLogout(String status) {
        System.out.println("Logged out with status '" + status + "'.");
    }

    @Override
    public int printViewMenuSelection() {
        String oper = "";
        while (oper.equals(""))
            oper = ScanUtils.getInputString(
                    "------------------\n" +
                            "Pick an option:\n" +
                            "[1] - Process: Successes\n" +
                            "[2] - Process: Ongoing\n" +
                            "[3] - Process: All\n" +
                            "[4] - Upload: Successes\n" +
                            "[5] - Upload: Ongoing\n" +
                            "[6] - Upload: All\n" +
                            "[0] - Back");
        if (isNumeric(oper))
            return Integer.parseInt(oper);
        else
            return -1;
    }

    @Override
    public int PrintMainMenuSelection() {
        String oper = "";
        while (oper.equals(""))
            oper = ScanUtils.getInputString(
                    "------------------\n" +
                            "Pick an option:\n" +
                            "[1] - Login\n" +
                            "[2] - Upload and Process image\n" +
                            "[3] - Check upload tokens\n" +
                            "[4] - Consult session's requests\n" +
                            "[5] - Logout\n" +
                            "[0] - Quit");
        if (isNumeric(oper))
            return Integer.parseInt(oper);
        else
            return -1;
    }

    @Override
    public void printInvalidOption() {
        System.out.println("Invalid option.");
    }

    @Override
    public String printUploadTokenInput() {
        return ScanUtils.getInputString("Upload Token:");
    }

    @Override
    public void printLoggedAs(String user, String sessionId) {
        System.out.println(String.format("\nLogged in as '%s' - Session: '%s'", user, sessionId));
    }

    @Override
    public void printWelcomeMessage() {
        System.out.println("Welcome to CNText!\n" +
                "Login to start reading and translating images.\n" +
                "Check the supported image formats here: https://cloud.google.com/vision/docs/supported-files");
    }

    @Override
    public String printIPInput(String svcIP) {
        return ScanUtils.getInputString("Svc IP:", svcIP);
    }

    @Override
    public int printPort(int svcPort) {
        return ScanUtils.getInputInt("Svc port:", svcPort);
    }

    private static boolean isNumeric(String str) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }
}
