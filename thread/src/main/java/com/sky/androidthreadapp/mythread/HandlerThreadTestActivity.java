package com.sky.androidthreadapp.mythread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.sky.androidthreadapp.R;

/**
 * Created by yuetu-develop on 2017/10/16.
 */

public class HandlerThreadTestActivity extends Activity {

    private TextView mInfoText;
    private HandlerThread mHandlerThread;
    private Handler mCheckMsgHandler;
    private boolean isUpdate;
    private static final int MSG_UPDATE_INFO = 0x110;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        //创建后台线程
        initBackThread();

        mInfoText = (TextView) findViewById(R.id.tv_info_count);
        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始更新
                isUpdate = true;
                mCheckMsgHandler.sendEmptyMessage(MSG_UPDATE_INFO);
            }
        });

        findViewById(R.id.btn_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //停止更新
                isUpdate = false;
                mCheckMsgHandler.removeMessages(MSG_UPDATE_INFO);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        isUpdate = false;
        mCheckMsgHandler.removeMessages(MSG_UPDATE_INFO);
    }

    private void initBackThread(){
        // 1.创建HandlerThread
        mHandlerThread = new HandlerThread("check-message-coming");
        // 2.启动HandlerThread线程
        mHandlerThread.start();
        // 3.创建Handler对象绑定该线程的Looper
        mCheckMsgHandler = new Handler(mHandlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg){
                //模拟耗时操作
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 主线程更新数据
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = "每隔2秒更新一下数据："+Math.random();
                        mInfoText.append("\n" + result);
                    }
                });

                if(isUpdate){ //1s后再次发送消息进行耗时操作
                    mCheckMsgHandler.sendEmptyMessageDelayed(MSG_UPDATE_INFO, 2000);
                }
            }
        };
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        //释放资源
        mHandlerThread.quit();
    }
}
