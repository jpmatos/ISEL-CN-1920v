package serviceapp.observer;

import CnText.CheckRequest;
import CnText.CheckResponse;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import io.grpc.stub.StreamObserver;
import serviceapp.dao.TextOfImage;
import serviceapp.util.SessionManager;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static utils.Output.log;

public class CheckRequestObserver implements StreamObserver<CheckRequest> {
    private final StreamObserver<CheckResponse> responseObserver;
    private final SessionManager sessionManager;
    private final Firestore db;
    private final String firestoreCollectionName;
    private ArrayList<String> infos = new ArrayList<>();

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
            log(String.format("Invalid session '%s'.", sessionID));
            infos.add(String.format("[%s] Invalid Session", uploadToken));
            return;
        }

        log(String.format("Check onNext() called with session '%s'.", sessionID));
        try {
            DocumentReference docRef = db.collection(firestoreCollectionName).document(uploadToken);
            DocumentSnapshot doc = docRef.get().get();
            TextOfImage textOfImage = doc.toObject(TextOfImage.class);
            if (textOfImage != null) {
                log(String.format("Check for upload token '%s'. Info: '%s'.", uploadToken, textOfImage.toString()));
                infos.add(String.format("[%s] " + textOfImage.toString(), uploadToken));
            }
            else {
                log(String.format("Failed to check for upload token '%s'.", uploadToken));
                infos.add(String.format("[%s] Failed to check upload token in DB", uploadToken));
            }
        } catch (InterruptedException | ExecutionException e) {{
            log(String.format("Failed to check for upload token '%s'.", uploadToken));
            infos.add(String.format("[%s] Failed to check upload token in DB", uploadToken));
            }
        }
    }

    @Override
    public void onError(Throwable throwable) {
        log("Check client error");
        responseObserver.onCompleted();
    }

    @Override
    public void onCompleted() {
        log("Check complete.");
        CheckResponse checkResponse = CheckResponse.newBuilder()
                .addAllResponse(infos)
                .build();
        responseObserver.onNext(checkResponse);
        responseObserver.onCompleted();
    }
}
