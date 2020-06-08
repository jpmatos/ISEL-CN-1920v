package vmocr;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.pubsub.v1.PubsubMessage;

import static utils.Console.print;

public class MessageReceiverHandler implements MessageReceiver {
    public void receiveMessage(PubsubMessage msg, AckReplyConsumer ackReplyConsumer) {
        //TODO
        print(msg.getData().toStringUtf8());
        ackReplyConsumer.nack();
    }
}
