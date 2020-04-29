package clientapp;

import com.google.cloud.ReadChannel;
import forum.ForumMessage;
import io.grpc.stub.StreamObserver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;

public class ForumMessageStreamObserver implements StreamObserver<ForumMessage> {
    StorageOptions storageOptions;
    Storage storage;
    String id;
    String downloadLocation;

    public ForumMessageStreamObserver(StorageOptions storageOptions, String downloadLocation, String id) {
        this.storageOptions = storageOptions;
        this.storage = StorageOptions.getDefaultInstance().getService();
        this.id = id;
        this.downloadLocation = downloadLocation;
    }

    @Override
    public void onNext(ForumMessage forumMessage) {
        System.out.println(String.format("[%s] User: '%s' TopicName: '%s' Message: '%s'",
                id, forumMessage.getFromUser(), forumMessage.getTopicName(), forumMessage.getTxtMsg()));

        Matcher match = Pattern.compile("\\[(.*?)\\]").matcher(forumMessage.getTxtMsg());
        match.find();
        String info = match.group(1);
        if(info.length() > 0){
            String[] split = info.split(";");
            String bucket = split[1];
            String blob = split[2];
            System.out.println(String.format("[%s] Downloading '%s/%s'...", id, bucket, blob));
            downloadBlob(bucket, blob);
            System.out.println(String.format("[%s] Done '%s/%s'", id, bucket, blob));
        }
    }

    private void downloadBlob(String bucketName, String blobName) {
        try{
            Path downloadTo = Paths.get(downloadLocation + blobName);
            BlobId blobId = BlobId.of(bucketName, blobName);
            Blob blob = storage.get(blobId);
            if (blob == null) {
                System.out.println("No such Blob exists !");
                return;
            }
            PrintStream writeTo = new PrintStream(new FileOutputStream(downloadTo.toFile()));
            if (blob.getSize() < 1_000_000) {
                // Blob is small read all its content in one request
                byte[] content = blob.getContent();
                writeTo.write(content);
            } else {
                // When Blob size is big or unknown use the blob's channel reader.
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
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
        System.out.println(String.format("[%s] Unsubscribed", id));
    }
}
