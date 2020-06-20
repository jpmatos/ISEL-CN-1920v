package gcloud.vision;

import com.google.protobuf.ByteString;
import dao.OCRResult;

import java.io.IOException;

public  interface IVisionOps {
    OCRResult getTextFromImage(ByteString imageBytes) throws IOException;
}