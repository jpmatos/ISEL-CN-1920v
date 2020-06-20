package gcloud.pubsub;

import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import vmTranslate.MessageReceivedHandler;

import static utils.Output.log;
import static vmTranslate.Worker.PROJECT_ID;

public class ReadSubscription {
    private static final String FREE_TRANSLATE = "free-translate";
    private static final String PREMIUM_TRANSLATE = "premium-translate";
    private final String subscription;
    private final Subscriber subscriber;

    public ReadSubscription(boolean premium) {
        this.subscription = premium ? PREMIUM_TRANSLATE : FREE_TRANSLATE;

        ProjectSubscriptionName projectSubscriptionName = ProjectSubscriptionName.of(PROJECT_ID, subscription);
        this.subscriber = Subscriber.newBuilder(projectSubscriptionName, new MessageReceivedHandler(premium))
                .build();
    }

    public void startRead() {
        log("Start reading " + subscription);
        subscriber.startAsync().awaitRunning();
    }

    public void stopRead() {
        log("Stop reading " + subscription);
        subscriber.stopAsync();
    }
}
