package gcloud.firestore;

import java.util.concurrent.ExecutionException;

public interface IFirestoreOps {
    boolean storeTranslatedTextResult(String id, String translateResult, String language,String locale,String originalText) throws ExecutionException, InterruptedException;
}

