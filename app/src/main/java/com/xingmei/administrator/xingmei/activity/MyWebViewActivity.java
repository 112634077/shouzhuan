package com.xingmei.administrator.xingmei.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.xingmei.administrator.xingmei.R;

public class MyWebViewActivity extends Activity {
    private WebView mWebView;
    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mywebview);

        initView();
        init();
    }

    private void initView(){
        mWebView = findViewById(R.id.wv_webview);
        mProgressBar = findViewById(R.id.wvProgressBar);
        mWebView.loadUrl("www.baidu.com");
    }

    protected void init(){
        //设置不用系统的浏览器
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //开始加载了
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //结束加载
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                //获取网页标题
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                 if (newProgress == 100){
                     mProgressBar.setVisibility(View.GONE);
                 }else {
                     mProgressBar.setVisibility(View.VISIBLE);
                     mProgressBar.setProgress(newProgress);
                 }
                //加载条
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()){
         mWebView.goBack();
         return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }

        super.onDestroy();
    }
}
