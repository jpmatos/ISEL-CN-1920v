package gcloud.pubsub;

import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import dao.OCRRequest;
import dao.TranslateRequest;

import java.io.IOException;

import static utils.Console.print;

public class PublishTopic implements AutoCloseable {
    private static final String FREE_TRANSLATE = "free-translate";
    private static final String PREMIUM_TRANSLATE = "premium-translate";

    private final Publisher publisher;
    private static final String PROJECT_ID = "g01-li61n";

    public PublishTopic(boolean premium) throws IOException {
        String topic = premium ? PREMIUM_TRANSLATE : FREE_TRANSLATE;
        TopicName tName = TopicName.ofProjectTopicName(PROJECT_ID, topic);
        this.publisher = Publisher.newBuilder(tName).build();
    }

    public PublishTopic(String topic) throws IOException {
        TopicName tName = TopicName.ofProjectTopicName(PROJECT_ID, topic);
        this.publisher = Publisher.newBuilder(tName).build();
    }

    public void publishMessage(TranslateRequest translateRequest) {
        ByteString msgData = ByteString.copyFromUtf8(translateRequest.getTextToTranslate());
        PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
                .setData(msgData)
                .putAttributes("token", translateRequest.getToken())
                .putAttributes("language", translateRequest.getLanguage())
                .build();

        //TODO fire and forget?
        publisher.publish(pubsubMessage);
    }

    public void publishMessage(OCRRequest ocrRequest) {
        print("Publishing Demo message...");
        PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
                .putAttributes("sessionID", ocrRequest.getSessionID())
                .putAttributes("token", ocrRequest.getToken())
                .putAttributes("blobName", ocrRequest.getBlobName())
                .putAttributes("language", ocrRequest.getLanguage())
                .build();

        publisher.publish(pubsubMessage);
    }

    @Override
    public void close(){
        //TODO
        print("Shutdown publisher");
        publisher.shutdown();
    }
}
