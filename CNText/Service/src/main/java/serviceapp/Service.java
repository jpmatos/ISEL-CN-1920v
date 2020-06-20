package serviceapp;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import io.grpc.ServerBuilder;

import static utils.Output.OutputType.ERROR;
import static utils.Output.log;

public class Service{
    private static int svcPort=8000;

    public static void main(String[] args) {
        log("Starting Service...");
        int pollingTime = 60;
        if(args.length > 0)
            pollingTime = Integer.parseInt(args[0]);

        try {
            GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
            FirestoreOptions options = FirestoreOptions.newBuilder().setCredentials(credentials).build();
            Firestore db = options.getService();
            Storage storage = StorageOptions.getDefaultInstance().getService();
            StorageOptions storageOptions = StorageOptions.getDefaultInstance();
            String projectID = storageOptions.getProjectId();

            log("Current Project ID:" + projectID);
            Operations operations = new Operations(db, storage, pollingTime);
            io.grpc.Server svc = ServerBuilder
                    .forPort(svcPort)
                    .addService(operations)
                    .build();

            svc.start();
            log(String.format("Service started. Now Listening on port '%d'...", svcPort));

            svc.awaitTermination();

            svc.shutdown();
            log("Service successfully shut down.");
        }
        catch (Exception ex) {
            log(ERROR, "Service Exception. Shutting down...");
            ex.printStackTrace();
        }
    }
}
