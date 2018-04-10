package com.sky.androidthreadapp.mythread;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.sky.androidthreadapp.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by yuetu-develop on 2017/10/18.
 */

public class ThreadPoolTestActivity extends Activity {

    private volatile int count = 0;
    private TextView mStatueText;
    private ScheduledExecutorService scheduledThreadPool;
    private boolean isRunning = false; //标识scheduledThreadPool是否在执行
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        mStatueText = (TextView) findViewById(R.id.tv_info_count);
        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = 0;
                mStatueText.append("\n线程开始执行，次数："+ count);
                startThreadPool();
            }
        });
        findViewById(R.id.btn_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != scheduledThreadPool && isRunning){
                    scheduledThreadPool.shutdown();
                    mStatueText.append("\nscheduledThreadPool线程停止，当前次数："+ count);
                    isRunning = false;
                }
            }
        });
    }

    private void startThreadPool(){
        //创建一个Runnable对象
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                    count++;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mStatueText.append("\n线程执行完毕，次数："+ count);
                        }
                    });
            }
        };

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
        fixedThreadPool.execute(runnable);

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(runnable);

        if(!isRunning){
            scheduledThreadPool = Executors.newScheduledThreadPool(4);
            // 1000ms后执行runnable
            scheduledThreadPool.schedule(runnable,3000,TimeUnit.MILLISECONDS);
            // 1000ms后，每3000ms执行一次runnable
            scheduledThreadPool.scheduleAtFixedRate(runnable,1000,3000,TimeUnit.MILLISECONDS);
            isRunning = true;
        }

        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        singleThreadExecutor.execute(runnable);

    }

}
