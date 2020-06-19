package serviceapp.util;

import gcloud.PublishOps;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Logger {

    private static PublishOps publishOps = null;

    public static void init() {
        try {
            publishOps = new PublishOps("logger");
        } catch (IOException ignored) {
        }
    }

    public static void log(String message){
        try {
            System.out.println(message);
            publishOps.publishMessage(message);
        } catch (ExecutionException | InterruptedException ignored) {
        }
    }
}
