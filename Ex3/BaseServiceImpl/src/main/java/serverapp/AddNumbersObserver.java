package serverapp;

import io.grpc.stub.StreamObserver;
import primesservice.Number;
import primesservice.SumResult;

public class AddNumbersObserver implements StreamObserver<Number> {
    StreamObserver<SumResult> responseObserver;
    int id;
    int sum;


    public AddNumbersObserver(StreamObserver<SumResult> responseObserver, int id) {
        this.responseObserver = responseObserver;
        this.id = id;
        this.sum = 0;
    }

    @Override
    public void onNext(Number number) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sum += number.getNumber();
        System.out.println(String.format("[%d] Added '%d'. Current sum '%d'.", id, number.getNumber(), sum));
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
        SumResult sumResult = SumResult.newBuilder().setSumResult(sum).build();
        responseObserver.onNext(sumResult);
        responseObserver.onCompleted();
        System.out.println(String.format("[%d] Total sum '%d'.", id, sum));
    }
}
