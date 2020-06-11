package vmocr;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import dao.OCRRequest;
import dao.TranslateRequest;
import gcloud.firestore.FirestoreOps;
import gcloud.firestore.IFirestoreOps;
import gcloud.pubsub.PublishTopic;
import gcloud.storage.IStorageOps;
import gcloud.storage.StorageOps;
import gcloud.vision.IVisionOps;
import gcloud.vision.IVisionOpsDummy;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static utils.Console.PrintType.ERROR;
import static utils.Console.print;

public class MessageReceiverHandler implements MessageReceiver {
    private final IVisionOps visionOps = new IVisionOpsDummy();
    private final IStorageOps storageOps = new StorageOps();
    private final IFirestoreOps firestoreOps = new FirestoreOps();
    private PublishTopic publish;


    public MessageReceiverHandler(boolean premium) {
        try (PublishTopic publishTopic = new PublishTopic(premium)) {
            this.publish = publishTopic;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveMessage(PubsubMessage msg, AckReplyConsumer ackReplyConsumer) {
        Map<String, String> attributesMap = msg.getAttributesMap();
        OCRRequest ocrRequest = new OCRRequest(
                attributesMap.get("sessionID"),
                attributesMap.get("blobName"),
                attributesMap.get("language")
        );

        String id = msg.getMessageId();

        print("[Request received] " + id + " | " + ocrRequest.toString());

        ackReplyConsumer.ack();
        try {
            print("Read blob from CloudStorare");
            ByteString blobBytes = storageOps.getBlobToByteString(ocrRequest.getBlobName());

            print("Get OCR from Vision");
            String ocrResult = visionOps.getTextFromImage(blobBytes);

            print("Save OCR result to Firestore");
            if (!firestoreOps.storeOCRResult(id, ocrResult)) {
                print(ERROR, "Cannot save OCR result to Firestore");
            }

            print("Delete blob image from Cloud Store");
            if (!storageOps.deleteBlob(ocrRequest.getBlobName())) {
                print(ERROR + "Cannot delete blob from Cloud Store: " + ocrRequest.getBlobName());
            }

            print("Send translate request");
            String translateReqId = publish.publishMessage(new TranslateRequest(id, ocrResult, ocrRequest.getLanguage()));
            print("Translate Request: " + translateReqId);

            print("Done");
        } catch (IOException | ExecutionException | InterruptedException e) {
//            ackReplyConsumer.nack(); //TODO
            e.printStackTrace();
        }

    }


}
