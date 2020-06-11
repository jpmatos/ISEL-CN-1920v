package vmocr;

import dao.OCRRequest;
import gcloud.pubsub.PublishTopic;
import gcloud.pubsub.ReadSubscription;

import java.io.IOException;
import java.util.Scanner;

import static utils.Console.print;

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

        ReadSubscription readSubscription = new ReadSubscription(premium);
        readSubscription.startRead();

        new Thread(Worker::simulateRequest).start();

        print("Listening... Press Enter to exit.");
        sc.nextLine();

    }

    private static void simulateRequest() {
        try (PublishTopic publishTopic = new PublishTopic("free-ocr")) {
            publishTopic.publishMessage(new OCRRequest(
                    "1234",
                    "token-sdfpojepg",
                    "image-for-ocr-translate.JPG",
                    "EN"
            ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void illegalArguments() {
        print("Arguments: -free|-premium");
        System.exit(0);
    }
}
