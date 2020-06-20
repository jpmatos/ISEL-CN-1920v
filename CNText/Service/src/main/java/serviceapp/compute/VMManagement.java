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
import static utils.Output.OutputType.WARNING;
import static utils.Output.console;
import static utils.Output.log;

public class VMManagement {
    private static final String FREE_OCR_VM = "ocr-free-final-project";
    private static final String FREE_OCR_IG = "ocr-premium-final-project\n";

    private static final String PREMIUM_TRANSLATE_VM = ""; //TODO
    private static final String PREMIUM_TRANSLATE_IG = ""; //TODO

    private static final String ZONE = "europe-north1-a";

    private final Object lock = new Object();
    private final Compute compute;
    private final Compute.Instances instances;

    public VMManagement() {
        this.compute = getComputeService();
        this.instances = compute.instances();
    }

    public static boolean updateVMInstances(int freeUsers, int premiumUsers) {
        if (freeUsers > 0) {
            //TODO Start VM if stoped
        } else {
            //TODO stop free VM
        }

        //TODO update Premium VM's
        return false;
    }

    public static void main(String... args) throws IOException {
        console("teste");

        VMManagement management = new VMManagement();

        new Thread(() -> {
            if (!management.startStop(true)) {
                console(WARNING, "Operations pending. Cannot Proceed");
            }
        }).start();

        new Thread(() -> {
            if (!management.startStop(false)) {
                console(WARNING, "Operations pending. Cannot Proceed");
            }
        }).start();

        new Thread(() -> {
            if (!management.startStop(true)) {
                console(WARNING, "Operations pending. Cannot Proceed");
            }
        }).start();


        while (true) {
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean startStop(boolean start) {
        log("Start VM: " + start);
        synchronized (lock) {
            try {
                long ops = compute.
                        zoneOperations()
                        .list(PROJECT_ID, ZONE)
                        .execute()
                        .getItems()
                        .stream()
                        .filter(op -> !op.getStatus().equals("DONE"))
                        .count();

                if (ops > 0) {
//                    console(ops.size() + "");
                    return false;
                }

                if (start) {
                    startVM(FREE_OCR_VM);
                } else {
                    stopVM(FREE_OCR_VM);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                return false;
            }
            return true;
        }
    }


    /**
     * https://cloud.google.com/compute/docs/reference/rest/v1/instances/start
     */
    private void stopVM(String instance) throws IOException {
        console("Stoping VM...");
        Operation operation = instances
                .stop(PROJECT_ID, ZONE, instance)
                .execute();
//            triggerConclusion(operation, "STOP_VM");
//        operation.setName("stopvm");
        waitOperation(operation);
        console("VM status: " + getStatus(instance));
    }

    private void startVM(String instance) throws IOException {
        console("Starting VM...");
        Operation operation = instances.start(PROJECT_ID, ZONE, instance)
                .execute();
//            triggerConclusion(operation, "START_VM");
//        operation.setName("startvm");
        waitOperation(operation);
        console("VM status: " + getStatus(instance));
    }

    private String getStatus(String instance) throws IOException {
        return instances.get(PROJECT_ID, ZONE, instance)
                .execute()
                .getStatus();
    }

    private void triggerConclusion(final Operation op, String opType) {
        new Thread(() -> {
            try {
                Operation operation = op;
                while (!operation.getStatus().equals("DONE")) {
                    console(opType + " " + operation.getStatus());
                    Thread.sleep(1_000);
                    operation = compute.zoneOperations()
                            .get(PROJECT_ID, ZONE, operation.getName())
                            .execute();
                }
                console("<<< " + opType + " " + operation.getStatus());
            } catch (InterruptedException | IOException ex) {
                ex.printStackTrace();
            }

        }).start();
    }

    private void waitOperation(Operation op) {
        try {
            while (!op.getStatus().equals("DONE")) {
                Thread.sleep(1_000);
                Compute.ZoneOperations.Get get = compute.zoneOperations().get(PROJECT_ID, ZONE, op.getName());
                op = get.execute();
                console(op.getStatus());
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
