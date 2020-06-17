package gcloud.firestore;

import dao.OCRResult;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IFirestoreOps {
    boolean storeOCRResult(String id, OCRResult ocrResult, String language) throws ExecutionException, InterruptedException;

    List<String> readTextFromId(String id);
}
