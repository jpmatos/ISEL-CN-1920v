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
import serviceapp.Operations;
import utils.Output;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import static serviceapp.Operations.*;

public class VMManagement {
    private static final String FREE_OCR_VM = "ocr-free-final-project";
    private static final String FREE_OCR_IG = "ocr-premium-final-project\n";

    private static final String PREMIUM_TRANSLATE_VM = ""; //TODO
    private static final String PREMIUM_TRANSLATE_IG = ""; //TODO

    private static final String ZONE = "europe-north1-a";


    public static void updateVMInstances(int freeUsers, int premiumUsers) {
        if (freeUsers > 0) {
            //TODO Start VM if stoped
        } else {
            //TODO stop free VM
        }

        //TODO update Premium VM's
    }

    public static void main(String... args) throws IOException, GeneralSecurityException {
        Output.console("teste");
//        startVM();
        stopVM();
    }

    private static void startVM() throws IOException, GeneralSecurityException {
        Compute computeService = getComputeService();
        Operation operation = computeService.instances()
                .start(PROJECT_ID, ZONE, FREE_OCR_VM)
                .execute();
    }

    private static void stopVM() throws IOException, GeneralSecurityException {
        Compute computeService = getComputeService();
        Operation operation = computeService.instances()
                .stop(PROJECT_ID, ZONE, FREE_OCR_VM)
                .execute();
    }

    private static Compute getComputeService() throws IOException, GeneralSecurityException {
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
    }


}
