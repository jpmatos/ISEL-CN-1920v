package clientapp.interfaces;

import CnText.CheckRequest;
import CnText.LogoutStatus;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public interface IOperations {
    CnText.LoginStatus login(String username, String password);
    void upload(Path path, String languages) throws IOException;
    void process(String uploadToken, String filename, String language);
    LogoutStatus logout();
    boolean isLogged();
    String getUser();
    ArrayList<IProcessRequest> getProcessRequests();
    ArrayList<IUploadRequest> getUploadRequests();
    StreamObserver<CheckRequest> check();
    void sendCheckRequest(StreamObserver<CheckRequest> check, String uploadToken);
    String getSessionId();
}
