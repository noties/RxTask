package ru.noties.rxtask.test;

import junit.framework.TestCase;

import ru.noties.rxtask.RxTask;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Dimitry Ivanov on 02.09.2015.
 */
public class RxTaskTest extends TestCase {

    public void testSubscription() {
        final RxTask<Void> task = new RxTask<Void>(Schedulers.io(), null) {
            @Override
            public Void call() throws Exception {

                // is called
                assertTrue(true);

                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        final CompositeSubscription compositeSubscription = new CompositeSubscription();
        task.observable(compositeSubscription)
                .subscribe();
        assertTrue(compositeSubscription.hasSubscriptions());
    }
}
