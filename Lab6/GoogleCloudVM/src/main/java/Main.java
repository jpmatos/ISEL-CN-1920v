import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.compute.Compute;
import com.google.api.services.compute.ComputeScopes;
import com.google.api.services.compute.model.NetworkInterface;
import com.google.api.services.compute.model.Operation;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static String PROJECT_ID;

    public static void main(String[] args) {

        try {
            GoogleCredentials credential = GoogleCredentials.getApplicationDefault();
            List<String> scopes = new ArrayList<String>();
            scopes.add(ComputeScopes.COMPUTE);
            credential = credential.createScoped(scopes);
            HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            HttpRequestInitializer requestInit = new HttpCredentialsAdapter(credential);
            Compute computeService = new Compute
                    .Builder(transport, jsonFactory, requestInit)
                    .setApplicationName(PROJECT_ID)
                    .build();

            PROJECT_ID = ScanUtils.getInputString("Project ID: ", "g01-li61n");

            Operations operations = new Operations(computeService);
            while (true) {
                int oper = ScanUtils.getInputInt(
                        "------------------\n" +
                                "Pick an operation:\n" +
                                "[1] - List VMs\n" +
                                "[2] - Create new VM\n" +
                                "[3] - Delete VM\n" +
                                "[4] - List Group Managers\n" +
                                "[5] - Resize Instance Group\n" +
                                "[0] - Quit");
                switch (oper) {
                    case 1:
                        listVMs(operations);
                        break;
                    case 2:
                        createNewInstance(operations);
                        break;
                    case 3:
                        deleteVM(operations);
                        break;
                    case 4:
                        listGroupManagers(operations);
                        break;
                    case 5:
                        resizeInstanceGroup(operations);
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

    private static void resizeInstanceGroup(Operations operations) throws IOException, InterruptedException {
        String instanceGroup = ScanUtils.getInputString("Instance group:", null);
        String zoneName = ScanUtils.getInputString("Zone name:", "us-central1-a");
        int newSize = ScanUtils.getInputInt("New size:");
        Operation.Error err = operations.resizeInstanceGroup(PROJECT_ID, zoneName, instanceGroup, newSize);
        if(err != null)
            System.out.println(err);
    }

    private static void listGroupManagers(Operations operations) throws IOException {
        String zoneName = ScanUtils.getInputString("Zone name:", "us-central1-a");
        operations.listGroupManagers(PROJECT_ID, zoneName);
    }

    private static void deleteVM(Operations operations) throws IOException, InterruptedException {
        String instanceName = ScanUtils.getInputString("Instance name:", null);
        String zoneName = ScanUtils.getInputString("Zone name:", "europe-west2-c");
        Operation.Error err = operations.deleteVM(PROJECT_ID, zoneName, instanceName);
        if(err != null)
            System.out.println(err);
    }

    private static void createNewInstance(Operations operations) throws IOException, InterruptedException {
        String instanceName = ScanUtils.getInputString("Instance name:", null);
        String zoneName = ScanUtils.getInputString("Zone name", "europe-west2-c");
        String machineType = ScanUtils.getInputString("Machine type", "f1-micro");
        Operation.Error err = operations.createNewInstance(instanceName, PROJECT_ID, zoneName, machineType);
        if(err != null)
            System.out.println(err);
    }

    private static void listVMs(Operations operations) throws IOException {
        String zoneName = ScanUtils.getInputString("Zone name:", "europe-west2-c");
        operations.listVms(PROJECT_ID, zoneName);
    }
}
