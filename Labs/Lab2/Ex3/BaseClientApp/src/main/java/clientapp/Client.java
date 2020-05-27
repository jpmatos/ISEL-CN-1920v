package clientapp;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import primesservice.*;
import primesservice.Number;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Client {

    private static String svcIP = "localhost";
    private static int svcPort = 8000;
    private static ManagedChannel channel;
    private static PrimesServiceGrpc.PrimesServiceStub noBlockStub;
    private static PrimesServiceGrpc.PrimesServiceBlockingStub blockingStub;
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
            blockingStub = PrimesServiceGrpc.newBlockingStub(channel);

            while (true) {
                System.out.println(
                        "------------------\n" +
                        "Pick an operation:\n" +
                        "[1] - FindPrimesBlocking\n" +
                        "[2] - FindPrimes\n" +
                        "[3] - AddNumbers\n" +
                        "[4] - AddNumbersCont\n" +
                        "[5] - FindPrimesInterval\n" +
                        "[6] - FindPrimesInterval 0-100\n" +
                        "[0] - Quit");
                int oper = sc.nextInt();

                switch (oper) {
                    case 1:
                        findPrimesBlocking();
                        break;
                    case 2:
                        findPrimes();
                        break;
                    case 3:
                        addNumbers();
                        break;
                    case 4:
                        addNumberCont();
                        break;
                    case 5:
                        findPrimesInterval();
                        break;
                    case 6:
                        findPrimes0to100();
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

    private static void findPrimes0to100() throws InterruptedException {
        System.out.print("ID:");
        String id = sc.next();

        ArrayList<PrimesStreamObserver> observers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            PrimesInterval interval = PrimesInterval.newBuilder().setStart(25 * i).setEnd(25 * (i+1)).build();
            PrimesStreamObserver replyStreamObserver = new PrimesStreamObserver(id);
            observers.add(replyStreamObserver);
            noBlockStub.findPrimesInterval(interval, replyStreamObserver);
        }

        for (PrimesStreamObserver observer : observers) {
            while (!observer.isCompleted()) {
                System.out.println("Waiting for [" + observer.getID() + "] to finish...");
                Thread.sleep(1000);
            }
        }
        System.out.println("Finished!");
    }

    private static void findPrimesInterval() throws InterruptedException {
        ArrayList<PrimesStreamObserver> observers = new ArrayList<>();
        while (true) {
            System.out.println("ID: (0 to finish)");
            String id = sc.next();

            if(id.equals("0"))
                break;

            System.out.println("Start:");
            int start = sc.nextInt();

            System.out.println("End: ");
            int end = sc.nextInt();

            PrimesInterval interval = PrimesInterval.newBuilder().setStart(start).setEnd(end).build();
            PrimesStreamObserver replyStreamObserver = new PrimesStreamObserver(id);
            observers.add(replyStreamObserver);

            noBlockStub.findPrimesInterval(interval, replyStreamObserver); //svc.findPrimes(id, start, numberOfPrimes, callbackStub);
            sc.nextLine(); //clean buffer
        }

        for (PrimesStreamObserver observer : observers) {
            while (!observer.isCompleted()) {
                System.out.println("Waiting for [" + observer.getID() + "] to finish...");
                Thread.sleep(1000);
            }
        }
        System.out.println("Finished!");

    }

    private static void addNumberCont() throws InterruptedException {
        SumContStreamObserver sumContStreamObserver = new SumContStreamObserver();
        StreamObserver<OperationRequest> reqs = noBlockStub.addNumbersCont(sumContStreamObserver);
        while (true) {
            System.out.println("First number: (0 to finish)");
            int num1 = sc.nextInt();

            if(num1 == 0)
                break;

            System.out.println("Second number:");
            int num2 = sc.nextInt();

            OperationRequest req = OperationRequest.newBuilder().setNum1(num1).setNum2(num2).build();
            reqs.onNext(req);
        }

        reqs.onCompleted();
        while (!sumContStreamObserver.isCompleted()) {
            System.out.println("Waiting for additions to finish...");
            Thread.sleep(1000);
        }
        System.out.println("Finished!");
    }

    private static void addNumbers() throws InterruptedException {
        SumStreamObserver sumStreamObserver = new SumStreamObserver();
        StreamObserver<Number> reqs = noBlockStub.addNumbers(sumStreamObserver);
        while (true) {
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
            System.out.println("ID: (0 to finish)");
            String id = sc.next();

            if(id.equals("0"))
                break;

            System.out.println("Start:");
            int start = sc.nextInt();

            if(start == 0)
                break;

            System.out.println("Number of Primes: ");
            int numberOfPrimes = sc.nextInt();
            sc.nextLine(); //clean buffer

            NumOfPrimes nop = NumOfPrimes.newBuilder().setNumOfPrimes(numberOfPrimes).setStartNum(start).build();
            PrimesStreamObserver replyStreamObserver = new PrimesStreamObserver(id);
            observers.add(replyStreamObserver);

            noBlockStub.findPrimes(nop, replyStreamObserver);
        }

        for (PrimesStreamObserver observer : observers) {
            while (!observer.isCompleted()) {
                System.out.println("Waiting for [" + observer.getID() + "] to finish...");
                Thread.sleep(1000);
            }
        }
        System.out.println("Finished!");
    }

    private static void findPrimesBlocking() {
        while (true) {
            System.out.println("Start: (0 to finish)");
            int start = sc.nextInt();

            if(start == 0)
                break;

            System.out.println("Number of Primes: ");
            int numberOfPrimes = sc.nextInt();
            sc.nextLine(); //clean buffer

            NumOfPrimes nop = NumOfPrimes.newBuilder().setNumOfPrimes(numberOfPrimes).setStartNum(start).build();

            Iterator<Prime> result = blockingStub.findPrimes(nop);

            result.forEachRemaining(prime -> System.out.println("Result: " + prime.getPrime()));
        }
    }
}
