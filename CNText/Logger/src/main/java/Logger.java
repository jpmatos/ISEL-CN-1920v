import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.pubsub.v1.PubsubMessage;

public class Logger {
    public static final String PROJECT_ID = "g01-li61n";

    public static void main(String[] args) {
        ReadSubscription readSubscription = new ReadSubscription("logger", new MessageReceiver() {

            public void receiveMessage(PubsubMessage pubsubMessage, AckReplyConsumer ackReplyConsumer) {
                System.out.println(pubsubMessage.getData().toStringUtf8());
                ackReplyConsumer.ack();
            }
        });

        System.out.println("Reader started");
        readSubscription.startRead();

        while (true) {
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
