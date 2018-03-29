package com.example.andy.andydemo.layout;

import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;

import com.example.andy.andydemo.R;
import com.example.andy.andydemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * Created by onething on 2018/3/16.
 */

public class IncludeMergeViewStubActivity extends BaseActivity {

    @BindView(R.id.btn_viewStub)
    Button btn_viewStub;

    @BindView(R.id.stub)
    ViewStub viewStub;

    boolean isInflate = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_includemergeviewstub);
        super.onCreate(savedInstanceState);

        viewStub.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub viewStub, View view) {
                isInflate = true;
            }
        });
    }

    @OnClick(R.id.btn_viewStub)
    public void onClick(){
        if (!isInflate)
            viewStub.inflate();
        else
            Toasty.info(getApplication(), "已经加载了").show();
    }

}
