package vmocr;

import gcloud.pubsub.ReadSubscription;
import utils.Output;

import java.util.NoSuchElementException;
import java.util.Scanner;

import static utils.Output.log;

public class Worker {
    private static Scanner sc = new Scanner(System.in);
    public static final String PROJECT_ID = "g01-li61n";

    private static boolean premium;

    public static void main(String[] args) {
        if (args.length != 1) {
            illegalArguments();
        }

        if ("-free".equals(args[0])) {
            log("Free selected");
            premium = false;
        } else if ("-premium".equals(args[0])) {
            log("Premium selected");
            premium = true;
        } else {
            illegalArguments();
        }

        ReadSubscription readSubscription = new ReadSubscription(premium);
        readSubscription.startRead();

        log("Listening...");

        while (true) {
            try {
                sc.nextLine();
                log("Leaving\n");
                break;
            } catch (NoSuchElementException ex) {
                try {
                    Thread.sleep(60_000);
                    log("PING - Main thread is alive.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

    }

    private static void illegalArguments() {
        log("Arguments: -free|-premium");
        System.exit(0);
    }
}
