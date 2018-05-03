package com.example.andy.andydemo.ndk;

import android.os.Bundle;
import android.widget.TextView;

import com.example.andy.andydemo.R;
import com.example.andy.andydemo.base.BaseActivity;

import butterknife.BindView;

public class NDKActivity extends BaseActivity {

    static {
        System.loadLibrary("ndkdemo");
    }

    @BindView(R.id.ndk_tv)
    public TextView mNdkTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ndk);
        super.onCreate(savedInstanceState);

        NdkUtils ndkUtils = new NdkUtils();
        mNdkTv.setText(ndkUtils.getStringFromC());
    }
}
