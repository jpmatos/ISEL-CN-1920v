package gcloud.storage;

import com.google.cloud.ReadChannel;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.*;
import com.google.protobuf.ByteString;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StorageOps implements IStorageOps {
    public static final String BUCKET_NAME = "image-store-cng01";
    private final Storage storage;

    public StorageOps() {
        StorageOptions options = StorageOptions.getDefaultInstance();
        this.storage = options.getService();
    }

    public void downloadBlod(String path, String blobName) throws IOException {
        Path downloadTo = Paths.get(path);
        BlobId blobId = BlobId.of(BUCKET_NAME, blobName);
        Blob blob = storage.get(blobId);
        PrintStream writeTo = new PrintStream(new FileOutputStream(downloadTo.toFile()));

        if (blob.getSize() < 1_000_000) {
            // Blob is small read all its content in one request
            byte[] content = blob.getContent();
            writeTo.write(content);
        } else { // When Blob size is big use the blob's channel reader
            try (ReadChannel reader = blob.reader()) {
                WritableByteChannel channel = Channels.newChannel(writeTo);
                ByteBuffer bytes = ByteBuffer.allocate(64 * 1024);
                while (reader.read(bytes) > 0) {
                    bytes.flip();
                    channel.write(bytes);
                    bytes.clear();
                }
            }
        }
        writeTo.close();
    }

    public ByteString getBlobToByteString(String blobName) throws IOException {
        Blob blob = getBlob(blobName);
        ByteString.Output writeTo = ByteString.newOutput();

        if (blob.getSize() < 1_000_000) {
            // Blob is small read all its content in one request
            byte[] content = blob.getContent();
            writeTo.write(content);
        } else { // When Blob size is big use the blob's channel reader
            try (ReadChannel reader = blob.reader()) {
                WritableByteChannel channel = Channels.newChannel(ByteString.newOutput());
                ByteBuffer bytes = ByteBuffer.allocate(64 * 1024);
                while (reader.read(bytes) > 0) {
                    bytes.flip();
                    channel.write(bytes);
                    bytes.clear();
                }
            }
        }
        writeTo.close();
        return writeTo.toByteString();
    }

    @Override
    public boolean deleteBlob(String blobName) throws IOException {
        Blob blob = getBlob(blobName);
        return storage.delete(blob.getBlobId());
    }


    @Override
    public BlobId uploadBlob(String path, String blobName) throws IOException {
        Path uploadFrom = Paths.get(path);
        String contentType = Files.probeContentType(uploadFrom);
        BlobId blobId = BlobId.of(BUCKET_NAME, blobName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(contentType)
                .build();

        if (Files.size(uploadFrom) < 1_000_000) {
            byte[] bytes = Files.readAllBytes(uploadFrom);
            // create the blob in one request.
            storage.create(blobInfo, bytes);
        } else {
            //Blob greather than 1Mbyte
            try (WriteChannel writer = storage.writer(blobInfo)) {
                byte[] buffer = new byte[1024];
                try (InputStream input = Files.newInputStream(uploadFrom)) {
                    int limit;
                    while ((limit = input.read(buffer)) >= 0) {
                        try {
                            writer.write(ByteBuffer.wrap(buffer, 0, limit));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }

        return blobId;
    }

    private Blob getBlob(String blobName) throws IOException {
        BlobId blobId = BlobId.of(BUCKET_NAME, blobName);
        Blob blob = storage.get(blobId);
        if (blob == null)
            throw new IOException("Cannot find blob");
        return blob;
    }
}
