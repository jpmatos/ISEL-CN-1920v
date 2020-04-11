package clientapp;

import io.grpc.stub.StreamObserver;
import primesservice.OperationReply;

public class SumContStreamObserver implements StreamObserver<OperationReply> {
    private boolean isCompleted=false;
    private boolean success=false;

    public boolean isCompleted() {
        return isCompleted;
    }

    public boolean OnSuccesss() {
        return success;
    }

    @Override
    public void onNext(OperationReply operationReply) {
//        System.out.println(String.format("[%s] Result '%d'.", operationReply.getId(), operationReply.getRes()));
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
//        System.out.println("Stream completed");
        isCompleted=true;
        success=true;
    }
}
