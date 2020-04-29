package clientapp;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.StorageOptions;
import com.google.protobuf.Empty;
import forum.ForumGrpc;
import forum.ForumMessage;
import forum.SubscribeUnSubscribe;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Client {

    private static String svcIP = "localhost";
    private static int svcPort = 8000;
    private static ManagedChannel channel;
    private static ForumGrpc.ForumStub stub;
    private static Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {
        try {
            channel = ManagedChannelBuilder.forAddress(svcIP, svcPort)
                    // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                    // needing certificates.
                    .usePlaintext()
                    .build();
            stub = ForumGrpc.newStub(channel);

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
                        topicSubscribe(args);
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

    private static void topicSubscribe(String[] args) throws IOException {
        System.out.println("User:");
        String user = sc.next();

        System.out.println("Topic Name:");
        String topic = sc.next();

        System.out.println("Download Location:");
        String downloadLocation = sc.next();

        System.out.println("Set an ID:");
        String id = sc.next();

        StorageOptions storageOptions;
        if (args.length == 0) {
            storageOptions = StorageOptions.getDefaultInstance();
        } else {
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(args[0]));
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

        System.out.println("Text Message: *<text>[;<bucket>;<blob>]*");
        String text = sc.next();

        ForumMessage req = ForumMessage.newBuilder().setFromUser(user).setTopicName(topic).setTxtMsg(text).build();
//        ForumMessage req = ForumMessage.newBuilder().setFromUser("joao").setTopicName("topic01").setTxtMsg("msg01[;bucket-g1-aus;world.tif]").build();
        EmptyStreamObserver emptyStreamObserver = new EmptyStreamObserver();
        stub.messagePublish(req, emptyStreamObserver);
    }

    private static void getAllTopics() throws InterruptedException {
        TopicStreamObserver topicStreamObserver = new TopicStreamObserver();
        stub.getAllTopics(Empty.newBuilder().build(), topicStreamObserver);
        while (!topicStreamObserver.isComplete())
            Thread.sleep(1000);
    }
}
