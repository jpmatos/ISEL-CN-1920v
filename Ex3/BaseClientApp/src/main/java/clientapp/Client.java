package clientapp;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import primesservice.NumOfPrimes;
import primesservice.PrimesServiceGrpc;

import java.util.ArrayList;
import java.util.Scanner;

public class Client {

    private static String svcIP = "localhost";
    //   private static String svcIP = "35.246.73.129";
    private static int svcPort = 8000;
    private static ManagedChannel channel;
    private static PrimesServiceGrpc.PrimesServiceStub noBlockStub;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            if (args.length == 2) {
                svcIP=args[0];
                svcPort=Integer.parseInt(args[1]);
            }

            channel = ManagedChannelBuilder.forAddress(svcIP, svcPort)
                    // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                    // needing certificates.
                    .usePlaintext()
                    .build();

            noBlockStub = PrimesServiceGrpc.newStub(channel);

            ArrayList<ClientStreamObserver> observers = new ArrayList<>();
            while (true) {
//                System.out.print("ID: ");
//                String id = sc.nextLine();
                System.out.println("Start: (0 to finish)");
                int start = sc.nextInt();

                if(start == 0)
                    break;

                System.out.println("Number of Primes: ");
                int numberOfPrimes = sc.nextInt();

                NumOfPrimes nop = NumOfPrimes.newBuilder().setNumOfPrimes(numberOfPrimes).setStartNum(start).build();
                ClientStreamObserver replyStreamObserver = new ClientStreamObserver();
                observers.add(replyStreamObserver);

                noBlockStub.findPrimes(nop, replyStreamObserver); //svc.findPrimes(id, start, numberOfPrimes, callbackStub);
                sc.nextLine(); //clean buffer
            }

            System.out.println("Waiting for observers to complete...");
            for (ClientStreamObserver observer : observers) {
                while (!observer.isCompleted())
                    Thread.sleep(1000);
            }
            System.out.println("Completed!");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
