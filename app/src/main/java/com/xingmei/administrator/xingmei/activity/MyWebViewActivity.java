package com.xingmei.administrator.xingmei.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.xingmei.administrator.xingmei.R;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;

public class MyWebViewActivity extends Activity {
    private WebView x5WebView;
    private String url = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.activity_web);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        url = getIntent().getStringExtra("url");

        x5WebView = findViewById(R.id.x5_webview);

        initView();

    }

    private void initView(){
        x5WebView.setLayerType(WebView.LAYER_TYPE_NONE,null);
        x5WebView.setDrawingCacheEnabled(true);

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

        x5WebView.loadUrl(url);
    }

}
