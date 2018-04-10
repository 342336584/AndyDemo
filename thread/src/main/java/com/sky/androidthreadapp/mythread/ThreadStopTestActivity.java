package com.sky.androidthreadapp.mythread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.sky.androidthreadapp.R;

/**
 * Created by Administrator on 2017/10/14.
 */

public class ThreadStopTestActivity extends Activity {

    private MyThread myThread = new MyThread();
    private SleepThread sleepThread = new SleepThread();

    private TextView mStateTextView;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                default:
                    mStateTextView.append("\n" + msg.obj);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mHandler.post(runnable); // handler运行runnable
                goThread();
            }
        });
        findViewById(R.id.btn_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mHandler.post(runnable); // handler运行runnable
                stopThread();
            }
        });

        mStateTextView = findViewById(R.id.tv_info_count);
    }

    private Message getMessage(String msg){
        Message message = new Message();
        message.obj = msg;
        return message;
    }

    public void goThread(){
//        if(null == myThread){
//            myThread = new MyThread();
//        }
//        myThread.start();
        if(null == sleepThread){
            sleepThread = new SleepThread();
        }
        sleepThread.start();
    }
    // 定义开始和结束线程的方法，与按钮绑定
    private void stopThread() {
        if(null != myThread && myThread.isAlive()){
            myThread.interrupt();
            myThread = null;
        }
        if(null != sleepThread && sleepThread.isAlive()){
            sleepThread.interrupt();
            sleepThread = null;
        }
    }

    public class MyThread extends Thread{
        @Override
        public void run() {
            super.run();
            int i = 0;
            // 判断状态，如果被打断则跳出并将线程置空
            while (!isInterrupted()){
                i++;
                mHandler.sendMessage(getMessage(Thread.currentThread().getName()+":Running()_Count:"+i));
            }
        }
    }

    public class SleepThread extends Thread{
        @Override
        public void run() {
            super.run();
            int i = 0;
            while(!isInterrupted()){ // 判断线程是否被打断
                try {
                    i++;
                    mHandler.sendMessage(getMessage(Thread.currentThread().getName()+":Running()_Count:"+i));
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    mHandler.sendMessage(getMessage(Thread.currentThread().getName()+"异常抛出，停止线程"));
                    break;// 抛出异常跳出循环
                }
            }

        }
    }





    //    public volatile boolean stop = false;
//    @Override
//    public void run() {
//        super.run();
//        while (!stop){
//            // thread runing
//        }
//    }
}
