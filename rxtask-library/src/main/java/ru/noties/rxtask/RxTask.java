package ru.noties.rxtask;

import java.util.concurrent.Callable;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 *
 * Created by Dimitry Ivanov on 02.09.2015.
 */
public abstract class RxTask<T> implements Callable<T> {

    private final Scheduler mSubscribeScheduler;
    private final Scheduler mObserveScheduler;

    public RxTask(Scheduler subscribeScheduler, Scheduler observeScheduler) {
        mSubscribeScheduler = subscribeScheduler;
        mObserveScheduler = observeScheduler;
    }

    public RxTask() {
        this(null, null);
    }

    @Override
    public abstract T call() throws Exception;

    public final Observable<T> observable() {
        return observable(null);
    }

    public final Observable<T> observable(CompositeSubscription attachSubscriptionTo) {

        final boolean hasSubscriber     = mSubscribeScheduler != null;
        final boolean hasObserver       = mObserveScheduler != null;
        final boolean hasSubscription   = attachSubscriptionTo != null;

        final Callable<T> callable = this;

        Observable<T> observable = Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(callable.call());
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });

        if (hasObserver) {
            observable = observable.observeOn(mObserveScheduler);
        }

        if (hasSubscriber) {
            observable = observable.subscribeOn(mSubscribeScheduler);
        }

        if (hasSubscription) {
            observable = observable.lift(new RxTaskCompositeSubscriptionOperator<T>(attachSubscriptionTo));
        }

        return observable;
    }
}
