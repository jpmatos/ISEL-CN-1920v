package gcloud.firestore;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import dao.TranslatedText;

import java.io.IOException;
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
    public boolean storeTranslatedTextResult(String id, String translateResult, String language, String locale, String originalText) {

        TranslatedText translatedText = new TranslatedText();
        translatedText.id = id;
        translatedText.translationResult = translateResult;
        translatedText.locale = locale;
        translatedText.text = originalText;
        translatedText.language = language;

        DocumentReference docRef = colRef.document(id);

        ApiFuture<WriteResult> future = docRef.set(translatedText);
        try {
            Timestamp updateTime = future.get().getUpdateTime();
            log("Translated Text stored at " + updateTime.toString() + " With ID: " + id);
            return true;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

}
