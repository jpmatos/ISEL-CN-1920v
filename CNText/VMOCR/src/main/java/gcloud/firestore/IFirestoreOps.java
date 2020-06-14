package gcloud.firestore;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IFirestoreOps {
    boolean storeOCRResult(String id, String ocrResult, String language) throws ExecutionException, InterruptedException;

    List<String> readTextFromId(String id);
}
