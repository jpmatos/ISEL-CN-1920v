package gcloud.vision;

import com.google.protobuf.ByteString;

public  interface IVisionOps {
    String getTextFromImage(ByteString imageBytes);
}
