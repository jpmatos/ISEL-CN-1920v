package serverapp;

import io.grpc.stub.StreamObserver;
import primesservice.OperationReply;
import primesservice.OperationRequest;
import primesservice.SumResult;

public class AddNumbersContObserver implements StreamObserver<OperationRequest> {
    StreamObserver<OperationReply> responseObserver;

    public AddNumbersContObserver(StreamObserver<OperationReply> responseObserver) {
        this.responseObserver = responseObserver;
    }

    @Override
    public void onNext(OperationRequest operationRequest) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int res = operationRequest.getNum1() + operationRequest.getNum2();
        responseObserver.onNext(OperationReply.newBuilder().setRes(res).setId(operationRequest.getId()).build());
        System.out.println(String.format("[%s] Operation '%d+%d=%d'.",
                operationRequest.getId(), operationRequest.getNum1(), operationRequest.getNum2(), res));
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
        responseObserver.onCompleted();
    }
}
