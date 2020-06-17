package gcloud.pubsub;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import dao.OCRRequest;
import dao.TranslateRequest;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static utils.Output.log;


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

    /**
     * @param translateRequest
     * @return msgID
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String publishMessage(TranslateRequest translateRequest) throws ExecutionException, InterruptedException {
        ByteString msgData = ByteString.copyFromUtf8(translateRequest.getOcrResult().getResult());
        PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
                .setData(msgData)
                .putAttributes("id", translateRequest.getId())
                .putAttributes("locale", translateRequest.getOcrResult().getLocale())
                .putAttributes("language", translateRequest.getLanguage())
                .build();

        ApiFuture<String> future = publisher.publish(pubsubMessage);
        return future.get();
    }

    /**
     * @param ocrRequest
     * @return msgID
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String publishMessage(OCRRequest ocrRequest) throws ExecutionException, InterruptedException {
        PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
                .putAttributes("sessionID", ocrRequest.getSessionID())
                .putAttributes("blobName", ocrRequest.getBlobName())
                .putAttributes("language", ocrRequest.getLanguage())
                .build();

        ApiFuture<String> future = publisher.publish(pubsubMessage);
        return future.get();
    }

    @Override
    public void close() {
        log("Shutdown publisher");
        publisher.shutdown();
    }
}
