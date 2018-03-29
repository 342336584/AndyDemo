package com.example.andy.andydemo.net;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.andy.andydemo.base.BaseActivity;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by onething on 2018/3/29.
 */

public class NetBaseActivity extends BaseActivity {
    /**
     *
     */
    private CompositeSubscription sCompositeSubscription ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(sCompositeSubscription == null || sCompositeSubscription.isUnsubscribed()){
            sCompositeSubscription = new CompositeSubscription();
        }
    }

    /**
     * 添加Subscription
     * @param subscription
     */
    public void addSubscription(Subscription subscription){
        Timber.d("add subscription");
        sCompositeSubscription.add(subscription);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(sCompositeSubscription!=null){
            Timber.d("base activity unscbscribe");
            sCompositeSubscription.unsubscribe();
        }
    }
}
