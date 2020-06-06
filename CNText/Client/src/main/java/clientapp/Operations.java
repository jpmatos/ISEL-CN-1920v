package clientapp;

import CnText.*;
import clientapp.observers.TranslateRequestObserver;
import clientapp.observers.UploadRequestObserver;
import clientapp.utils.CompletedTranslations;
import clientapp.utils.CompletedUploads;
import clientapp.utils.IOpers;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Operations implements IOpers {
    private final ManagedChannel channel;
    private final CnTextGrpc.CnTextStub noBlockStub;
    private final CnTextGrpc.CnTextBlockingStub blockingStub;
    private Session session;
    private ArrayList<CompletedUploads> completedUploads = new ArrayList<>();
    private ArrayList<CompletedTranslations> completedTranslations = new ArrayList<>();

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

            if (res.getStatus() == LoginStatus.SUCCESS)
                this.session = res;

            return res.getStatus();
        }
        catch (Exception e){
            return LoginStatus.COMMUNICATION_ERROR;
        }
    }

    public void upload(Path path) throws IOException {
        byte[] file = Files.readAllBytes(path);
        ByteString bs = ByteString.copyFrom(file);
        UploadRequestObserver urObserver = new UploadRequestObserver(this, path.getFileName().toString());
        noBlockStub.upload(UploadRequest.newBuilder().setImage(bs).setSessionId(session.getSessionId()).build(), urObserver);
    }

    public void translate(String uploadToken, String language){
        TranslateRequest translateRequest = TranslateRequest.newBuilder()
                .setUploadToken(uploadToken)
                .setLanguage(language)
                .setSessionId(session.getSessionId())
                .build();
        TranslateRequestObserver trObserver = new TranslateRequestObserver(this, uploadToken, language);
        noBlockStub.translate(translateRequest, trObserver);
    }

    public void logout() {
        //TODO Rework this response into a enum like in login
        CloseResponse closeResponse = blockingStub.close(session);
        session = null;
    }

    @Override
    public void addToCompletedObserversList(UploadRequestObserver urObserver) {
        completedUploads.add(new CompletedUploads(urObserver.getFilename(), urObserver.getUploadToken()));
    }

    @Override
    public void addToCompletedTranslationsList(TranslateRequestObserver trObserver) {
        completedTranslations.add(new CompletedTranslations(trObserver.getUploadToken(), trObserver.getTranslation(), trObserver.getLanguage()));
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

    public ArrayList<CompletedUploads> getCompletedUploads() {
        return completedUploads;
    }

    public ArrayList<CompletedTranslations> getCompletedTranslations() {
        return completedTranslations;
    }
}
