package server;

import contrato.ICallback;

import java.rmi.RemoteException;

public class Worker implements Runnable {
    private int start;
    private int numberOfPrimes;
    private ICallback listener;

    public Worker(int start, int numberOfPrimes, ICallback listener) {
        this.start = start;
        this.numberOfPrimes = numberOfPrimes;
        this.listener = listener;
    }

    @Override
    public void run() {
        printId("Worker started");
        try {
            for (int i = start; numberOfPrimes != 0; i++, numberOfPrimes--) {
                if (isPrime(i)) {
                    Thread.sleep(1000);
                    printId("Found prime: " + i);
                    listener.nextPrime(i);
                }
            }
            listener.endOfWork();
            printId("Worker END");
        } catch (RemoteException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void printId(String msg) {
        long threadId = Thread.currentThread().getId();
        System.out.println("[Thread:" + threadId + "] " + msg);
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
