package gcloud.pubsub;

import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import io.grpc.LoadBalancerRegistry;
import io.grpc.internal.PickFirstLoadBalancerProvider;
import vmocr.MessageReceiverHandler;

import static utils.Console.print;
import static vmocr.Worker.PROJECT_ID;

public class ReadSubscription {
    private static final String FREE_OCR = "free-ocr";
    private static final String PREMIUM_OCR = "premium-ocr";
    private final boolean premium;
    private final String subscription;
    private final Subscriber subscriber;

    public ReadSubscription(boolean premium) {
        this.premium = premium;
        this.subscription = premium ? PREMIUM_OCR : FREE_OCR;

        ProjectSubscriptionName projsubscriptionName = ProjectSubscriptionName.of(PROJECT_ID, subscription);
        this.subscriber = Subscriber.newBuilder(projsubscriptionName, new MessageReceiverHandler(premium))
                .build();
    }

    public void startRead() {
        print("Start reading " + subscription);
        subscriber.startAsync().awaitRunning();
    }

    public void stopRead() {
        print("Stop reading " + subscription);
        subscriber.stopAsync();
    }

}
