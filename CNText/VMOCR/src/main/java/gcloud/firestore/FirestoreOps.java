package gcloud.firestore;

import static utils.Console.PrintType.WARNING;
import static utils.Console.print;

public class FirestoreOps implements IFirestoreOps {
    @Override
    public boolean storeOCRResult(String token, String OCRResult) throws UnsupportedOperationException {
        //TODO
        print(WARNING, "Store OCR not Implemented");
        return false;
    }
}
