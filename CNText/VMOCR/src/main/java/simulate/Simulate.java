package simulate;

import com.google.cloud.storage.BlobId;
import dao.OCRRequest;
import gcloud.pubsub.PublishTopic;
import gcloud.storage.IStorageOps;
import gcloud.storage.StorageOps;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static utils.Console.PrintType.ERROR;
import static utils.Console.print;

public class Simulate {

    public static void main(String[] args) throws IOException {
        simulateOCRRequest();
    }


    private static void simulateOCRRequest() throws IOException {
        String blobName = "image-for-ocr-translate.JPG";
        String fileToUpload = "C:\\Users\\helio.fitas\\Dropbox\\Helio\\ISEL\\2019-2020 SV\\CN\\LABs\\Final\\" + blobName;
        IStorageOps storageOps = new StorageOps();

        print("Try to upload blob");
        BlobId blobId = storageOps.uploadBlob(fileToUpload, blobName);
        if (blobId == null) {
            print(ERROR, "Cannot Upload blob");
            return;
        } else {
            print("Blob uploaded successfully!");
        }


        try (PublishTopic publishTopic = new PublishTopic("free-ocr")) {
            String msgID = publishTopic.publishMessage(
                    new OCRRequest(
                            "1234",
                            blobName,
                            "EN"
                    ));

            print("Request submited with ID: " + msgID);
        } catch (IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
