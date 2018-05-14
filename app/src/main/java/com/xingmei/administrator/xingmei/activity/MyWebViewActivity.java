package com.xingmei.administrator.xingmei.activity;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.xingmei.administrator.xingmei.R;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;

public class MyWebViewActivity extends Activity{
    private WebView x5WebView;
    private ProgressBar progressBar;
    private String url = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.activity_web);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        url = getIntent().getStringExtra("url");

        initId();
        initWeb();
        initSetting();
    }

    private void initId(){
        x5WebView = findViewById(R.id.x5_webview);
        progressBar = findViewById(R.id.progressBar1);

    }

    private void initWeb(){
        x5WebView.setLayerType(WebView.LAYER_TYPE_NONE,null);
        x5WebView.setDrawingCacheEnabled(true);

        x5WebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return true;
            }
        });

        x5WebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                if (i == 100){
                    progressBar.setVisibility(View.GONE);
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(i);
                }
            }
        });
    }

    private void initSetting(){
        WebSettings webSettings = x5WebView.getSettings();

        webSettings.setAllowFileAccess(true);
        webSettings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportMultipleWindows(false);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);

        webSettings.setGeolocationEnabled(true);
        webSettings.setAppCacheMaxSize(Long.MAX_VALUE);
        webSettings.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSettings.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());

        x5WebView.loadUrl("https://m.baidu.com/?from=1086k");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            if(x5WebView.canGoBack()) {//当webview不是处于第一页面时，返回上一个页面
                x5WebView.goBack();
                return true;
            }
            else {//当webview处于第一页面时,直接退出程序
                System.exit(0);
            }


        }

        return super.onKeyDown(keyCode, event);
    }

}
