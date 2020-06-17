package gcloud.vision;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import dao.OCRResult;
import utils.Output;

import java.util.ArrayList;
import java.util.List;

import static utils.Output.log;

public class VisionOps implements IVisionOps {

    public OCRResult getTextFromImage(ByteString imageBytes) {
        try {
            return detectText(imageBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static OCRResult detectText(ByteString imgBytes) throws Exception {
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
                log(Output.OutputType.ERROR, res.getError().getMessage());
                return null; //TODO
            } else {
                EntityAnnotation textAnnotations = res.getTextAnnotations(0);
                return new OCRResult(textAnnotations.getDescription(), textAnnotations.getLocale());
            }

        }
    }
}
