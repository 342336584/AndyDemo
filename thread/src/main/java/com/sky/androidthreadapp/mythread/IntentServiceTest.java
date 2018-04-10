package com.sky.androidthreadapp.mythread;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by yuetu-develop on 2017/10/17.
 */

public class IntentServiceTest extends IntentService {

    private LocalBroadcastManager mLocalBroadcastManager;
    private int[] mProgress = new int[2];
    // 必须创建该构造函数
    public IntentServiceTest() {
        super(IntentServiceTestActivity.TAG_MYINTENTSERVICE);
    }
    // 初始化mLocalBroadcastManager
    @Override
    public void onCreate() {
        super.onCreate();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        sendIntentServiceStatus("IntentServiceTest初始化 --> onCreate()");
    }
    // 实际处理任务
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        sendThreadStatus("IntentServiceTest开始处理 --> onHandleIntent()");
        // intent已经通过IntentService内部的Handler传递过来
        int taskId = intent.getIntExtra("taskId",0);
        if(taskId == 0){
            startThread(0);
        } else {
            startThread(1);
        }
    }
    //根据不同的taskId来标记不同的进度
    private void startThread(int taskId){
        try {
            Thread.sleep(1000);
            sendThreadStatus("线程启动 --> startThread()");
            boolean runnIng = true;
            mProgress[taskId] = 0;
            while (runnIng){
                mProgress[taskId] += 20;
                if(mProgress[taskId] >= 100){
                    runnIng = false;
                }
                sendThreadStatus("线程Running --> startThread()");
                Thread.sleep(3000);
            }
            sendThreadStatus("线程结束 --> startThread()");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sendIntentServiceStatus("IntentServiceTest销毁 --> onDestroy()");
    }

    /**
     * 通过mLocalBroadcastManager发送IntentService的状态信息
     * @param status
     */
    private void sendIntentServiceStatus(String status) {
        Intent intent = new Intent(IntentServiceTestActivity.ACTION_INTENTSERVICE_STATUS);
        intent.putExtra("status",status);
        mLocalBroadcastManager.sendBroadcast(intent);
    }
    /**
     * 通过mLocalBroadcastManager发送工作线程的状态信息
     * @param status
     */
    private void sendThreadStatus(String status) {
        Intent intent = new Intent(IntentServiceTestActivity.ACTION_THREAD_STATUS);
        intent.putExtra("status",status);
        intent.putExtra("progress",mProgress);
        mLocalBroadcastManager.sendBroadcast(intent);
    }

}
