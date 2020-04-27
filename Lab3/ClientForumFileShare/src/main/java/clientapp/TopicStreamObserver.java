package clientapp;

import io.grpc.stub.StreamObserver;
import forum.ExistingTopics;

public class TopicStreamObserver implements StreamObserver<ExistingTopics> {
    private boolean isComplete = false;

    @Override
    public void onNext(ExistingTopics existingTopics) {
        for (String curr : existingTopics.getTopicNameList()) {
            System.out.println("Topic: " + curr);
        }
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
        isComplete = true;
    }

    public boolean isComplete(){
        return isComplete;
    }
}
