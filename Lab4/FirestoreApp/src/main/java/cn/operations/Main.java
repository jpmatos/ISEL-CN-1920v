package cn.operations;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try{
            GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
            FirestoreOptions options = FirestoreOptions.newBuilder().setCredentials(credentials).build();
            Firestore db = options.getService();
            Operations operations = new Operations(db);

            while (true) {
                System.out.println(
                        "------------------\n" +
                                "Pick an operation:\n" +
                                "[1] - Insert Document\n" +
                                "[2] - Show Document by ID\n" +
                                "[3] - Delete Document by ID and Name\n" +
                                "[4] - Get Freguesia\n" +
                                "[5] - Get Document Complex\n" +
                                "[6] - \n" +
                                "[0] - Quit");
                int oper = sc.nextInt();

                switch (oper) {
                    case 1:
                        operations.insertDocuments();
                        break;
                    case 2:
                        showDocumentID(operations);
                        break;
                    case 3:
                        deleteDocumentIDFieldName(operations);
                        break;
                    case 4:
                        getDocumentsByFreguesia(operations);
                        break;
                    case 5:
                        getDocumentsGreaterIDByFreguesiaByEvent(operations);
                        break;
                    case 0:
                        System.exit(0);
                    default:
                        System.out.println("Unknown operation");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void getDocumentsGreaterIDByFreguesiaByEvent(Operations operations) throws ExecutionException, InterruptedException {
        System.out.println("Collection Name:");
        String collectionName = sc.next();

        System.out.println("ID: ");
        int ID = sc.nextInt();

        System.out.println("Freguesia: ");
        String freguesia = sc.next();

        System.out.println("Event: ");
        String event = sc.next();

        operations.getDocumentsGreaterIDByFreguesiaByEvent(collectionName, ID, freguesia, event);
    }

    private static void getDocumentsByFreguesia(Operations operations) throws ExecutionException, InterruptedException {
        System.out.println("Collection Name:");
        String collectionName = sc.next();

        System.out.println("Freguesia:");
        String freguesia = sc.next();

        operations.getDocumentByFreguesia(collectionName, freguesia);
    }

    private static void deleteDocumentIDFieldName(Operations operations) throws ExecutionException, InterruptedException {
        System.out.println("Collection Name:");
        String collectionName = sc.next();

        System.out.println("ID:");
        String ID = sc.next();

        System.out.println("Field Name:");
        String fieldName = sc.next();

        operations.deleteDocumentIDFieldName(collectionName, ID, fieldName);
    }

    private static void showDocumentID(Operations operations) throws ExecutionException, InterruptedException {
        System.out.println("Collection Name:");
        String collectionName = sc.next();

        System.out.println("ID:");
        String ID = sc.next();

        operations.showDocumentByID(collectionName, ID);
    }
}
