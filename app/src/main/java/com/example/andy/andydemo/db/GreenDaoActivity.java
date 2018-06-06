package com.example.andy.andydemo.db;

import android.os.Bundle;

import com.example.andy.andydemo.AndyApplication;
import com.example.andy.andydemo.R;
import com.example.andy.andydemo.base.BaseActivity;

import java.util.List;

import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class GreenDaoActivity extends BaseActivity {

    private UserDao mUserDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_green_dao);
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.insert)
    public void clickInsert(){
        if (mUserDao == null){
            mUserDao = MySQLiteOpenHelper.getmDaoSession().getUserDao();
        }

        mUserDao.insert(new User(null, "jack", 10, "f", "111111111"));
    }

    @OnClick(R.id.query)
    public void clickQuery(){
        if (mUserDao == null){
            mUserDao = MySQLiteOpenHelper.getmDaoSession().getUserDao();
        }

        List<User> users = mUserDao.queryRaw("");
        if (users == null)
            return;

        for ( User user : users){
            Toasty.info(AndyApplication.getApplication(), user.getUsername()).show();
        }
    }

}
