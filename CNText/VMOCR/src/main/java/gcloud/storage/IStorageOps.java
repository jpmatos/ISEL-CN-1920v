package gcloud.storage;

import com.google.cloud.storage.BlobId;
import com.google.protobuf.ByteString;

import java.io.IOException;

public interface IStorageOps {
    void downloadBlod(String path, String blobName) throws IOException; //TODO delete

    ByteString getBlobToByteString(String blobName) throws IOException;

    boolean deleteBlob(String blobName) throws IOException;

    BlobId uploadBlob(String path, String blobName) throws IOException;
}
