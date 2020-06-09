package clientapp;

import CnText.*;
import clientapp.observers.TranslationRequestObserver;
import clientapp.observers.UploadRequestObserver;
import clientapp.utils.ITranslationRequest;
import clientapp.utils.IUploadRequest;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static CnText.TranslateStatus.TRANSLATE_SUCCESS;
import static CnText.UploadStatus.UPLOAD_SUCCESS;

public class Operations {
    private final ManagedChannel channel;
    private final CnTextGrpc.CnTextStub noBlockStub;
    private final CnTextGrpc.CnTextBlockingStub blockingStub;
    private Session session;
    private ArrayList<UploadRequestObserver> uploadRequests = new ArrayList<>();
    private ArrayList<TranslationRequestObserver> translationRequests = new ArrayList<>();

    public Operations(String svcIP, int svcPort) {
        channel = ManagedChannelBuilder.forAddress(svcIP, svcPort)
                .usePlaintext()
                .build();
        noBlockStub = CnTextGrpc.newStub(channel);
        blockingStub = CnTextGrpc.newBlockingStub(channel);
    }

    public CnText.LoginStatus login(String username, String password) {
        try {
            Session res = blockingStub.start(Login.newBuilder().setUser(username).setPassword(password).build());

            if (res.getStatus() == LoginStatus.LOGIN_SUCCESS)
                this.session = res;

            return res.getStatus();
        }
        catch (Exception e){
            return LoginStatus.LOGIN_COMMUNICATION_ERROR;
        }
    }

    public void upload(Path path) throws IOException {
        byte[] file = Files.readAllBytes(path);
        ByteString bs = ByteString.copyFrom(file);
        UploadRequestObserver urObserver = new UploadRequestObserver(path.getFileName().toString());
        UploadRequest uploadRequest = UploadRequest.newBuilder()
                .setImage(bs)
                .setSessionId(session.getSessionId())
                .build();
        noBlockStub.upload(uploadRequest, urObserver);
        uploadRequests.add(urObserver);
    }

    public void translate(String uploadToken, String filename, String language){
        TranslationRequestObserver trObserver = new TranslationRequestObserver(uploadToken, filename, language);
        TranslateRequest translateRequest = TranslateRequest.newBuilder()
                .setUploadToken(uploadToken)
                .setLanguage(language)
                .setSessionId(session.getSessionId())
                .build();
        noBlockStub.translate(translateRequest, trObserver);
        translationRequests.add(trObserver);
    }

    public LogoutStatus logout() {
        CloseResponse closeResponse = blockingStub.close(session);
        session = null;
        return closeResponse.getStatus();
    }

    public boolean isLogged() {
        return session != null;
    }

    public String getUser() {
        if(session == null)
            return null;
        else
            return session.getUser();
    }

    public ArrayList<ITranslationRequest> getTranslationSuccesses() {
        ArrayList<ITranslationRequest> res = new ArrayList<>();
        for (TranslationRequestObserver req : translationRequests) {
            if(req.getStatus() == TRANSLATE_SUCCESS)
                res.add(req);
        }
        return res;
    }

    public ArrayList<ITranslationRequest> getTranslationOngoing() {
        ArrayList<ITranslationRequest> res = new ArrayList<>();
        for (TranslationRequestObserver req : translationRequests) {
            if(!req.isCompleted())
                res.add(req);
        }
        return res;
    }

    public ArrayList<ITranslationRequest> getTranslationAllRequests() {
        ArrayList<ITranslationRequest> res = new ArrayList<>(translationRequests);
        return res;
    }

    public ArrayList<IUploadRequest> getUploadSuccesses() {
        ArrayList<IUploadRequest> res = new ArrayList<>();
        for (UploadRequestObserver req : uploadRequests) {
            if(req.getStatus() == UPLOAD_SUCCESS)
                res.add(req);
        }
        return res;
    }

    public ArrayList<IUploadRequest> getUploadOngoing() {
        ArrayList<IUploadRequest> res = new ArrayList<>();
        for (UploadRequestObserver req : uploadRequests) {
            if(!req.isCompleted())
                res.add(req);
        }
        return res;
    }

    public ArrayList<IUploadRequest> getUploadAllRequests() {
        ArrayList<IUploadRequest> res = new ArrayList<>(uploadRequests);
        return res;
    }
}
