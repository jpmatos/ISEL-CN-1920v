package gcloud.vision;

import com.google.protobuf.ByteString;
import dao.OCRResult;

public class VisionOpsDummy implements IVisionOps {

    @Override
    public OCRResult getTextFromImage(ByteString imageBytes) {
        return new OCRResult("Dummy Text", "en");
    }
}
