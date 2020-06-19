package clientapp;

import CnText.*;
import clientapp.interfaces.IOperations;
import clientapp.interfaces.IProcessRequest;
import clientapp.interfaces.IUploadRequest;
import clientapp.observer.CheckRequestObserver;
import clientapp.observer.ProcessRequestObserver;
import clientapp.observer.UploadRequestObserver;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class Operations implements IOperations {
    private final ManagedChannel channel;
    private final CnTextGrpc.CnTextStub noBlockStub;
    private final CnTextGrpc.CnTextBlockingStub blockingStub;
    private Session session;
    private ArrayList<UploadRequestObserver> uploadRequests = new ArrayList<>();
    private ArrayList<ProcessRequestObserver> processRequests = new ArrayList<>();
    private final String[] supportedMIMETypes =
            {"image/jpeg", "image/png", "image/gif", "image/bmp", "image/webp", "image/x-dcraw",
                    "image/vnd.microsoft.icon", "application/pdf", "image/tiff"};
    private final String[] supportedExtensions =
            {"jpg", "jpeg", "png", "gif", "bmp", "webp", "raw", "ico", "pdf", "tif", "tiff"};

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
        } catch (Exception e) {
            return LoginStatus.LOGIN_COMMUNICATION_ERROR;
        }
    }

    @Override
    public void upload(Path path, String languages) throws IOException {

        //Check if file exists
        File file = path.toFile();
        if (!file.exists()) {
            System.out.println("File does not exist");
            return;
        }

        //Get filename, extension and MIME
        String filename = path.getFileName().toString();
        String mimeType = Files.probeContentType(path);
        String extension = "";
        int i = path.getFileName().toString().lastIndexOf('.');
        if (i > 0)
            extension = path.getFileName().toString().substring(i + 1);

        if (!Arrays.asList(supportedMIMETypes).contains(mimeType)) {
            System.out.println("Unsupported MIME type " + mimeType);
            return;
        }

        if (!Arrays.asList(supportedExtensions).contains(extension)) {
            System.out.println("Unsupported extension " + extension);
            return;
        }

        UploadRequestObserver urObserver = new UploadRequestObserver(path.getFileName().toString(), languages, this);
        uploadRequests.add(urObserver);
        StreamObserver<UploadRequest> requestObserver = noBlockStub.upload(urObserver);

        try {
            //Send file to Service
            BufferedInputStream bInputStream = new BufferedInputStream(new FileInputStream(file));
            int bufferSize = 512 * 1024; // 512k
            byte[] buffer = new byte[bufferSize];
            int size;
            while ((size = bInputStream.read(buffer)) > 0) {
                ByteString byteString = ByteString.copyFrom(buffer, 0, size);
                UploadRequest req = UploadRequest.newBuilder()
                        .setSessionId(session.getSessionId())
                        .setImage(byteString)
                        .setMime(mimeType)
                        .setFilename(filename)
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

    @Override
    public void process(String uploadToken, String filename, String language) {
        ProcessRequestObserver trObserver = new ProcessRequestObserver(uploadToken, filename, language);
        ProcessRequest trRequest = ProcessRequest.newBuilder()
                .setSessionId(session.getSessionId())
                .setUploadToken(uploadToken)
                .setLanguage(language)
                .setPublish(true)
                .build();
        noBlockStub.process(trRequest, trObserver);
        processRequests.add(trObserver);
    }

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
        if (session == null)
            return null;
        else
            return session.getUser();
    }

    @Override
    public String getSessionId() {
        return session.getSessionId();
    }

    @Override
    public ArrayList<IProcessRequest> getProcessRequests() {
        ArrayList<IProcessRequest> res = new ArrayList<>(processRequests);
        return res;
    }

    @Override
    public ArrayList<IUploadRequest> getUploadRequests() {
        ArrayList<IUploadRequest> res = new ArrayList<>(uploadRequests);
        return res;
    }

    @Override
    public StreamObserver<CheckRequest> check() {
        CheckRequestObserver crObserver = new CheckRequestObserver();
        return noBlockStub.check(crObserver);
    }

    @Override
    public void sendCheckRequest(StreamObserver<CheckRequest> check, String uploadToken) {
        CheckRequest checkRequest = CheckRequest.newBuilder()
                .setSessionId(session.getSessionId())
                .setUploadToken(uploadToken)
                .build();
        check.onNext(checkRequest);
    }

}
