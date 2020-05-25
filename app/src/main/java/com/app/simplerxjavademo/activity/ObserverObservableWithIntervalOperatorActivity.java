package com.app.simplerxjavademo.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.app.simplerxjavademo.R;
import com.app.simplerxjavademo.models.TaskWithPriority;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ObserverObservableWithIntervalOperatorActivity extends AppCompatActivity {

    private String TAG = this.getClass().getName();
    private Observable<Long> intervaLongObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_observer_observable);

        //emit an observable on every interval
        intervaLongObservable = Observable
                .interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .takeWhile(new Predicate<Long>() {
                    @Override
                    public boolean test(Long aLong) throws Throwable {
                        Log.d(TAG, "test: "+ aLong +" , thread: " + Thread.currentThread().getName());
                        return aLong <= 5;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());

        intervaLongObservable.subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: Called");
            }

            @Override
            public void onNext(@NonNull Long aLong) {
                Log.d(TAG, "onNext: " + Thread.currentThread().getName());
                Log.d(TAG, "onNext: interval: " + aLong);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete Called");
            }
        });
    }

    public List<TaskWithPriority> generateTaskList() {
        List<TaskWithPriority> taskWithPriorities = new ArrayList<>();
        taskWithPriorities.add(new TaskWithPriority("Wake up in morning", false, 0));
        taskWithPriorities.add(new TaskWithPriority("Brush your teeth", false, 2));
        taskWithPriorities.add(new TaskWithPriority("HAving Bath", false, 4));
        taskWithPriorities.add(new TaskWithPriority("Go to work", true, 1));
        taskWithPriorities.add(new TaskWithPriority("Rest after work", true, 3));

        return taskWithPriorities;
    }
}
