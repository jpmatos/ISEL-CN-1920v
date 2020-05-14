import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private final static String PROJECT_ID = "g01-li61n";

    public static void main(String[] args) throws IOException {

        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
        FirestoreOptions options = FirestoreOptions.newBuilder().setCredentials(credentials).build();
        Firestore db = options.getService();

        String subsName = ScanUtils.getInputString("Sub Name:");
        String collectionName = ScanUtils.getInputString("Collection Name:");


        ProjectSubscriptionName projSubName =
                ProjectSubscriptionName.of(PROJECT_ID, subsName);

        Subscriber subscriber =
                Subscriber.newBuilder(projSubName, new MessageReceiveHandler(db.collection(collectionName))).build();

        System.out.println("Listening... Press Enter to exit.");
        subscriber.startAsync().awaitRunning();
        sc.nextLine();
    }
}
