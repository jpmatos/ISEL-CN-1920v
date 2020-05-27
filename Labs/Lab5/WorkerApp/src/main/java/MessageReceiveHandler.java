import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MessageReceiveHandler implements MessageReceiver {
    private final CollectionReference colRef;

    public MessageReceiveHandler(CollectionReference colRef) {
        this.colRef = colRef;
    }

    public void receiveMessage(PubsubMessage msg, AckReplyConsumer ackReply) {
        try {
            insertDocument(msg.getAttributesMap(), msg.getData(), msg.getMessageId());
            ackReply.ack();
        } catch (Exception e) {
            e.printStackTrace();
            ackReply.nack();
        }
    }

    private void insertDocument(Map<String, String> attributesMap, ByteString data, String messageId) throws ExecutionException, InterruptedException {
        Sale sale = new Sale(
            messageId,
            data.toStringUtf8(),
            attributesMap.get("caixa"),
            attributesMap.get("item"),
            Double.valueOf(attributesMap.get("quant")),
            Double.valueOf(attributesMap.get("preçoUnit")),
            Double.valueOf(attributesMap.get("total")));

        DocumentReference docRef = colRef.document(sale.id + "");
        ApiFuture<WriteResult> result = docRef.set(sale);
        WriteResult res = result.get();
        System.out.println(res);
    }
}
