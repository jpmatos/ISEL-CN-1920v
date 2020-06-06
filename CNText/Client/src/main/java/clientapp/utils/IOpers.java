package clientapp.utils;

import clientapp.observers.TranslateRequestObserver;
import clientapp.observers.UploadRequestObserver;

public interface IOpers {
//    void consult(UploadRequestResponse urResponse);
    void addToCompletedObserversList(UploadRequestObserver urObserver);
    void addToCompletedTranslationsList(TranslateRequestObserver trObserver);
}
