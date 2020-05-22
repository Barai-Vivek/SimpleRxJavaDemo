package com.app.simplerxjavademo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.app.simplerxjavademo.R;
import com.app.simplerxjavademo.models.TaskWithPriority;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SimpleObserverObservableActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private Observable<TaskWithPriority> taskWithPriorityObservable;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_observer_observable);

        taskWithPriorityObservable = Observable.fromIterable(generateTaskList())
                .subscribeOn(Schedulers.io())
                .filter(new Predicate<TaskWithPriority>() {
                    @Override
                    public boolean test(TaskWithPriority taskWithPriority) throws Throwable {
                        Log.d(TAG, "test: " + Thread.currentThread().getName());
                        //if you write this code here it will in background thread observ thread name
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return taskWithPriority.isActive();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());

        taskWithPriorityObservable.subscribe(new Observer<TaskWithPriority>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: Called");
                //disposable will be used to release the observer once activity is destroyed
                disposable.add(d);
            }

            @Override
            public void onNext(@NonNull TaskWithPriority taskWithPriority) {
                Log.d(TAG, "onNext: " + Thread.currentThread().getName());
                Log.d(TAG, "onNext: " + taskWithPriority.getDescription());

                //if you write this code this will freez on main thread obeserve thread name
                /*try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //this will clear ther observer so if activity is destroyed you can release them
        disposable.clear();
    }
}
