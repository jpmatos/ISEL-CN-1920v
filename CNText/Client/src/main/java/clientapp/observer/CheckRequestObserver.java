package clientapp.observer;

import CnText.CheckResponse;
import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;

import java.util.List;

public class CheckRequestObserver implements StreamObserver<CheckResponse> {
    private List<ByteString> response;

    @Override
    public void onNext(CheckResponse checkResponse) {
        response = checkResponse.getResponseList().asByteStringList();
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Failed to check with Service");
    }

    @Override
    public void onCompleted() {
        if(response != null)
            for (ByteString string : response)
                System.out.println(string.toStringUtf8());
    }
}
