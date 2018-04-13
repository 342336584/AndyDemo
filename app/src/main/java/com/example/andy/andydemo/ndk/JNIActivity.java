package com.example.andy.andydemo.ndk;

import android.os.Bundle;
import android.widget.TextView;

import com.example.andy.andydemo.R;
import com.example.andy.andydemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.andy.andydemo.ndk.myJNI.plus;

public class JNIActivity extends BaseActivity {

    static {
        System.loadLibrary("JniTest");
    }

    @BindView(R.id.value)
    TextView mValueTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_jni);
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.plus)
    public void clickPlus(){
        int x = myJNI.plus(4, 3);
        mValueTv.setText("4+3="+x);
        mValueTv.append(myJNI.sayHello());
    }
}
