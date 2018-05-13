package com.xingmei.administrator.xingmei.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tencent.smtt.sdk.WebSettings;
import com.xingmei.administrator.xingmei.R;
import com.xingmei.administrator.xingmei.utils.X5WebView;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;

public class MyWebViewActivity extends Activity {
    private X5WebView x5WebView;
    private String url = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        url = getIntent().getStringExtra("url");
        System.out.println("mywebview"+url);
        x5WebView = findViewById(R.id.x5_webview);

        initView();

    }

    private void initView(){
        WebSettings webSettings = x5WebView.getSettings();

        webSettings.setAllowFileAccess(true);
        webSettings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportMultipleWindows(false);
        // webSetting.setLoadWithOverviewMode(true);
        webSettings.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setAppCacheMaxSize(Long.MAX_VALUE);
        webSettings.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSettings.setDatabasePath(this.getDir("databases", 0).getPath());
        webSettings.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSettings.setPluginState(WebSettings.PluginState.ON_DEMAND);

        x5WebView.loadUrl(url);
    }

}
