package com.example.andy.andydemo.demo;

import android.os.Bundle;

import com.example.andy.andydemo.R;
import com.example.andy.andydemo.base.BaseActivity;

import javax.inject.Inject;

import timber.log.Timber;

public class Dagger2DemoActivity extends BaseActivity {

    @Inject
    Product mProduct;

    @Inject
    Factory mFacory;

    @Inject
    OkHttpClient mOkHttpClient;

    @Inject
    RetrofitManager mRetrofitManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger2_demo);

//        DaggerDagger2DemoActivityComponent.create().inject(this);
        DaggerDagger2DemoActivityComponent.builder()
                .dagger2DemoActivityModule(new Dagger2DemoActivityModule(100))
                .build().inject(this);

        Timber.d(mProduct.toString());
        Timber.d(mFacory.toString());
        Timber.d(mOkHttpClient.toString());
        Timber.d(mRetrofitManager.toString()
                + mRetrofitManager.getClient().toString()
                + mRetrofitManager.getClient().getCacheSize());
    }

}
