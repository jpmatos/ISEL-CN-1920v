package clientapp;

import CnText.*;
import clientapp.interfaces.IOperations;
//import clientapp.interfaces.ITranslationRequest;
import clientapp.interfaces.IUploadRequest;
//import clientapp.observers.TranslationRequestObserver;
import clientapp.observers.UploadRequestObserver;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Operations implements IOperations {
    private final ManagedChannel channel;
    private final CnTextGrpc.CnTextStub noBlockStub;
    private final CnTextGrpc.CnTextBlockingStub blockingStub;
    private Session session;
    private ArrayList<UploadRequestObserver> uploadRequests = new ArrayList<>();
//    private ArrayList<TranslationRequestObserver> translationRequests = new ArrayList<>();

    public Operations(String svcIP, int svcPort) {
        channel = ManagedChannelBuilder.forAddress(svcIP, svcPort)
                .usePlaintext()
                .build();
        noBlockStub = CnTextGrpc.newStub(channel);
        blockingStub = CnTextGrpc.newBlockingStub(channel);
    }

    @Override
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

    @Override
    public void upload(Path path, String languages) {
        UploadRequestObserver urObserver = new UploadRequestObserver(path.getFileName().toString());
        uploadRequests.add(urObserver);
        StreamObserver<UploadRequest> requestObserver = noBlockStub.upload(urObserver);

        try {

            //Check if file exists
            File file = path.toFile();
            if (!file.exists()) {
                System.out.println("File does not exist");
                return;
            }

            //Get file MIME and Extension
            String mimeType = Files.probeContentType(path);
            String extension = "";
            int i = path.getFileName().toString().lastIndexOf('.');
            if (i > 0) {
                extension = path.getFileName().toString().substring(i+1);
            }


            //TODO Verify if valid mime/extension

            //Send file to Service
            BufferedInputStream bInputStream = new BufferedInputStream(new FileInputStream(file));
            int bufferSize = 512 * 1024; // 512k
            byte[] buffer = new byte[bufferSize];
            int size;
            while ((size = bInputStream.read(buffer)) > 0) {
                ByteString byteString = ByteString.copyFrom(buffer, 0, size);
                UploadRequest req = UploadRequest.newBuilder()
                        .setSessionId(session.getSessionId())
                        .setLanguages(languages)
                        .setImage(byteString)
                        .setMime(mimeType)
                        .setExtension(extension)
                        .build();
                requestObserver.onNext(req);
            }
        } catch (RuntimeException | IOException e) {
            requestObserver.onError(e);
            e.printStackTrace();
            return;
        }
        requestObserver.onCompleted();
    }

//    @Override
//    public void translate(String uploadToken, String filename, String language){
//        TranslationRequestObserver trObserver = new TranslationRequestObserver(uploadToken, filename, language);
//        TranslateRequest translateRequest = TranslateRequest.newBuilder()
//                .setUploadToken(uploadToken)
//                .setLanguage(language)
//                .setSessionId(session.getSessionId())
//                .build();
//        noBlockStub.translate(translateRequest, trObserver);
//        translationRequests.add(trObserver);
//    }

    @Override
    public LogoutStatus logout() {
        CloseResponse closeResponse = blockingStub.close(session);
        session = null;
        return closeResponse.getStatus();
    }

    @Override
    public boolean isLogged() {
        return session != null;
    }

    @Override
    public String getUser() {
        if(session == null)
            return null;
        else
            return session.getUser();
    }

//    @Override
//    public ArrayList<ITranslationRequest> getTranslationRequests(){
//        ArrayList<ITranslationRequest> res = new ArrayList<>(translationRequests);
//        return res;
//    }

    @Override
    public ArrayList<IUploadRequest> getUploadRequests() {
        ArrayList<IUploadRequest> res = new ArrayList<>(uploadRequests);
        return res;
    }
}
