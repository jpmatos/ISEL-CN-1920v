package clientapp;

import io.grpc.stub.StreamObserver;
import primesservice.SumResult;

public class SumStreamObserver implements StreamObserver<SumResult> {
    private boolean isCompleted=false;
    private boolean success=false;
    private int sumResult;

    public int getSumResult(){
        if(isCompleted)
            return sumResult;
        else
            return 0;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public boolean OnSuccesss() {
        return success;
    }

    @Override
    public void onNext(SumResult sumResult) {
//        System.out.println("Sum result: " + sumResult.getSumResult());
        this.sumResult = sumResult.getSumResult();
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
