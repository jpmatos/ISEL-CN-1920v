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
    private static String serverIP = "localhost";
    private static String clientIp = "localhost";
    private static int registerPort = 7000;

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            System.out.println("Parameters order: clientIp serverIp registerPort");
            if (args.length > 0) clientIp = args[0];
            if (args.length > 1) serverIP = args[1];
            if (args.length > 2) registerPort = Integer.parseInt(args[2]);

            // lookup RMI server in registry
            Registry registry = LocateRegistry.getRegistry(serverIP, registerPort);
            IPrimesServices svc = (IPrimesServices) registry.lookup(SERVICE_NAME);

            Properties props = System.getProperties();
            props.put("java.rmi.server.hostname", clientIp);

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
