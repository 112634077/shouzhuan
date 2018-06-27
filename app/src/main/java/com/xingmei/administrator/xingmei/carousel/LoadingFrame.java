package com.xingmei.administrator.xingmei.carousel;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xingmei.administrator.xingmei.R;
import com.xingmei.administrator.xingmei.utils.Utils;

public abstract class LoadingFrame extends FrameLayout {
    private Context mContext;

    private static final int LOADING = 1;//加载中
    private static final int LOADERROR = 2;//加载失败
    private static final int NETERROR = 3;//网络错误
    private static final int LOADED = 4;//加载完成
    private static final int NODATA = 5;//无数据可显示

    private ImageView loadingView;
    private LinearLayout mlinearLayoutLoading;
    private ImageView noDataView;
    private LinearLayout mlinearLayoutNoData;
    private LinearLayout mlinearLayoutLoadError;
    private ImageView netErrorView;
    private LinearLayout mlinearLayoutNetError;

    private View successView;

    private int currentState = LOADING;
    private FrameLayout.LayoutParams params;

    public LoadingFrame(Context context) {
        super(context);
        this.mContext = context;
        createView();
    }

    public LoadingFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        createView();
    }

    public LoadingFrame(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        createView();
    }


    private void initData() {
        currentState = LOADING;
//        mLoadingTask = new LoadingTask();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int code = onLoad();
                if (code == 200) {
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            currentState = LOADED;
                            successView = onSuccessView();
                            addView(successView, params);
                        }
                    });
                } else if (code == 201) {
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            currentState = NODATA;
                        }
                    });

                }else if (code == 404) {
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            currentState = LOADERROR;
                        }
                    });

                } else if (code == -1) {
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            currentState = NETERROR;
                        }
                    });
                }
                showSafePage();
            }
        }).start();
    }

    private void refreshView() {
        mlinearLayoutLoading.setVisibility(currentState == LOADING ? View.VISIBLE : View.GONE);
        mlinearLayoutNoData.setVisibility(currentState == NODATA ? View.VISIBLE : View.GONE);
        mlinearLayoutNetError.setVisibility(currentState == NETERROR ? View.VISIBLE : View.GONE);
        mlinearLayoutLoadError.setVisibility(currentState == LOADERROR ? View.VISIBLE : View.GONE);
        if (successView != null) {
            successView.setVisibility(currentState == LOADED ? View.VISIBLE : View.GONE);
        }
    }

    public void show() {
        initData();
    }

    private void createView() {
        params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;

        createLoadingView();

        createNoDataView();

        createNetErrorView();

        createLoadedErrorView();

        addView(mlinearLayoutLoading, params);
        addView(mlinearLayoutNoData, params);
        addView(mlinearLayoutNetError, params);
        addView(mlinearLayoutLoadError, params);
        showSafePage();
    }

    private void showSafePage(){
        Utils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                refreshView();
            }
        });
    }

    private void createNetErrorView() {
        mlinearLayoutNetError  = new LinearLayout(mContext);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayoutParams.gravity = Gravity.CENTER;
        mlinearLayoutNetError.setOrientation(LinearLayout.VERTICAL);

        netErrorView = new ImageView(mContext);
        netErrorView.setImageResource(R.mipmap.no_error);

        mlinearLayoutNetError.addView(netErrorView,linearLayoutParams);
        TextView textView = new TextView(mContext);
        textView.setText("网络错误，检查您的网络或点击重试");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentState = LOADING;
                show();
                showSafePage();
            }
        });

        mlinearLayoutNetError.addView(textView,linearLayoutParams);

        mlinearLayoutNetError.setVisibility(View.GONE);
    }

    private void createNoDataView() {
        mlinearLayoutNoData  = new LinearLayout(mContext);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayoutParams.gravity = Gravity.CENTER;
        mlinearLayoutNoData.setOrientation(LinearLayout.VERTICAL);

        noDataView = new ImageView(mContext);
        noDataView.setImageResource(R.mipmap.no_error);
        mlinearLayoutNoData.addView(noDataView,linearLayoutParams);
        TextView textView = new TextView(mContext);
        textView.setText("没有数据可供显示！");
        mlinearLayoutNoData.addView(textView,linearLayoutParams);
        mlinearLayoutNoData.setVisibility(View.GONE);
    }
    private void createLoadedErrorView() {
        mlinearLayoutLoadError  = new LinearLayout(mContext);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayoutParams.gravity = Gravity.CENTER;
        mlinearLayoutLoadError.setOrientation(LinearLayout.VERTICAL);

        noDataView = new ImageView(mContext);
        noDataView.setImageResource(R.drawable.loading);
        mlinearLayoutLoadError.addView(noDataView,linearLayoutParams);
        TextView textView = new TextView(mContext);
        textView.setText("加载失败！点击重试");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentState = LOADING;
                show();
                showSafePage();
            }
        });
        mlinearLayoutLoadError.addView(textView,linearLayoutParams);
        mlinearLayoutLoadError.setVisibility(View.GONE);
    }

    private void createLoadingView() {
        mlinearLayoutLoading  = new LinearLayout(mContext);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayoutParams.gravity = Gravity.CENTER;
        mlinearLayoutLoading.setOrientation(LinearLayout.VERTICAL);

        loadingView = new ImageView(mContext);
        loadingView.setImageResource(R.drawable.loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) loadingView.getDrawable();
        animationDrawable.start();
        mlinearLayoutLoading.addView(loadingView,linearLayoutParams);
        TextView textView = new TextView(mContext);
        textView.setText("正在加载中");
        mlinearLayoutLoading.addView(textView,linearLayoutParams);
        mlinearLayoutLoading.setVisibility(View.GONE);
    }

    public abstract View onSuccessView();

    public abstract int onLoad();

}
