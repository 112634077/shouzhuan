package com.xingmei.administrator.xingmei.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tencent.smtt.utils.TbsLog;
import com.xingmei.administrator.xingmei.R;
import com.xingmei.administrator.xingmei.utils.X5WebView;


public class MyWebViewActivity extends Activity{
    /**
     * 作为一个浏览器的示例展示出来，采用android+web的模式
     */
    private X5WebView mWebView;
    private static final String TAG = "SdkDemo";
    private boolean mNeedTestPage = false;
    private String url;

    private ProgressBar mPageLoadingProgressBar = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);

		setContentView(R.layout.activity_web);

		url = getIntent().getStringExtra("url");
		mTestHandler.sendEmptyMessageDelayed(MSG_INIT_UI, 10);

	}

	private void initProgressBar() {
		mPageLoadingProgressBar = (ProgressBar) findViewById(R.id.progressBar1);// new
																				// ProgressBar(getApplicationContext(),
																				// null,
																				// android.R.attr.progressBarStyleHorizontal);
		mPageLoadingProgressBar.setMax(100);
		mPageLoadingProgressBar.setProgressDrawable(this.getResources()
				.getDrawable(R.drawable.pg));
	}

	@SuppressLint("SetJavaScriptEnabled")
    private void init() {

		mWebView =findViewById(R.id.x5_webview);

		initProgressBar();

		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return false;
			}

			@Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mTestHandler.sendEmptyMessageDelayed(MSG_OPEN_TEST_URL, 5000);// 5s?
            }
		});

		mWebView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView webView, int i) {
//				super.onProgressChanged(webView, i);
//				mPageLoadingProgressBar.setProgress(i);
				if (i == 100){
					mPageLoadingProgressBar.setVisibility(View.GONE);
				}else {
					if (mPageLoadingProgressBar.getVisibility() == View.GONE)
						mPageLoadingProgressBar.setVisibility(View.VISIBLE);
					mPageLoadingProgressBar.setProgress(i);
				}
			}

			@Override
			public boolean onJsConfirm(WebView arg0, String arg1, String arg2,
					JsResult arg3) {
				return super.onJsConfirm(arg0, arg1, arg2, arg3);
			}

			@Override
			public boolean onJsAlert(WebView arg0, String arg1, String arg2,
					JsResult arg3) {
				/**
				 * 这里写入你自定义的window alert
				 */
				return super.onJsAlert(null, arg1, arg2, arg3);
			}
		});

		mWebView.setDownloadListener(new DownloadListener() {

			@Override
			public void onDownloadStart(String arg0, String arg1, String arg2,
					String arg3, long arg4) {
				TbsLog.d(TAG, "url: " + arg0);
				new AlertDialog.Builder(MyWebViewActivity.this)
						.setTitle("是否允许下载？")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										Toast.makeText(
                                                MyWebViewActivity.this,
												"消息: 确定下载...",
												Toast.LENGTH_LONG).show();
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										Toast.makeText(
                                                MyWebViewActivity.this,
												"消息: 取消下载...",
												Toast.LENGTH_SHORT).show();
									}
								})
						.setOnCancelListener(
								new DialogInterface.OnCancelListener() {

									@Override
									public void onCancel(DialogInterface dialog) {
										// TODO Auto-generated method stub
										Toast.makeText(
                                                MyWebViewActivity.this,
												"fake message: refuse download...",
												Toast.LENGTH_SHORT).show();
									}
								}).show();
			}
		});

		WebSettings webSetting = mWebView.getSettings();
		webSetting.setAllowFileAccess(true);
		webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		webSetting.setSupportZoom(true);
		webSetting.setBuiltInZoomControls(true);
		webSetting.setUseWideViewPort(true);
		webSetting.setSupportMultipleWindows(false);
		webSetting.setAppCacheEnabled(true);
		webSetting.setDomStorageEnabled(true);
		webSetting.setJavaScriptEnabled(true);
		webSetting.setGeolocationEnabled(true);
		webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
		webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
		webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
		webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
				.getPath());
		webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
		long time = System.currentTimeMillis();

		if (!TextUtils.isEmpty(url)) {
			mWebView.loadUrl("file:///android_asset/fullvideo.html");
			enablePageVideoFunc();
		}
		else Toast.makeText(MyWebViewActivity.this,"地址不存在",Toast.LENGTH_LONG).show();

		TbsLog.d("time-cost", "cost time: "
				+ (System.currentTimeMillis() - time));
		CookieSyncManager.createInstance(this);
		CookieSyncManager.getInstance().sync();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mWebView != null && mWebView.canGoBack()) {
				mWebView.goBack();
				return true;
			} else
				return super.onKeyDown(keyCode, event);
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		if (intent == null || mWebView == null || intent.getData() == null)
			return;
		mWebView.loadUrl(intent.getData().toString());
	}

	@Override
	protected void onDestroy() {
		if (mTestHandler != null)
			mTestHandler.removeCallbacksAndMessages(null);
		if (mWebView != null)
			mWebView.destroy();
		super.onDestroy();
	}

	public static final int MSG_OPEN_TEST_URL = 0;
	public static final int MSG_INIT_UI = 1;
	private final int mUrlStartNum = 0;
	private int mCurrentUrl = mUrlStartNum;
	private Handler mTestHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_OPEN_TEST_URL:
				if (!mNeedTestPage) {
					return;
				}

				String testUrl = "file:///sdcard/outputHtml/html/"
						+ Integer.toString(mCurrentUrl) + ".html";
				if (mWebView != null) {
					mWebView.loadUrl(testUrl);
				}

				mCurrentUrl++;
				break;
			case MSG_INIT_UI:
				init();
				break;
			}
			super.handleMessage(msg);
		}
	};

	private void enablePageVideoFunc() {
		if (mWebView.getX5WebViewExtension() != null) {
			Toast.makeText(this, "页面内全屏播放模式", Toast.LENGTH_LONG).show();
			Bundle data = new Bundle();

			data.putBoolean("standardFullScreen", false);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，

			data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，

			data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

			mWebView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
					data);
		}
	}

}