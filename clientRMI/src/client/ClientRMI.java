package client;

import contrato.ICallback;
import contrato.IPrimesServices;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;
import java.util.Scanner;

import static contrato.IPrimesServices.SERVICE_NAME;

public class ClientRMI {
    private final static String SERVER_IP = "localhost";
    private final static String CLIENT_IP = "localhost";
    private final static int REGISTER_PORT = 7000;

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            // lookup RMI server in registry
            Registry registry = LocateRegistry.getRegistry(SERVER_IP, REGISTER_PORT);
            IPrimesServices svc = (IPrimesServices) registry.lookup(SERVICE_NAME);

            Properties props = System.getProperties();
            props.put("java.rmi.server.hostname", CLIENT_IP);

            ICallback callback = new CuriousAboutPrimes();

            ICallback callbackStub = (ICallback) UnicastRemoteObject.exportObject(callback, 0);
            System.out.println("Callback registerd @ " + callbackStub);


            while (true) {
                System.out.print("ID: ");
                String id = sc.nextLine();
                System.out.print("Start: ");
                int start = sc.nextInt();
                System.out.print("Number of Primes: ");
                int numberOfPrimes = sc.nextInt();
                svc.findPrimes(id, start, numberOfPrimes, callbackStub);
                sc.nextLine(); //clean buffer
            }

//            while (!callbackStub.isFinished()) {}
//            System.exit(0);


//            System.out.print("Start: ");
//            int start = sc.nextInt();
//            System.out.print("Number of Primes: ");
//            int numberOfPrimes = sc.nextInt();
//            svc.findPrimes("dummy", start, numberOfPrimes, callbackStub);
//
//            while(!callbackStub.isFinished()){}
//
//            System.exit(0);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }


    }
}
