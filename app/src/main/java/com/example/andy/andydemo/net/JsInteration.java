package com.example.andy.andydemo.net;

import android.app.Activity;
import android.webkit.JavascriptInterface;

import es.dmoral.toasty.Toasty;

/**
 * Created by onething on 2018/4/4.
 * js调用Android本地Java方法
 在Android4.2以上可以直接使用@JavascriptInterface注解来声明
 */

public class JsInteration {

    public static final String NAME_SPACE = "android";// Android类与JS代码的映射对象名称

    private Activity mBaseActivity;

    public JsInteration(Activity mBaseActivity) {
        this.mBaseActivity = mBaseActivity;
    }

    @JavascriptInterface
    public String hello() {
        Toasty.info(mBaseActivity, "hello world").show();
        return "hello world";
    }


}
