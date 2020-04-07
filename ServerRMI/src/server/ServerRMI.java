package server;

import contrato.ICallback;
import contrato.IPrimesServices;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ServerRMI implements IPrimesServices {
    private final static int REGISTER_PORT = 7000;
    private final static int SVC_PORT = 7001;

    static ServerRMI svc = null;
    static ExecutorService executor = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        try {
            Properties props = System.getProperties();
            props.put("java.rmi.server.hostname", args[0]);

            // create server object
            svc = new ServerRMI();

            IPrimesServices stubSvc = (IPrimesServices) UnicastRemoteObject.exportObject(svc, SVC_PORT);

            // create local registry
            Registry registry = LocateRegistry.createRegistry(REGISTER_PORT);

            System.out.println("Server registerd @ " + stubSvc);
            registry.rebind(SERVICE_NAME, stubSvc);  // register skeleton on registry

            System.out.println("Server ready: Press any key to finish server");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            System.exit(0);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            System.err.println("Server unhandled exception: " + ex.toString());
            ex.printStackTrace();
        }
    }

    @Override
    public void findPrimes(String workerId, int start, int numberOfPrimes, ICallback listener) throws RemoteException {
//        try {
//            while (true) {
//                System.out.println(Thread.currentThread().getId());
//                Thread.sleep(1000);
//            }
//        }catch (InterruptedException ex){
//            ex.printStackTrace();
//        }

        Runnable worker = new Worker(workerId, start, numberOfPrimes, listener);
        executor.execute(worker);
    }

}
