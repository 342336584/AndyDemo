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
 * Created by yuetu-develop on 2017/10/13.
 */

public class ThreadTestActivity extends Activity {

    public static final int NUM_1 = 1;
    public static final int NUM_2 = 2;
    public static final int NUM_3 = 3;


    private TextView mStateTextView;
    private int count = 0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case NUM_1:
                    mStateTextView.append("\n" + msg.obj);
                    break;
                case NUM_2:
                    mStateTextView.append("\n" + msg.obj);
                    break;
                case NUM_3:
                    mStateTextView.append("\n" + msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mHandler.sendMessage(getMessage(NUM_1, Thread.currentThread().getName()+":"+count));
            count ++;
            mHandler.postDelayed(runnable,3000); // 执行后每1000毫秒再次加执行
        }
    };

    private Message getMessage(int what, String msg){
        Message message = new Message();
        message.what = what;
        message.obj = msg;
        return message;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.post(runnable); // handler运行runnable
                goThread();
            }
        });

        mStateTextView = findViewById(R.id.tv_info_count);
    }

    @Override
    protected void onDestroy() {
        // 销毁线程，释放资源
        mHandler.removeCallbacks(runnable);
        super.onDestroy();
    }

    public void goThread(){
        new MyThread().start();
        new Thread(new MyRunnable()).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                mHandler.sendMessage(getMessage(NUM_1, Thread.currentThread().getName()+":Running()"));
            }
        }).start();
    }

    public class MyThread extends Thread{

        @Override
        public void run() {
            super.run();
            // do something
            mHandler.sendMessage(getMessage(NUM_1, Thread.currentThread().getName()+":Running()"));
        }
    }

    public class MyRunnable implements Runnable{

        @Override
        public void run() {
            // do something
            mHandler.sendMessage(getMessage(NUM_1, Thread.currentThread().getName()+":Running()"));
        }
    }


}
