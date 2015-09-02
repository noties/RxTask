package ru.noties.rxtask;

import java.util.concurrent.Executors;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by Dimitry Ivanov on 02.09.2015.
 */
public class RxTaskSchedulers {

    private static volatile RxTaskSchedulers sInstance = null;

    private static RxTaskSchedulers getInstance() {
        RxTaskSchedulers local = sInstance;
        if (local == null) {
            synchronized (RxTaskSchedulers.class) {
                local = sInstance;
                if (local == null) {
                    local = sInstance = new RxTaskSchedulers();
                }
            }
        }
        return local;
    }

    private final Scheduler mCachedScheduler;

    private RxTaskSchedulers() {
        mCachedScheduler = Schedulers.from(Executors.newCachedThreadPool());
    }

    /**
     * {@link Executors#newCachedThreadPool()} is known to be suitable for big number of short running tasks
     * @see Executors#newCachedThreadPool()
     * @return {@link Scheduler} created from {@link Executors#newCachedThreadPool()}
     */
    public static Scheduler cached() {
        return RxTaskSchedulers.getInstance().mCachedScheduler;
    }
}
