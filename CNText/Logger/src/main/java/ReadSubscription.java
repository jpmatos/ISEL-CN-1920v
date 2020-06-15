import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;

public class ReadSubscription {
    private final Subscriber subscriber;

    public ReadSubscription(String subscription, MessageReceiver handler) {
        ProjectSubscriptionName projsubscriptionName = ProjectSubscriptionName.of(Logger.PROJECT_ID, subscription);
        this.subscriber = Subscriber.newBuilder(projsubscriptionName, handler)
                .build();
    }

    public void startRead() {
        subscriber.startAsync().awaitRunning();
    }

    public void stopRead() {
        subscriber.stopAsync();
    }

}
