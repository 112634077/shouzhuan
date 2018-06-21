package com.xingmei.administrator.xingmei.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;

import com.xingmei.administrator.xingmei.MainActivity;

import java.util.concurrent.ExecutorService;

/**
 * Created by Administrator on 2018/3/6.
 */

public class Utils {
    private static ExecutorService mExecutorSrvice;
    public static int width = 0;
    public static int height = 0;

    public Context getContext(){
        return MainActivity.context;
    }
    public View inflate(int id){
        View view = LayoutInflater.from(getContext()).inflate(id,null);
        return view;
    }

    public static void runOnUiThread(final Runnable mRunnable){
        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                new Handler(Looper.getMainLooper()).post(mRunnable);
                Looper.loop();
            }
        }.start();
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context,float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
