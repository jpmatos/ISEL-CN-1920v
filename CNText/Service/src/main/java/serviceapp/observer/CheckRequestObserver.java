package serviceapp.observer;

import CnText.CheckRequest;
import CnText.CheckResponse;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import serviceapp.dao.TextOfImage;
import io.grpc.stub.StreamObserver;
import serviceapp.util.Logger;
import serviceapp.util.SessionManager;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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
            Logger.log(String.format("Invalid session '%s'.", sessionID));
            infos.add(String.format("[%s] Invalid Session", uploadToken));
            return;
        }

        Logger.log(String.format("Check onNext() called with session '%s'.", sessionID));
        try {
            DocumentReference docRef = db.collection(firestoreCollectionName).document(uploadToken);
            DocumentSnapshot doc = docRef.get().get();
            TextOfImage textOfImage = doc.toObject(TextOfImage.class);
            if (textOfImage != null) {
                Logger.log(String.format("Check for upload token '%s'. Info: '%s'.", uploadToken, textOfImage.toString()));
                infos.add(String.format("[%s] " + textOfImage.toString(), uploadToken));
            }
            else {
                Logger.log(String.format("Failed to check for upload token '%s'.", uploadToken));
                infos.add(String.format("[%s] Failed to check upload token in DB", uploadToken));
            }
        } catch (InterruptedException | ExecutionException e) {{
            Logger.log(String.format("Failed to check for upload token '%s'.", uploadToken));
            infos.add(String.format("[%s] Failed to check upload token in DB", uploadToken));
            }
        }
    }

    @Override
    public void onError(Throwable throwable) {
        Logger.log("Check client error");
        responseObserver.onCompleted();
    }

    @Override
    public void onCompleted() {
        Logger.log("Check complete.");
        CheckResponse checkResponse = CheckResponse.newBuilder()
                .addAllResponse(infos)
                .build();
        responseObserver.onNext(checkResponse);
        responseObserver.onCompleted();
    }
}
