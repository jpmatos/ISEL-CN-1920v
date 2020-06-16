package serviceapp;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import io.grpc.ServerBuilder;

import java.util.List;
import java.util.Scanner;

public class Service{
    private static int svcPort=8000;

    public static void main(String[] args) {

        try {
            GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
            FirestoreOptions options = FirestoreOptions.newBuilder().setCredentials(credentials).build();
            Firestore db = options.getService();
            Storage storage = StorageOptions.getDefaultInstance().getService();

            StorageOptions storageOptions = StorageOptions.getDefaultInstance();
            String projectID = storageOptions.getProjectId();
            if (projectID != null)
                System.out.println("Current Project ID:" + projectID);
            else
                System.out.println("Error getting storage");

            Operations operations = new Operations(db, storage);
            io.grpc.Server svc = ServerBuilder
                    .forPort(svcPort)
                    .addService(operations)
                    .build();

            svc.start();
            System.out.println("Server started, listening on " + svcPort);

            Scanner scan=new Scanner(System.in);
            boolean cont = true;
            while (cont){
                System.out.println("------------------\n" +
                        "Pick an option:\n" +
                        "[1] - Print Active Sessions\n" +
                        "[0] - Exit");
                int oper = scan.nextInt();

                switch (oper){
                    case 1:
                        printActiveSessions(operations);
                        break;
                    default:
                        cont = false;
                }
            }
            svc.shutdown();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void printActiveSessions(Operations operations) {
        List<SessionManager.Session> sessions = operations.getActiveSessions();
        System.out.println("Active Sessions:");
        for (SessionManager.Session session : sessions) {
            System.out.println(session.getID() + " - " + session.getPremium());
        }
    }
}
