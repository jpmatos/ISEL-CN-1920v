package serviceapp.compute;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.compute.Compute;
import com.google.api.services.compute.ComputeScopes;
import com.google.api.services.compute.model.Operation;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import static serviceapp.Operations.PROJECT_ID;
import static utils.Output.OutputType.ERROR;
import static utils.Output.console;
import static utils.Output.log;

public class VMManagement {
    private static final String FREE_OCR_VM = "ocr-free-final-project";
    private static final String PREMIUM_OCR_IG = "ocr-premium-final-project";

    private static final String FREE_TRANSLATE_VM = "todo"; //TODO
    private static final String PREMIUM_TRANSLATE_IG = "todo"; //TODO

    private static final String ZONE = "europe-north1-a";

    private final Object lock = new Object();
    private final Compute compute;
    private final Compute.Instances instances;

    public VMManagement() {
        this.compute = getComputeService();
        this.instances = compute.instances();
    }

    public void updateVMInstancesAsync(int freeUsers, int premiumUsers) {
        new Thread(() -> updateVMInstances(freeUsers, premiumUsers))
                .start();
    }

    public void updateVMInstances(int freeUsers, int premiumUsers) {
        console("Update Instances: Free = " + freeUsers + " Premium = " + premiumUsers);
        if (freeUsers > 0) {
            startVM(FREE_OCR_VM);
//            startVM(FREE_TRANSLATE_VM);
        } else {
            stopVM(FREE_OCR_VM);
//            stopVM(FREE_TRANSLATE_VM);
        }

        resizeInstanceGroup(PREMIUM_OCR_IG, premiumUsers);
//        resizeInstanceGroup(PREMIUM_TRANSLATE_IG, premiumUsers);
    }

    public static void main(String... args) throws IOException {
        console("teste");
        VMManagement management = new VMManagement();

        management.updateVMInstancesAsync(3, 2);

        while (true) {
            try {
                console("Do other stuff");
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * https://cloud.google.com/compute/docs/reference/rest/v1/instances/stop
     */
    private void stopVM(String instance) {
        try {
            console("Stoping VM " + instance + " ...");
            Operation operation = instances
                    .stop(PROJECT_ID, ZONE, instance)
                    .execute();
            waitOperation(operation);
            console("VM " + instance + ": " + getStatus(instance));
        } catch (IOException ex) {
            log(ERROR, ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void startVM(String instance) {
        try {
            console("Starting VM " + instance + " ...");
            Operation operation = instances
                    .start(PROJECT_ID, ZONE, instance)
                    .execute();
            waitOperation(operation);
            console("VM " + instance + ": " + getStatus(instance));
        } catch (IOException ex) {
            log(ERROR, ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void resizeInstanceGroup(String ig, int newSize) {
        try {
            console("Resizing Instance Group " + ig + " ...");
            Operation operation = compute.instanceGroupManagers()
                    .resize(PROJECT_ID, ZONE, ig, newSize)
                    .execute();
            waitOperation(operation);
            console("Instance Group " + ig + " resized to: " + newSize);
        } catch (IOException ex) {
            log(ERROR, ex.getMessage());
            ex.printStackTrace();
        }
    }

    private String getStatus(String instance) throws IOException {
        return instances.get(PROJECT_ID, ZONE, instance)
                .execute()
                .getStatus();
    }

    private void waitOperation(Operation op) {
        try {
            while (!op.getStatus().equals("DONE")) {
                Thread.sleep(1_000);
                Compute.ZoneOperations.Get get = compute.zoneOperations().get(PROJECT_ID, ZONE, op.getName());
                op = get.execute();
            }
        } catch (InterruptedException | IOException ex) {
            log(ERROR, ex.getMessage());
            ex.printStackTrace();
        }
    }

    private Compute getComputeService() {
        try {
            GoogleCredentials credential = GoogleCredentials.getApplicationDefault();
            List<String> scopes = new ArrayList<>();
            scopes.add(ComputeScopes.COMPUTE);
            credential = credential.createScoped(scopes);
            HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            HttpRequestInitializer requestInit = new HttpCredentialsAdapter(credential);

            return new Compute
                    .Builder(transport, jsonFactory, requestInit)
                    .setApplicationName("gRPC Service G01")
                    .build();

        } catch (IOException | GeneralSecurityException ex) {
            ex.printStackTrace();
            log(ERROR, ex.getMessage());
            return null;
        }
    }


}
