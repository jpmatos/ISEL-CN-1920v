import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.Topic;
import com.google.pubsub.v1.TopicName;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Operations {
    private final String PROJECT_ID;
    private final String CAIXA_ID;
    private final CollectionReference collection;

    public Operations(String projectID, String caixaID, CollectionReference collection) {
        this.PROJECT_ID = projectID;
        this.CAIXA_ID = caixaID;
        this.collection = collection;
    }

    public void publishMessage(String topicName, String msgTxt, String item, double quant, double precoUnit, double total) throws IOException, ExecutionException, InterruptedException {
        TopicName tName=TopicName.ofProjectTopicName(PROJECT_ID, topicName);
        Publisher publisher = Publisher.newBuilder(tName).build();

        // Por cada mensagem
        ByteString msgData = ByteString.copyFromUtf8(msgTxt);
        PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
                .setData(msgData)
                .putAttributes("caixa", CAIXA_ID)
                .putAttributes("item", item)
                .putAttributes("quant", Double.toString(quant))
                .putAttributes("preçoUnit", Double.toString(precoUnit))
                .putAttributes("total", Double.toString(total))
                .build();
        ApiFuture<String> future = publisher.publish(pubsubMessage);
        String msgID = future.get();
        System.out.println("Message Published with ID=" + msgID);

        // No fim de enviar as mensagens
        publisher.shutdown();
    }

    public void listTopics() throws IOException {
        TopicAdminClient topicAdmin = TopicAdminClient.create();
        TopicAdminClient.ListTopicsPagedResponse res =
                topicAdmin.listTopics(ProjectName.of(PROJECT_ID));
        for (Topic top : res.iterateAll()) {
            System.out.println("TopicName=" + top.getName());
        }
        topicAdmin.close();
    }

    public void createTopic(String topicName) throws IOException {
        TopicAdminClient topicAdmin = TopicAdminClient.create();
        TopicName tName = TopicName.ofProjectTopicName(PROJECT_ID, topicName);
        Topic topic = topicAdmin.createTopic(tName);
    }

    public void queryCaixaSalesVolume(String caixa) throws ExecutionException, InterruptedException {
        Query query = collection.whereEqualTo("caixa", caixa);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        double salesTotal = 0;
        for (DocumentSnapshot doc : querySnapshot.get().getDocuments()) {
            Sale sale = doc.toObject(Sale.class);
            salesTotal += sale.total;
        }
        System.out.println(String.format("Sales Volume for caixa '%s': %.2f€", caixa, salesTotal));
    }

    public void querySalesAboveAmount(double amount) throws ExecutionException, InterruptedException {
        Query query = collection.whereGreaterThan("total", amount);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        for (DocumentSnapshot doc : querySnapshot.get().getDocuments()) {
            Sale sale = doc.toObject(Sale.class);
            System.out.println(sale);
        }
    }

    public void querySalesWithItem(String item) throws ExecutionException, InterruptedException {
        Query query = collection.whereEqualTo("item", item);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        for (DocumentSnapshot doc : querySnapshot.get().getDocuments()) {
            Sale sale = doc.toObject(Sale.class);
            System.out.println(sale);
        }
    }
}
