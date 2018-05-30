package com.example.andy.andydemo.app;

import android.os.Bundle;

import com.example.andy.andydemo.R;
import com.example.andy.andydemo.base.BaseActivity;

import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxJavaActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_rx_java);
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.go)
    public void clickGo() {
        Observable mObservable;
        Observer mObserver;
//        mObservable = Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                subscriber.onNext("create1 onNext");
//                subscriber.onNext("create2 onNext");
//                subscriber.onCompleted();
//            }
//        });

        mObservable = Observable.just("create1 onNext", "create2 onNext");

        mObserver = new Observer<String>() {
            @Override
            public void onCompleted() {
                Toasty.info(getApplicationContext(), "Observer onCompleted").show();
            }

            @Override
            public void onError(Throwable e) {
                Toasty.info(getApplicationContext(), "Observer onError").show();
            }

            @Override
            public void onNext(String s) {
                Toasty.info(getApplicationContext(), "Observer onNext " + s).show();
            }
        };

        mObservable.subscribe(mObserver);
    }


    @OnClick(R.id.go2)
    public void clickGo2() {
        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.newThread())
                .map(new Func1<Integer, String>() {

                    @Override
                    public String call(Integer integer) {
                        return integer + "";
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Toasty.info(getApplicationContext(), "Observer onNext " + s).show();
                    }
                });
    }

}
