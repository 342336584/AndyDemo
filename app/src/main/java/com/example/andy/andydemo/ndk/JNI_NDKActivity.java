package com.example.andy.andydemo.ndk;

import android.content.Intent;
import android.os.Bundle;

import com.example.andy.andydemo.R;
import com.example.andy.andydemo.base.BaseActivity;

import butterknife.OnClick;

public class JNI_NDKActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_jni__ndk);
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.jni)
    public void clickJNI(){
        startActivity(new Intent(this, JNIActivity.class));
    }

    @OnClick(R.id.ndk)
    public void clickNDK(){
        startActivity(new Intent(this, NDKActivity.class));
    }
}
