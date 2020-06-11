package gcloud.vision;

import com.google.protobuf.ByteString;

public class IVisionOpsDummy implements IVisionOps {

    @Override
    public String getTextFromImage(ByteString imageBytes) {
        return "Dummy Text";
    }
}
