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

import static utils.Console.PrintType.ERROR;
import static utils.Console.print;

public class MessageReceiverHandler implements MessageReceiver {
    private final IVisionOps visionOps = new IVisionOpsDummy();
    private final IStorageOps storageOps = new StorageOps();
    private final IFirestoreOps firestoreOps = new FirestoreOps();
    private PublishTopic publish;


    public MessageReceiverHandler(boolean premium) {
        try (PublishTopic publishTopic = new PublishTopic(premium)){
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
                attributesMap.get("token"),
                attributesMap.get("blobName"),
                attributesMap.get("language")
        );

        print("[Request received] " + ocrRequest.toString());

        ackReplyConsumer.ack();
        try {
            print("Read blob from CloudStorare");
            ByteString blobBytes = storageOps.getBlobToByteString(ocrRequest.getBlobName());

            print("Get OCR from Vision");
            String ocrResult = visionOps.getTextFromImage(blobBytes);

            print("Save OCR result to Firestore");
            if (!firestoreOps.storeOCRResult(ocrRequest.getToken(), ocrResult)) {
                print(ERROR, "Cannot save OCR result to Firestore");
            }

            print("Delete blob image from Cloud Store");
            if (!storageOps.deleteBlob(ocrRequest.getBlobName())) {
                print(ERROR + "Cannot delete blob from Cloud Store: " + ocrRequest.getBlobName());
            }

            print("Send translate request");
            publish.publishMessage(new TranslateRequest(ocrRequest.getToken(), ocrResult, ocrRequest.getLanguage()));

            print("Done");
        } catch (IOException e) {
//            ackReplyConsumer.nack(); //TODO
            e.printStackTrace();
        }

    }


}
