package com.example.andy.andydemo.sys;

import android.os.Bundle;

import com.example.andy.andydemo.AndyApplication;
import com.example.andy.andydemo.R;
import com.example.andy.andydemo.base.BaseActivity;
import com.example.andy.andydemo.db.MySQLiteOpenHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import win.smartown.android.library.tableLayout.TableAdapter;
import win.smartown.android.library.tableLayout.TableLayout;

public class VersionActivity extends BaseActivity {

    private VersionDao mVersionDao;
    private List<Version> mVersions;

    @BindView(R.id.main_table)
    TableLayout mTableLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_version);
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.query)
    public void clickQuery(){
//        mRecycleView.setAdapter();


        if (mVersionDao == null)
            mVersionDao = MySQLiteOpenHelper.getmDaoSession().getVersionDao();

        mVersions = mVersionDao.queryRaw("");
        if (mVersions != null) {
            Toasty.info(AndyApplication.getApplication(), "" + mVersions.size()).show();
        }

        mTableLayout.setAdapter(new TableAdapter() {
            @Override
            public int getColumnCount() {
                return mVersions.size();
            }

            @Override
            public String[] getColumnContent(int position) {
                return mVersions.get(position).toArray();
            }
        });
    }

}
