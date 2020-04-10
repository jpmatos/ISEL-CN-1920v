package clientapp;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import primesservice.NumOfPrimes;
import primesservice.Number;
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

            while (true) {
                System.out.println(
                        "------------------\n" +
                        "Pick an operation:\n" +
                        "[1] - FindPrimes\n" +
                        "[2] - AddNumbers\n" +
                        "[0] - Quit");
                int oper = sc.nextInt();

                switch (oper) {
                    case 1:
                        findPrimes();
                        break;
                    case 2:
                        addNumbers();
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

    private static void addNumbers() throws InterruptedException {
        SumStreamObserver sumStreamObserver = new SumStreamObserver();
        StreamObserver<Number> reqs = noBlockStub.addNumbers(sumStreamObserver);
        while (true)
        {
            System.out.println("Add number: (0 to finish)");
            int num = sc.nextInt();

            if(num == 0)
                break;

            Number number = Number.newBuilder().setNumber(num).build();
            reqs.onNext(number);
        }

        reqs.onCompleted();
        while (!sumStreamObserver.isCompleted()) {
            System.out.println("Waiting for addition to finish...");
            Thread.sleep(1000);
        }
        System.out.println(String.format("Total sum: %d", sumStreamObserver.getSumResult()));
    }

    private static void findPrimes() throws InterruptedException {
        ArrayList<PrimesStreamObserver> observers = new ArrayList<>();
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
            PrimesStreamObserver replyStreamObserver = new PrimesStreamObserver();
            observers.add(replyStreamObserver);

            noBlockStub.findPrimes(nop, replyStreamObserver); //svc.findPrimes(id, start, numberOfPrimes, callbackStub);
            sc.nextLine(); //clean buffer
        }

        for (PrimesStreamObserver observer : observers) {
            while (!observer.isCompleted()) {
                System.out.println("Waiting for primes to finish...");
                Thread.sleep(1000);
            }
        }
        System.out.println("Completed!");
    }
}
