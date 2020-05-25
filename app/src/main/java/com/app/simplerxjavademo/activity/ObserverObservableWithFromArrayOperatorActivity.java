package com.app.simplerxjavademo.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.app.simplerxjavademo.R;
import com.app.simplerxjavademo.models.TaskWithPriority;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ObserverObservableWithFromArrayOperatorActivity extends AppCompatActivity {

    private String TAG = this.getClass().getName();
    private Observable<TaskWithPriority> taskWithPriorityObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_observer_observable);

        //this will accept T[] array
        taskWithPriorityObservable = Observable
                .fromArray(generateTaskArray())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        taskWithPriorityObservable.subscribe(new Observer<TaskWithPriority>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: Called");
            }

            @Override
            public void onNext(@NonNull TaskWithPriority taskWithPriority) {
                Log.d(TAG, "onNext: " + Thread.currentThread().getName());
                Log.d(TAG, "onNext: " + taskWithPriority.getDescription());
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

    public TaskWithPriority[] generateTaskArray() {
        TaskWithPriority[] taskWithPriorities = new TaskWithPriority[5];
        taskWithPriorities[0] = (new TaskWithPriority("Wake up in morning", false, 0));
        taskWithPriorities[1] = (new TaskWithPriority("Brush your teeth", false, 2));
        taskWithPriorities[2] = (new TaskWithPriority("HAving Bath", false, 4));
        taskWithPriorities[3] = (new TaskWithPriority("Go to work", true, 1));
        taskWithPriorities[4] = (new TaskWithPriority("Rest after work", true, 3));

        return taskWithPriorities;
    }
}
