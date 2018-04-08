package com.example.andy.andydemo.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.andy.andydemo.R;
import com.example.andy.andydemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class H5InteractionActivity extends BaseActivity {

    private String mUrl = "http://140.143.28.32/songziweimaochengrui/index.html";

    @BindView(R.id.webview)
    WebView mWebView;

    @BindView(R.id.interactionh5_btn)
    View mInteractionh5Btn;

    private WebViewClient mWebViewClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_h5_interaction);
        super.onCreate(savedInstanceState);

        initWebView();
        initData();
    }

    private void initWebView() {
        mWebViewClient = new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
                super.onReceivedLoginRequest(view, realm, account, args);
            }

            @Override
            public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail) {
                return super.onRenderProcessGone(view, detail);
            }
        };

        mWebView.setWebViewClient(mWebViewClient);
        mWebView.addJavascriptInterface(new JsInteration(H5InteractionActivity.this), JsInteration.NAME_SPACE);
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);

        WebSettings webSettings = mWebView.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
//        webSettings.setSupportZoom(true);
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setUseWideViewPort(true);
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        webSettings.setLoadWithOverviewMode(true);
        String appCacheDir = getDir("cache", Context.MODE_PRIVATE).getPath();
        webSettings.setDatabasePath(appCacheDir);
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);// 设置缓冲大小，我设的是8M
        webSettings.setAppCachePath(appCacheDir);
        webSettings.setAllowFileAccess(true);
//		    webSettings.setAppCacheEnabled(true);
//		    webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setAppCacheEnabled(false);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        webSettings.setUserAgentString(BaseCallBack.getUserAgent());
    }

    private void initData() {
        mWebView.loadUrl(mUrl);
    }

    @OnClick(R.id.interactionh5_btn)
    public void clickInteractionH5(){
        mWebView.loadUrl("javascript:drawingApp.init()");
        Toasty.info(getApplication(), "drawingApp").show();
    }
}
