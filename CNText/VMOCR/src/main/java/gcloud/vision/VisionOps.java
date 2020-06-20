package gcloud.vision;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import dao.OCRResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VisionOps implements IVisionOps {

    public OCRResult getTextFromImage(ByteString imgBytes) throws IOException {
        List<AnnotateImageRequest> requests = new ArrayList<>();

        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
        AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                .addFeatures(feat)
                .setImage(img)
                .build();
        requests.add(request);

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();
            AnnotateImageResponse res = responses.iterator().next();

            //Error in OCR reading
            if (res.hasError()) {
                // write in our Log Console
                String errorMsg = res.getError().getMessage();
                throw new IOException(errorMsg);
            } else {
                EntityAnnotation textAnnotations = res.getTextAnnotations(0);
                return new OCRResult(textAnnotations.getDescription(), textAnnotations.getLocale());
            }

        }
    }
}
