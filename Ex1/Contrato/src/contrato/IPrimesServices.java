package contrato;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPrimesServices extends Remote {
    final static String SERVICE_NAME = "RemCNServer";

    void findPrimes(int start, int numberOfPrimes, ICallback listener) throws RemoteException;
}
