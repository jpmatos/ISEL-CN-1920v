package serviceapp;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import io.grpc.ServerBuilder;
import serviceapp.util.Logger;

public class Service{
    private static int svcPort=8000;

    public static void main(String[] args) {
        Logger.init();
        Logger.log("Starting Service...");

        try {
            GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
            FirestoreOptions options = FirestoreOptions.newBuilder().setCredentials(credentials).build();
            Firestore db = options.getService();
            Storage storage = StorageOptions.getDefaultInstance().getService();
            StorageOptions storageOptions = StorageOptions.getDefaultInstance();
            String projectID = storageOptions.getProjectId();

            Logger.log("Current Project ID:" + projectID);
            Operations operations = new Operations(db, storage);
            io.grpc.Server svc = ServerBuilder
                    .forPort(svcPort)
                    .addService(operations)
                    .build();

            svc.start();
            Logger.log(String.format("Service started. Now Listening on port '%d'...", svcPort));

            svc.awaitTermination();

            svc.shutdown();
            Logger.log("Service successfully shut down.");
        }
        catch (Exception ex) {
            Logger.log("Service Exception. Shutting down...");
            ex.printStackTrace();
        }
    }
}
