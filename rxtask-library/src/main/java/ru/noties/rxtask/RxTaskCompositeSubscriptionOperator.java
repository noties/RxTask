package ru.noties.rxtask;

import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Dimitry Ivanov on 02.09.2015.
 */
class RxTaskCompositeSubscriptionOperator<T> implements Observable.Operator<T, T> {

    private final CompositeSubscription mCompositeSubscription;

    RxTaskCompositeSubscriptionOperator(CompositeSubscription compositeSubscription) {
        this.mCompositeSubscription = compositeSubscription;
    }

    @Override
    public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
        mCompositeSubscription.add(subscriber);
        return subscriber;
    }
}
