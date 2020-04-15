package client;

import contrato.ICallback;
import contrato.IPrimesServices;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
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

            ArrayList<ICallback> callbacks = new ArrayList<>();
            while (true) {
                System.out.println("ID: (0 to finish)");
                String id = sc.nextLine();

                if(id.equals("0"))
                    break;

                System.out.println("Start: ");
                int start = sc.nextInt();
                System.out.println("Number of Primes: ");
                int numberOfPrimes = sc.nextInt();

                ICallback callback = new CuriousAboutPrimes(id);
                ICallback callbackStub = (ICallback) UnicastRemoteObject.exportObject(callback, 0);
                System.out.println("Callback registerd @ " + callbackStub);
                callbacks.add(callback);

                svc.findPrimes(start, numberOfPrimes, callbackStub);
                sc.nextLine(); //clean buffer
            }

            for (ICallback cb : callbacks) {
                while (!cb.isFinished()) {
                    System.out.println("Waiting for all callbacks...");
                    Thread.sleep(1000);
                }
            }
            System.exit(0);
        } catch (RemoteException | NotBoundException | InterruptedException e) {
            e.printStackTrace();
        }


    }
}
