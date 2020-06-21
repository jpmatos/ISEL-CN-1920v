package gcloud.firestore;

import java.util.concurrent.ExecutionException;

public interface IFirestoreOps {
    boolean storeTranslatedTextResult(String id, String translateResult, String language,String locale,String originalText,String error) throws ExecutionException, InterruptedException;
}

