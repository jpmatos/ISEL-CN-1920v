package vmocr;

import gcloud.pubsub.ReadSubscription;
import utils.Output;

import java.util.NoSuchElementException;
import java.util.Scanner;

import static utils.Gcloud.registerBalancerProvider;

public class Worker {
    private static Scanner sc = new Scanner(System.in);
    public static final String PROJECT_ID = "g01-li61n";

    private static boolean premium;

    public static void main(String[] args) {
        if (args.length != 1) {
            illegalArguments();
        }

        if ("-free".equals(args[0])) {
            Output.log("Free selected");
            premium = false;
        } else if ("-premium".equals(args[0])) {
            Output.log("Premium selected");
            premium = true;
        } else {
            illegalArguments();
        }

        registerBalancerProvider();
        ReadSubscription readSubscription = new ReadSubscription(premium);
        readSubscription.startRead();

        Output.log("Listening...");

        while (true) {
            try {
                sc.nextLine();
                Output.log("Leaving\n");
                break;
            } catch (NoSuchElementException ex) {
                try {
                    Thread.sleep(60_000);
                    Output.log("PING - Main thread is alive.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

    }

    private static void illegalArguments() {
        Output.log("Arguments: -free|-premium");
        System.exit(0);
    }
}
