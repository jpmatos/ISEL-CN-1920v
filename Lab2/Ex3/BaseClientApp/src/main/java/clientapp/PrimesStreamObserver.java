package clientapp;

import io.grpc.stub.StreamObserver;
import primesservice.Prime;

public class PrimesStreamObserver implements StreamObserver<Prime> {
    private boolean isCompleted=false;
    private boolean success=false;
    private String id;

    public PrimesStreamObserver(String id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public boolean OnSuccesss() {
        return success;
    }

    public String getID() {
        return id;
    }

    @Override
    public void onNext(Prime prime) {
//        System.out.println("Prime found: " + prime.getPrime());
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Error on call: " + throwable.getMessage());
        isCompleted=true;
        success=false;
    }

    @Override
    public void onCompleted() {
//        System.out.println("Stream completed");
        isCompleted=true;
        success=true;
    }
}
