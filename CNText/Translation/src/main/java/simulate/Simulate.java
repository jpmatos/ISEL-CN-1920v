package simulate;

import dao.TranslateRequest;
import gcloud.pubsub.PublishTopic;
import gcloud.translate.ITranslateOps;
import gcloud.translate.TranslateOps;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static utils.Output.log;

public class Simulate {

        public static void main(String[] args) throws IOException {
            simulateRequest();
        }


        private static void simulateRequest() throws IOException {
            //String blobName = "image-for-ocr-translate.JPG";
            //String blobName = "covid19.JPG";
            String blobName = "Apptio.pdf";
            //String fileToUpload = "C:\\Users\\helio.fitas\\Dropbox\\Helio\\ISEL\\2019-2020 SV\\CN\\LABs\\Final\\" + blobName;
            //String fileToUpload="C:\\Users\\tiagogsilva\\Documents\\V2020\\ISEL\\CN\\Simulate\\ISEL-CN-1920v-master\\Images\\" + blobName;
            String fileToUpload="C:\\Users\\tiagogsilva\\Desktop\\" + blobName;
            ITranslateOps translateOps = new TranslateOps();

           // ByteString imageToDected =storageOps.getBlobToByteString(blobName);
            // OCRResult textToTranslate =  visionOps.getTextFromImage(imageToDected);
            String texto= "This certificate acknowledges successful completion and passing of";
            String msgID;
            try (PublishTopic publishTopic = new PublishTopic("free-translate")) {
                msgID = publishTopic.publishMessage( new TranslateRequest(
                    "123456789",
                    texto,
                    "it",
                    "en"
            ));
                log("Request submited with ID: " + msgID);
            }  catch (IOException | ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }

        }
}
