package com.example.andy.andydemo.debug;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.andy.andydemo.R;
import com.example.andy.andydemo.base.BaseActivity;

import timber.log.Timber;

/**
 * Created by onething on 2018/3/13.
 */

public class DebugDrawerActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello_debugdrawer);
    }
}
