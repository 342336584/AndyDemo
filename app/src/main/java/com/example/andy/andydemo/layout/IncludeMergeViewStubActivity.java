package com.example.andy.andydemo.layout;

import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;

import com.example.andy.andydemo.R;
import com.example.andy.andydemo.base.BaseActivity;

import es.dmoral.toasty.Toasty;

/**
 * Created by onething on 2018/3/16.
 */

public class IncludeMergeViewStubActivity extends BaseActivity {
    private Button btn_viewStub;
    private ViewStub viewStub;

    boolean isInflate = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_includemergeviewstub);

        btn_viewStub=(Button)findViewById(R.id.btn_viewStub);
        viewStub=(ViewStub) findViewById(R.id.stub);
        btn_viewStub.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!isInflate)
                    viewStub.inflate();
                else
                    Toasty.info(getApplication(), "已经加载了").show();
            }
        });

        viewStub.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub viewStub, View view) {
                isInflate = true;
            }
        });
    }
}
