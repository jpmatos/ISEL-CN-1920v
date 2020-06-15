package gcloud;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class PublishOps implements AutoCloseable {
    private final Publisher publisher;
    private static final String PROJECT_ID = "g01-li61n";


    public PublishOps(String topic) throws IOException {
        TopicName tName = TopicName.ofProjectTopicName(PROJECT_ID, topic);
        this.publisher = Publisher.newBuilder(tName).build();
    }

    public String publishMessage(String msg) throws ExecutionException, InterruptedException {
        ByteString msgData = ByteString.copyFromUtf8(msg);
        PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
                .setData(msgData)
                .build();

        ApiFuture<String> future = publisher.publish(pubsubMessage);
        return future.get();
    }

    @Override
    public void close() {
        publisher.shutdown();
    }
}
