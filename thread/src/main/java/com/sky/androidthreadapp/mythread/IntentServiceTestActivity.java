package com.sky.androidthreadapp.mythread;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sky.androidthreadapp.R;

/**
 * Created by yuetu-develop on 2017/10/17.
 */

public class IntentServiceTestActivity extends Activity implements View.OnClickListener {

    public static final String ACTION_INTENTSERVICE_STATUS = "IntentServiceStatus"; //IntentService状态
    public static final String ACTION_THREAD_STATUS = "IntentServiceThreadStatus";//IntentService中子线程状态
    public static final String TAG_MYINTENTSERVICE = "myIntentService";//自定义IntentService名称
    private TextView mInfoText;
    private TextView mPb1;
    private TextView mPb2;
    private Button mButtonTask1;
    private Button mButtonTask2;
    private ProgressBar mProgressBar1;
    private ProgressBar mProgressBar2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intentserivcetest);
        // 将MyBroadcastReceiver注册到LocalBroadcastManager
        LocalBroadcastManager.getInstance(this).registerReceiver(new MyBroadcastReceiver(), new IntentFilter(ACTION_INTENTSERVICE_STATUS));
        LocalBroadcastManager.getInstance(this).registerReceiver(new MyBroadcastReceiver(), new IntentFilter(ACTION_THREAD_STATUS));
        initView();
    }

    private void initView() {
        mInfoText = (TextView) findViewById(R.id.tv_info_count);
        mPb1 = (TextView) findViewById(R.id.tv_pb1);
        mPb2 = (TextView) findViewById(R.id.tv_pb2);
        mButtonTask1 = (Button) findViewById(R.id.btn_task1);
        mButtonTask2 = (Button) findViewById(R.id.btn_task2);
        mProgressBar1 = (ProgressBar) findViewById(R.id.pb_task1);
        mProgressBar2 = (ProgressBar) findViewById(R.id.pb_task2);

        mButtonTask1.setOnClickListener(this);
        mButtonTask2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        try {
            if (i == R.id.btn_task1) {
                Intent intentTask1 = new Intent(IntentServiceTestActivity.this, IntentServiceTest.class);
                intentTask1.putExtra("taskId", 0);
                startService(intentTask1);

            } else if (i == R.id.btn_task2) {
                Intent intentTask2 = new Intent(IntentServiceTestActivity.this, IntentServiceTest.class);
                intentTask2.putExtra("taskId", 1);
                startService(intentTask2);

            }
        } catch (java.lang.Exception e) {
            mInfoText.append("\n " + e.getMessage());
        }
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case ACTION_INTENTSERVICE_STATUS:
                    mInfoText.append("\n" + intent.getStringExtra("status"));
                    break;
                case ACTION_THREAD_STATUS:
                    int[] progress = intent.getIntArrayExtra("progress");
                    mProgressBar1.setProgress(progress[0]);
                    mProgressBar2.setProgress(progress[1]);
                    mPb1.setText(progress[0] + "%");
                    mPb2.setText(progress[1] + "%");
                    mInfoText.append("\n" + intent.getStringExtra("status"));
                    break;
            }
        }
    }

}
