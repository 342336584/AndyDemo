package com.sky.androidthreadapp.mythread;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sky.androidthreadapp.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yuetu-develop on 2017/10/12.
 */

public class MyAsyncTaskActivity extends Activity {

    private Button mDownButton;
    private Button mCancelButton;
    private TextView mStatuText;
    private MyAsyncTask downloadTask;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                default:
                    mStatuText.append("\n" + msg.obj);
                    break;
            }
        }
    };

    private Message getMessage(String msg){
        Message message = new Message();
        message.obj = msg;
        return message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myasynctask);
        mDownButton = (Button) findViewById(R.id.btn_download);
        mCancelButton = (Button) findViewById(R.id.btn_cancel);
        mStatuText = (TextView) findViewById(R.id.tv_statue);

        mDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] urls = {
                        "http://www.jianshu.com/p/d3984e7e204f",
                        "http://www.jianshu.com/p/8e3cfb87f35e",
                        "http://www.jianshu.com/p/4fbc2ea2e02b"
                };
                downloadTask = new MyAsyncTask();
                // 这里的url也可以分成一个一个参数传递
                downloadTask.execute(urls);
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null != downloadTask){
                    downloadTask.cancel(true);
                }
            }
        });
    }

    //第一个参数：Params参数类型:String类型
    //第二个参数：Progress执行进度类型:Object类型
    //第三个参数：Result参数类型:Long类型
    private class MyAsyncTask extends AsyncTask<String,Object,Long>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mStatuText.append("MyAsyncTask:onPreExecute,Thread name: "+ Thread.currentThread().getName());
            mDownButton.setText("下载中");
            mDownButton.setEnabled(false);
            mStatuText.setText("开始下载");
        }
        @Override
        protected Long doInBackground(String... strings) {
            mHandler.sendMessage(getMessage("\nMyAsyncTask:doInBackground,Thread name: "+ Thread.currentThread().getName()));
            long totalByte = 0;
            for (String url:strings){
                // downloadFile方法只是一个简单的下载字节流
                // 返回的Object[] result是二维数组，一个是下载的字节数，一个是标题
                Object[] result = downloadFile(url);
                int byteCount = (int) result[0];
                totalByte += byteCount;
                //传递进度
                publishProgress(result);
                // 异步任务调用取消则跳出for循环
                if(isCancelled()){
                    break;
                }
            }
            return totalByte;
        }
        // 如果单独下载某文件，解开下方1.2.注释将下载进度传递
        private Object[] downloadFile(String string){
            Object[] results = new Object[2];
            int byteCount = 0;
            String blogName = "";
            HttpURLConnection connection = null;
            try {
                URL url = new URL(string);
                // 创建链接以流的形式读取
                connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] bytes = new byte[1024];
                int length = -1;
                 // 1.int total = connection.getContentLength();
                while ((length = inputStream.read(bytes)) != -1) {
                    baos.write(bytes, 0, length);
                    byteCount += length;
                 // 2.publishProgress((int) ((byteCount / (float) total) * 100));
                }
                String respone = new String(baos.toByteArray(), "utf-8");
                int startIndex = respone.indexOf("<title>");
                if(startIndex > 0){
                    startIndex += 7;
                    int endIndex = respone.indexOf("</title>");
                    if(endIndex > startIndex){
                        //解析出博客中的标题
                        blogName = respone.substring(startIndex, endIndex);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(connection != null){
                    connection.disconnect();
                }
            }
            results[0] = byteCount;
            results[1] = blogName;
            return results;
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            mStatuText.append("\nMyAsyncTask:onProgressUpdate,Thread name: "+ Thread.currentThread().getName());
            super.onProgressUpdate(values);
            int byteCount = (int)values[0];
            String blogName = (String)values[1];
            String statuText = mStatuText.getText().toString();
            statuText += "\n<"+blogName+">下载完成共："+byteCount+"字节。";
            mStatuText.setText(statuText);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            mStatuText.append("\nMyAsyncTask:onPostExecute,Thread name: "+ Thread.currentThread().getName());
            super.onPostExecute(aLong);
            String text = mStatuText.getText().toString();
            text += "\n全部下载完成，总共下载了" + aLong + "个字节";
            mStatuText.setText(text);
            mDownButton.setEnabled(true);
            mDownButton.setText("开始下载");
        }

        //任务取消调用
        @Override
        protected void onCancelled() {
            mStatuText.append("\nMyAsyncTask:onCancelled,Thread name: \" "+ Thread.currentThread().getName());
            super.onCancelled();
            mStatuText.append("\n取消下载");
            mDownButton.setEnabled(true);
            mDownButton.setText("开始下载");
        }
    }
}
