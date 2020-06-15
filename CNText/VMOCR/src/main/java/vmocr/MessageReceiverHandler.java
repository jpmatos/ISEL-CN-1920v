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

import static utils.Output.OutputType.ERROR;
import static utils.Output.log;


public class MessageReceiverHandler implements MessageReceiver {
    private final IVisionOps visionOps = new IVisionOpsDummy();
    private final IStorageOps storageOps = new StorageOps();
    private IFirestoreOps firestoreOps;
    private PublishTopic publishToTranslate;

    public MessageReceiverHandler(boolean premium) {
        try {
            this.publishToTranslate = new PublishTopic(premium);
            this.firestoreOps = new FirestoreOps();
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

        log("[Request received] " + id + " | " + ocrRequest.toString());

        ackReplyConsumer.ack();
        try {
            log("Read blob from CloudStorare");
            ByteString blobBytes = storageOps.getBlobToByteString(ocrRequest.getBlobName());

            log("Get OCR from Vision");
            String ocrResult = visionOps.getTextFromImage(blobBytes);

            log("Save OCR result to Firestore");
            if (!firestoreOps.storeOCRResult(id, ocrResult, ocrRequest.getLanguage())) {
                log(ERROR, "Cannot save OCR result to Firestore");
            }

            log("Delete blob image from Cloud Store");
            if (!storageOps.deleteBlob(ocrRequest.getBlobName())) {
                log(ERROR, "Cannot delete blob from Cloud Store: " + ocrRequest.getBlobName());
            }

            log("Send translate request");
            String translateReqId = publishToTranslate.publishMessage(new TranslateRequest(id, ocrResult, ocrRequest.getLanguage()));
            log("Translate Request: " + translateReqId);

            log("Done");
        } catch (IOException | ExecutionException | InterruptedException e) {
//            ackReplyConsumer.nack(); //TODO
            e.printStackTrace();
        }

    }


}
