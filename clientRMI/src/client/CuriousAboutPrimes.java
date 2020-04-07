package client;

import contrato.ICallback;

import java.rmi.RemoteException;

public class CuriousAboutPrimes implements ICallback {
    private boolean finished = false;

    @Override
    public void nextPrime(int i) throws RemoteException {
//        System.out.println("Numero primo encontrado pelo servidor: " + i);
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
