package clientapp.observers;

import CnText.CheckResponse;
import io.grpc.stub.StreamObserver;

public class CheckRequestObserver implements StreamObserver<CheckResponse> {
    @Override
    public void onNext(CheckResponse checkResponse) {
        //TODO
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
        //TODO
    }
}
