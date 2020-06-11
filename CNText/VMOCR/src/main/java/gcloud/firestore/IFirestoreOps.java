package gcloud.firestore;

public interface IFirestoreOps {
    boolean storeOCRResult(String token, String OCRResult) throws UnsupportedOperationException;
}
