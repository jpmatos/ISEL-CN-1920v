package vmTranslate;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.pubsub.v1.PubsubMessage;
import dao.TranslateRequest;
import gcloud.firestore.FirestoreOps;
import gcloud.firestore.IFirestoreOps;
import gcloud.pubsub.PublishTopic;
import gcloud.translate.ITranslateOps;
import gcloud.translate.TranslateOps;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static utils.Output.OutputType.ERROR;
import static utils.Output.OutputType.WARNING;
import static utils.Output.console;
import static utils.Output.log;

public class MessageReceivedHandler implements MessageReceiver {

    private final ITranslateOps translateOps = new TranslateOps();
    private IFirestoreOps firestoreOps;
    private PublishTopic publishToTranslate;

    public MessageReceivedHandler(boolean premium) {
        try {
            this.publishToTranslate = new PublishTopic(premium);
            this.firestoreOps = new FirestoreOps();
        } catch (IOException e) {
            e.printStackTrace();
            log(ERROR, "The Type of user isn't define");
        }
    }

    @Override
    public void receiveMessage(PubsubMessage msg, AckReplyConsumer ackReplyConsumer) {
        Map<String, String> attributesMap = msg.getAttributesMap();
        TranslateRequest translateRequest = new TranslateRequest(

                attributesMap.get("id"),
                attributesMap.get("data"),
                attributesMap.get("language"),
                attributesMap.get("locale")

        );

        //Firestore ID
        String id = translateRequest.getId();


        log("[Request received] " + id + " | " + translateRequest.toString());

        ackReplyConsumer.ack();
        try {

            log("Get text translation from translation API ");
            String translatedText = translateOps.getTextTranslated(msg.getData().toStringUtf8(), translateRequest.getLocale(), translateRequest.getLanguage());
            String errorTranslateMessage = translateOps.getError();

            log("Save translated text to Firestore");
            if (!firestoreOps.storeTranslatedTextResult(id, translatedText, translateRequest.getLanguage(), translateRequest.getLocale(), msg.getData().toStringUtf8(), errorTranslateMessage)) {
                log(ERROR, "Cannot save translated text result to Firestore");
            }
                log("Done");
            } catch(ExecutionException | InterruptedException e){
                e.printStackTrace();
            }

        }
    }
