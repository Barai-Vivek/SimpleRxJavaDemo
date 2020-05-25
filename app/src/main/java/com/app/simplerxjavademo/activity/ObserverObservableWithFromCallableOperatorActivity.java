package com.app.simplerxjavademo.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.app.simplerxjavademo.R;
import com.app.simplerxjavademo.models.TaskWithPriority;

import java.util.concurrent.Callable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ObserverObservableWithFromCallableOperatorActivity extends AppCompatActivity {

    private String TAG = this.getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_observer_observable);

        // create Observable (method will not execute yet)
        Observable<TaskWithPriority> callable = Observable
                .fromCallable(new Callable<TaskWithPriority>() {
                    @Override
                    public TaskWithPriority call() throws Exception {
                        Log.d(TAG, "call: " + Thread.currentThread().getName());
                        return (new TaskWithPriority("Wake up in morning", false, 0));//you can also retrive from database query it can also be List[T]
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        // method will be executed since now something has subscribed
        callable.subscribe(new Observer<TaskWithPriority>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: Called");
            }

            @Override
            public void onNext(TaskWithPriority task) {
                Log.d(TAG, "onNext: " + Thread.currentThread().getName());
                Log.d(TAG, "onNext: : " + task.getDescription());
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete Called");
            }
        });
    }
}
