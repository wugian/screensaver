package com.wugian.rxstudy;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import rx.Observable;
import rx.Subscriber;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
    }

    public void helloClick(View view) {
//        hello();
        rxClick();
    }

    private void hello() {
        Observable<String> myObservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("Hello, world!");
                        sub.onCompleted();
                    }
                }
        );
        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onNext(String s) {
                Log.d("lovely", s);
            }

            @Override
            public void onCompleted() {
                Log.d("lovely", "mySubscriber.onCompleted");
            }

            @Override
            public void onError(Throwable e) {
            }
        };
        myObservable.subscribe(mySubscriber);
    }

    public void rxClick() {
        Observable<Double> myObser = Observable.create(new Observable.OnSubscribe<Double>() {
            @Override
            public void call(Subscriber<? super Double> subscriber) {
                double x = 1;
                double y = 1;
                if (x == 0) {
                    subscriber.onError(new Throwable("empty error"));
                } else {
                    double z = y / x;
                    subscriber.onNext(z);
                }
                subscriber.onCompleted();
            }
        });

        Subscriber<Double> mySubs = new Subscriber<Double>() {
            @Override
            public void onCompleted() {
                Log.d("lovely", "运行完成");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("lovely", e.toString());
            }

            @Override
            public void onNext(Double aDouble) {
                Log.d("lovely", "result is :" + aDouble);
            }
        };

        myObser.subscribe(mySubs);
    }
}
