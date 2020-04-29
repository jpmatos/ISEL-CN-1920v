package clientapp;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.protobuf.Empty;
import forum.ForumGrpc;
import forum.ForumMessage;
import forum.SubscribeUnSubscribe;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {

    private static String svcIP = "localhost";
    private static int svcPort = 8000;
    private static ManagedChannel channel;
    private static ForumGrpc.ForumStub stub;
    private static Scanner sc = new Scanner(System.in);
    private static Queue<Pair<String, LocalDateTime>> messages = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) {
        try {
            if (args.length == 2) {
                svcIP=args[0];
                svcPort=Integer.parseInt(args[1]);
            }
            String googleCredentialFile = "";
            if (args.length == 3) {
                googleCredentialFile = args[2];
            }
            channel = ManagedChannelBuilder.forAddress(svcIP, svcPort)
                    // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                    // needing certificates.
                    .usePlaintext()
                    .build();
            stub = ForumGrpc.newStub(channel);

            new Thread(() -> {
                try {
                    verifyBlobTimeout();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

            while (true) {
                System.out.println(
                        "------------------\n" +
                                "Pick an operation:\n" +
                                "[1] - Get All Topics\n" +
                                "[2] - Publish Message\n" +
                                "[3] - Subscribe Topic\n" +
                                "[4] - Unsubscribe Topic\n" +
                                "[0] - Quit");
                int oper = sc.nextInt();

                switch (oper) {
                    case 1:
                        getAllTopics();
                        break;
                    case 2:
                        messagePublish();
                        break;
                    case 3:
                        topicSubscribe(googleCredentialFile);
                        break;
                    case 4:
                        topicUnsubscribe();
                        break;
                    case 0:
                        System.exit(0);
                    default:
                        System.out.println("Unknown operation");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void topicUnsubscribe() {
        System.out.println("User:");
        String user = sc.next();

        System.out.println("Topic Name:");
        String topic = sc.next();

        SubscribeUnSubscribe unsub = SubscribeUnSubscribe.newBuilder().setUsrName(user).setTopicName(topic).build();
//        SubscribeUnSubscribe unsub = SubscribeUnSubscribe.newBuilder().setUsrName("joao").setTopicName("topic01").build();
        EmptyStreamObserver emptyStreamObserver = new EmptyStreamObserver();
        stub.topicUnSubscribe(unsub, emptyStreamObserver);
    }

    private static void topicSubscribe(String googleCredentialFile) throws IOException {
        System.out.println("User:");
        String user = sc.next();

        System.out.println("Topic Name:");
        String topic = sc.next();

        System.out.println("Download Location:");
        String downloadLocation = sc.next();

        System.out.println("Set an ID:");
        String id = sc.next();

        StorageOptions storageOptions;
        if (googleCredentialFile.equals("")) {
            storageOptions = StorageOptions.getDefaultInstance();
        } else {
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(googleCredentialFile));
            storageOptions = StorageOptions.newBuilder().setCredentials(credentials).build();
        }
        SubscribeUnSubscribe sub = SubscribeUnSubscribe.newBuilder().setUsrName(user).setTopicName(topic).build();
        ForumMessageStreamObserver forumMessageStreamObserver = new ForumMessageStreamObserver(storageOptions, downloadLocation, id);
//        SubscribeUnSubscribe sub = SubscribeUnSubscribe.newBuilder().setUsrName("joao").setTopicName("topic01").build();
//        ForumMessageStreamObserver forumMessageStreamObserver = new ForumMessageStreamObserver(storageOptions, "/home/jpmatos/Downloads/", "obs1");
        stub.topicSubscribe(sub,forumMessageStreamObserver);
    }

    private static void messagePublish() {
        System.out.println("User:");
        String user = sc.next();

        System.out.println("Topic Name:");
        String topic = sc.next();

        System.out.println("Text Message: <text>[;<bucket>;<blob>]");
        String text = sc.next();

        ForumMessage req = ForumMessage.newBuilder().setFromUser(user).setTopicName(topic).setTxtMsg(text).build();
//        ForumMessage req = ForumMessage.newBuilder().setFromUser("joao").setTopicName("topic01").setTxtMsg("msg01[;bucket-g1-aus;world.tif]").build();
        EmptyStreamObserver emptyStreamObserver = new EmptyStreamObserver();
        stub.messagePublish(req, emptyStreamObserver);
        messages.add(new Pair<>(text, LocalDateTime.now()));
    }

    private static void getAllTopics() throws InterruptedException {
        TopicStreamObserver topicStreamObserver = new TopicStreamObserver();
        stub.getAllTopics(Empty.newBuilder().build(), topicStreamObserver);
        while (!topicStreamObserver.isComplete())
            Thread.sleep(1000);
    }

    private static void verifyBlobTimeout() throws InterruptedException {
        Storage storage = StorageOptions.getDefaultInstance().getService();

        while (true){

            //Wait 10 seconds before checking timeouts
            Thread.sleep(10000);
            for (Pair pair : messages) {

                //Separate pair
                String message = (String)pair.getKey();
                LocalDateTime messageTime = (LocalDateTime) pair.getValue();

                //Apply regex to message
                Matcher match = Pattern.compile("\\[(.*?)\\]").matcher(message);
                match.find();
                String info = match.group(1);
                if(info.length() > 0){

                    //Split info
                    String[] split = info.split(";");
                    String bucketName = split[1];
                    String blobName = split[2];

                    //Get blob
                    BlobId blobId = BlobId.of(bucketName, blobName);
                    Blob blob = storage.get(blobId);
                    Map<String, String> metadata = blob.getMetadata();

                    //See if blob has Timeout
                    if(!metadata.containsKey("Timeout"))
                        continue;

                    //Make blob private if Timeout already passed
                    long timeout = Long.parseLong(metadata.get("Timeout"));
                    if(messageTime.plusMinutes(timeout).isAfter(LocalDateTime.now()))
                        continue;

                    System.out.println(String.format("Blob 'gs://%s/%s' exceeded timeout of %d minutes! Making it private...", bucketName, blobName, timeout));
                    blob.deleteAcl(Acl.User.ofAllUsers());
                    messages.remove(pair);
                }
            }
        }
    }
}
