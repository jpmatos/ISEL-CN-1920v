package vmocr;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import static utils.Console.print;

public class Worker {
    private static Scanner sc = new Scanner(System.in); //TODO delete?
    private static final String FREE = "free-ocr";
    private static final String PREMIUM = "premium-ocr";
    private static final String PROJECT_ID = "g01-li61n";

    private static String topic;

    public static void main(String[] args) {
        if (args.length != 1) {
            illegalArguments();
        }

        if ("-free".equals(args[0])) {
            print("Free selected");
            topic = FREE;
        } else if ("-premium".equals(args[0])) {
            print("Premium selected");
            topic = PREMIUM;
        } else {
            illegalArguments();
        }

        new Thread(()-> createSubscriber(topic)).start();

        System.out.println("Listening... Press Enter to exit.");
        sc.nextLine();

    }

    private static void createSubscriber(String subsName) {
        ProjectSubscriptionName projsubscriptionName = ProjectSubscriptionName.of(PROJECT_ID, subsName);
        Subscriber subscriber = Subscriber.newBuilder(projsubscriptionName, new MessageReceiverHandler())
                .build();

        print("start async " + subsName);
        subscriber.startAsync().awaitRunning();
    }

    private static void illegalArguments() {
        print("Arguments: -free|-premium");
        System.exit(0);
    }
}
