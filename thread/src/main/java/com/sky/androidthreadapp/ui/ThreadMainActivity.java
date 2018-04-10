package com.sky.androidthreadapp.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sky.androidthreadapp.R;
import com.sky.androidthreadapp.mythread.HandlerThreadTestActivity;
import com.sky.androidthreadapp.mythread.IntentServiceTestActivity;
import com.sky.androidthreadapp.mythread.MyAsyncTaskActivity;
import com.sky.androidthreadapp.mythread.ThreadPoolTestActivity;
import com.sky.androidthreadapp.mythread.ThreadStopTestActivity;
import com.sky.androidthreadapp.mythread.ThreadTestActivity;

import java.util.ArrayList;
import java.util.List;

public class ThreadMainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> items = fillList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, items);
        setListAdapter(adapter);
    }
    private List<String> fillList() {
        List<String> items = new ArrayList<String>();
        items.add("HandlerThread使用");
        items.add("IntentService使用");
        items.add("AsyncTask使用");
        items.add("ThreadPool(线程池)使用");
        items.add("ThreadStop终止线程");
        items.add("ThreadTest普通线程使用");
        return items;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position){
            case 0:
                startActivity(HandlerThreadTestActivity.class);
                break;
            case 1:
                startActivity(IntentServiceTestActivity.class);
                break;
            case 2:
                startActivity(MyAsyncTaskActivity.class);
                break;
            case 3:
                startActivity(ThreadPoolTestActivity.class);
                break;
            case 4:
                startActivity(ThreadStopTestActivity.class);
                break;
            case 5:
                startActivity(ThreadTestActivity.class);
                break;
        }

    }

    private void startActivity(Class<?> cs){
        Intent intent = new Intent(this,cs);
        this.startActivity(intent);
    }

}
