import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Main {
    private final static String projectID = "g01-li61n";

    public static void main(String[] args) {

        try {

            GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
            FirestoreOptions options = FirestoreOptions.newBuilder().setCredentials(credentials).build();
            Firestore db = options.getService();

            String caixaName = ScanUtils.getInputString("Caixa Name:");
            String collection = ScanUtils.getInputString(("Collection: (for queries)"));

            Operations operations = new Operations(projectID, caixaName, db.collection(collection));
            while (true) {
                int oper = ScanUtils.getInputInt(
                        "------------------\n" +
                                "Pick an operation:\n" +
                                "[1] - Create Topic\n" +
                                "[2] - List Topics\n" +
                                "[3] - Publish Message\n" +
                                "[4] - Query: Caixa Sales Volume\n" +
                                "[5] - Query: Sales with Total above Amount\n" +
                                "[6] - Query: Sales with given Item\n" +
                                "[0] - Quit");

                switch (oper) {
                    case 1:
                        createTopic(operations);
                        break;
                    case 2:
                        listTopics(operations);
                        break;
                    case 3:
                        publishMessage(operations);
                        break;
                    case 4:
                        queryCaixaSalesVolume(operations);
                        break;
                    case 5:
                        querySalesAboveAmount(operations);
                        break;
                    case 6:
                        querySalesWithItem(operations);
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

    private static void querySalesWithItem(Operations operations) throws ExecutionException, InterruptedException {
        String item = ScanUtils.getInputString("Item:");

        operations.querySalesWithItem(item);
    }

    private static void querySalesAboveAmount(Operations operations) throws ExecutionException, InterruptedException {
        double amount = ScanUtils.getInputDouble("Amount: ");

        operations.querySalesAboveAmount(amount);
    }

    private static void queryCaixaSalesVolume(Operations operations) throws ExecutionException, InterruptedException {
        String caixa = ScanUtils.getInputString("Caixa:");

        operations.queryCaixaSalesVolume(caixa);
    }

    private static void publishMessage(Operations operations) throws IOException, ExecutionException, InterruptedException {
        String topicName = ScanUtils.getInputString("Topic Name:");
        String message = ScanUtils.getInputString("Message:");
        String item = ScanUtils.getInputString("Item:");
        double quant = ScanUtils.getInputDouble("Quant:");
        double precoUnit = ScanUtils.getInputDouble("PreçoUnit:");
        double total = precoUnit * quant;

        operations.publishMessage(topicName, message, item, quant, precoUnit, total);
    }

    private static void listTopics(Operations operations) throws IOException {
        operations.listTopics();
    }

    private static void createTopic(Operations operations) throws IOException {
        String topicName = ScanUtils.getInputString("Topic Name:");

        operations.createTopic(topicName);
    }


}
