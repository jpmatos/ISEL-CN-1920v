package vmocr;

import gcloud.pubsub.ReadSubscription;

import java.util.Scanner;

import static utils.Console.print;
import static utils.Gcloud.registerBalancerProvider;

public class Worker {
    private static Scanner sc = new Scanner(System.in); //TODO delete?
    public static final String PROJECT_ID = "g01-li61n";

    private static boolean premium;

    public static void main(String[] args) {
        if (args.length != 1) {
            illegalArguments();
        }

        if ("-free".equals(args[0])) {
            print("Free selected");
            premium = false;
        } else if ("-premium".equals(args[0])) {
            print("Premium selected");
            premium = true;
        } else {
            illegalArguments();
        }

        registerBalancerProvider();
        ReadSubscription readSubscription = new ReadSubscription(premium);
        readSubscription.startRead();

//        new Thread(Worker::simulateRequest).start();

        print("Listening... Press Enter to exit.");
        sc.nextLine();

    }
    private static void illegalArguments() {
        print("Arguments: -free|-premium");
        System.exit(0);
    }
}
