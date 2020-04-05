package contract;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPrimesService extends Remote {
    void findPrimes(int start, int numberOfPrimes, ICallback listener) throws RemoteException;
}
