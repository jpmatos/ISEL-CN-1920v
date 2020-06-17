package gcloud.vision;

import com.google.protobuf.ByteString;
import dao.OCRResult;

public  interface IVisionOps {
    OCRResult getTextFromImage(ByteString imageBytes);
}