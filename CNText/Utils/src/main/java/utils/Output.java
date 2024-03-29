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

    /**
     * Output to Console and Logger
     * @param msg
     */
    public static void log(String msg) {
        log(OutputType.INFO, msg);
    }


    /**
     * Output to Console and Logger
     * @param type
     * @param msg
     */
    public static void log(OutputType type, String msg) {
        try {
            String message = processMsg(type, msg);

            //Output to Console
            System.out.println(message);

            //Output to Topic Logger
            publishOps.publishMessage(message);

        } catch (UnknownHostException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Output only to Console
     * @param msg
     */
    public static void console(String msg){
        console(OutputType.INFO, msg);
    }

    /**
     * Output only to Console
     * @param type
     * @param msg
     */
    public static void console(OutputType type, String msg){
        try {
            String message = processMsg(type, msg);

            //Output to Console
            System.out.println(message);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static String processMsg(OutputType type, String msg) throws UnknownHostException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        long threadId = Thread.currentThread().getId();

        //Output to console
        String hostname = InetAddress.getLocalHost().getHostName();
        return "[" + hostname + "]" + "[" + dtf.format(now) + "]" + "[Thread " + threadId + "]" + "[" + type + "] " + msg;
    }


}
