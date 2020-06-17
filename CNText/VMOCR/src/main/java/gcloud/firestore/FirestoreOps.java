package gcloud.firestore;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import dao.OCRResult;
import dao.TextOfImage;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static utils.Output.log;

public class FirestoreOps implements IFirestoreOps {
    private final String COLLECTION = "TextOfImages";
    private final Firestore db;
    private final CollectionReference colRef;

    public FirestoreOps() throws IOException {
        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
        FirestoreOptions options = FirestoreOptions.newBuilder()
                .setCredentials(credentials)
                .build();
        this.db = options.getService();
        this.colRef = db.collection(COLLECTION);
    }


    @Override
    public boolean storeOCRResult(String id, OCRResult ocrResult, String language) {
        TextOfImage textOfImage = new TextOfImage();
        textOfImage.id = id;
        textOfImage.language = language;
        textOfImage.locale = ocrResult.getLocale();
        textOfImage.text = ocrResult.getResult();

        DocumentReference docRef = colRef.document(id);
        ApiFuture<WriteResult> future = docRef.set(textOfImage);
        try {
            Timestamp updateTime = future.get().getUpdateTime();
            log("OCR Result stored at " + updateTime.toString());
            return true;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<String> readTextFromId(String id) {
        //TODO
        return null;
    }
}
