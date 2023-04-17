package com.example.TalanCDZ.helper;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class KafkaObservable implements Observer<String>, ObservableOnSubscribe<String> {
    private static KafkaObservable instance = null;
    private PublishSubject<String> subject;

    private KafkaObservable() {
        this.subject = PublishSubject.create();
    }

    public static synchronized KafkaObservable getInstance() {
        if (instance == null) {
            instance = new KafkaObservable();
        }
        return instance;
    }


    public synchronized void update(Observable o, Object arg) {
        sendMessage(arg.toString());
    }

    public synchronized void sendMessage(String message) {
        subject.onNext(message);
    }

    @Override
    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
        subject.subscribe(emitter::onNext, emitter::onError, emitter::onComplete);
    }

    public Observable<String> getObservable() {
        return Observable.create(this);
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull String s) {

    }

    @Override
    public void onError(@NonNull Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}


