package observer;

import CnText.CheckRequest;
import CnText.CheckResponse;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import dao.TextOfImage;
import io.grpc.stub.StreamObserver;
import serviceapp.SessionManager;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CheckRequestObserver implements StreamObserver<CheckRequest> {
    private final StreamObserver<CheckResponse> responseObserver;
    private final SessionManager sessionManager;
    private final Firestore db;
    private final String firestoreCollectionName;
    ArrayList<String> infos = new ArrayList<>();

    public CheckRequestObserver(StreamObserver<CheckResponse> responseObserver, SessionManager sessionManager, Firestore db, String firestoreCollectionName) {
        this.responseObserver = responseObserver;
        this.sessionManager = sessionManager;
        this.db = db;
        this.firestoreCollectionName = firestoreCollectionName;
    }

    @Override
    public void onNext(CheckRequest checkRequest) {
        String sessionID = checkRequest.getSessionId();
        String uploadToken = checkRequest.getUploadToken();

        //Validate Session
        if(!sessionManager.isValid(sessionID)){
            infos.add(String.format("[%s] Invalid Session", uploadToken));
            return;
        }

        try {
            DocumentReference docRef = db.collection(firestoreCollectionName).document(uploadToken);
            DocumentSnapshot doc = docRef.get().get();
            TextOfImage textOfImage = doc.toObject(TextOfImage.class);
            if (textOfImage != null)
                infos.add(String.format("[%s] " + textOfImage.toString(), uploadToken));
            else
                infos.add(String.format("[%s] Failed to check upload token in DB", uploadToken));
        } catch (InterruptedException | ExecutionException e) {
            infos.add(String.format("[%s] Failed to check upload token in DB", uploadToken));
        }
    }

    @Override
    public void onError(Throwable throwable) {
        responseObserver.onCompleted();
    }

    @Override
    public void onCompleted() {
        CheckResponse checkResponse = CheckResponse.newBuilder()
                .addAllResponse(infos)
                .build();
        responseObserver.onNext(checkResponse);
        responseObserver.onCompleted();
    }
}
