package com.example.administrator.cookman.model.executer;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;


/**
 * Created by PeOS on 2016/9/6 0006.
 */
public class RxJavaExecuter {

    private ThreadExecutor threadExecutor;
    private PostExecutionThread postExecutionThread;

    private Subscription subscription = Subscriptions.empty();

    public RxJavaExecuter(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread){
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    public void execute(Observable observable, Subscriber subscriber) {
        this.subscription = observable
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .subscribe(subscriber);
    }
}
