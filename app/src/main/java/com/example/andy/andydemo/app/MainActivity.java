package com.example.andy.andydemo.app;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.andy.andydemo.R;
import com.example.andy.andydemo.base.BaseActivity;

import timber.log.Timber;

/**
 * Created by onething on 2018/3/28.
 */

public class MainActivity extends BaseActivity {

    private Manager m_oMgr = null;
    private int m_nResult = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello_world);

        if (m_oMgr == null) {
            m_oMgr = new Manager();
        }

        m_oMgr.doWork();
    }

    private class Manager {
        public Manager(){
        }

        public void doWork(){
            m_nResult = m_nResult ++;
            Timber.d(m_nResult + "");
        }
    }
}
