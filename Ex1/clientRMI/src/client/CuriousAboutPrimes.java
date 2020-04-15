package client;

import contrato.ICallback;

import java.rmi.RemoteException;

public class CuriousAboutPrimes implements ICallback {
    private boolean finished = false;
    private String id;

    public CuriousAboutPrimes(String id) {
        this.id = id;
    }

    @Override
    public void nextPrime(int prime) throws RemoteException {
        //System.out.println(String.format("[%s] Found prime: %d", id, prime));
    }

    @Override
    public void endOfWork() throws RemoteException {
        finished = true;
    }

    @Override
    public boolean isFinished() throws RemoteException {
        return finished;
    }

}
