package ru.noties.rxtask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import ru.noties.debug.Debug;
import ru.noties.debug.out.AndroidLogDebugOutput;
import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Debug.init(new AndroidLogDebugOutput(true));

        final CompositeSubscription compositeSubscription = new CompositeSubscription();

        new SampleTask()
                .observable(compositeSubscription)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Debug.i("thread: %s, s: %s", Thread.currentThread(), s);
                    }
                });
        Debug.i("hasSubscriptions: %s", compositeSubscription.hasSubscriptions());
    }

    private static class SampleTask extends RxTask<String> {

        public SampleTask() {
            super(RxTaskSchedulers.cached(), AndroidSchedulers.mainThread());
        }

        @Override
        public String call() throws Exception {

            Debug.i("Thread: %s", Thread.currentThread());

            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                Debug.e(e);
            }

            return getClass().getCanonicalName();
        }
    }
}
