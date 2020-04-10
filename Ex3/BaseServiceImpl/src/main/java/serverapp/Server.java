package serverapp;


import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import primesservice.NumOfPrimes;
import primesservice.Prime;
import primesservice.PrimesServiceGrpc;

import java.util.Scanner;

public class Server extends PrimesServiceGrpc.PrimesServiceImplBase {
    private static int svcPort=8000;

    public static void main(String[] args) {
        try {
            if (args.length > 0) svcPort=Integer.parseInt(args[0]);
            io.grpc.Server svc = ServerBuilder
                    .forPort(svcPort)
                    .addService(new Server())
                    .build();
            svc.start();
            System.out.println("Server started, listening on " + svcPort);
            Scanner scan=new Scanner(System.in);
            scan.nextLine();
            svc.shutdown();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void findPrimes(NumOfPrimes request, StreamObserver<Prime> responseObserver) {
        System.out.println("FindPrimes called");
        int id = 1000 + (int)(Math.random() * 1000);
        int found = 0;
        for (int i = 0; found <= request.getNumOfPrimes(); i++) {
            int curr = request.getStartNum() + i;
            if(isPrime(curr))
            {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(String.format("[%d] Found prime: %d", id, curr));
                found++;
                responseObserver.onNext(Prime.newBuilder().setPrime(curr).build());
            }
        }
        responseObserver.onCompleted();
    }

    private static boolean isPrime(int num) {
        if (num <= 1) return false;
        if (num == 2 || num == 3) return true;
        if (num % 2 == 0) return false;
        for (int i = 3; i <= Math.sqrt(num); i += 2) {
            if (num % i == 0) return false;
        }
        return true;
    }
}
