package com.example.andy.andydemo.net;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.example.andy.andydemo.R;
import com.example.andy.andydemo.net.student.Student;
import com.example.andy.andydemo.net.student.StudentAdapter;
import com.example.andy.andydemo.net.student.StudentLoader;

import java.util.List;

import butterknife.BindView;
import rx.Subscription;
import rx.functions.Action1;
import timber.log.Timber;

public class RequestPHPActivity extends NetBaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mListView;

    private StudentLoader mStudentLoader;

    private StudentAdapter mStudentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_request_php);
        super.onCreate(savedInstanceState);

        initData();
        initView();
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//设置布局管理器
        mListView.setLayoutManager(layoutManager);
//设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
//设置Adapter
        mListView.setAdapter(mStudentAdapter);
        //设置分隔线
        mListView.addItemDecoration( new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
//设置增加或删除条目的动画
        mListView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initData() {
        mStudentAdapter = new StudentAdapter(getBaseContext());
        mStudentLoader = new StudentLoader();
        Subscription subscription = mStudentLoader.getStudentsInfo().subscribe(new Action1<List<Student>>() {
                                                                                   @Override
                                                                                   public void call(List<Student> students) {
                                                                                       Timber.d(students.toString());
                                                                                       mStudentAdapter.setData(students);
                                                                                       mStudentAdapter.notifyDataSetChanged();
                                                                                   }
                                                                               }, new Action1<Throwable>() {
                                                                                   @Override
                                                                                   public void call(Throwable throwable) {
                                                                                       Timber.d(throwable);
                                                                                   }
                                                                               }
        );

        addSubscription(subscription);
    }

}
