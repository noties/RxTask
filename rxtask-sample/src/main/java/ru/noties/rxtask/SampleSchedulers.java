package ru.noties.rxtask;

import java.util.concurrent.Executors;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by Dimitry Ivanov on 02.09.2015.
 */
public class SampleSchedulers {

    private static volatile Scheduler sInstance = null;

    public static Scheduler cached() {
        Scheduler local = sInstance;
        if (local == null) {
            synchronized (SampleSchedulers.class) {
                local = sInstance;
                if (local == null) {
                    local = sInstance = Schedulers.from(Executors.newCachedThreadPool());
                }
            }
        }
        return local;
    }
}
