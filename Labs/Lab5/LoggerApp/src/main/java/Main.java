import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private final static String PROJECT_ID = "g01-li61n";

    public static void main(String[] args) throws IOException {
        String subsName = ScanUtils.getInputString("Sub Name:");

        ProjectSubscriptionName projsubscriptionName =
                ProjectSubscriptionName.of(PROJECT_ID, subsName);

        Subscriber subscriber =
                        Subscriber.newBuilder(projsubscriptionName, new MessageReceiveHandler())
                        .build();

        System.out.println("Listening... Press Enter to exit.");
        subscriber.startAsync().awaitRunning();
        sc.nextLine();
    }
}
