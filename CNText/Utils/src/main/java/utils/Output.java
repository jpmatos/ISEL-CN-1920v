package utils;

import gcloud.PublishOps;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;

public class Output {
    private static PublishOps publishOps;

    static {
        try {
            publishOps = new PublishOps("logger");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public enum OutputType {
        ERROR,
        INFO,
        WARNING
    }

    public static void log(String msg) {
        log(OutputType.INFO, msg);
    }

    public static void log(OutputType type, String msg) {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            long threadId = Thread.currentThread().getId();

            //Output to console
            String hostname = InetAddress.getLocalHost().getHostName();
            String message = "[" + hostname + "]" + "[" + dtf.format(now) + "]" + "[Thread " + threadId + "]" + "[" + type + "] " + msg;
            System.out.println(message);

            //Output to Topic Logger
            publishOps.publishMessage(message);

        } catch (UnknownHostException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
