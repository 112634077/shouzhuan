package com.xingmei.administrator.xingmei.application;

import android.app.Application;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;

public class APPAplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {

            }

            @Override
            public void onViewInitFinished(boolean b) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + b);
                System.out.println("APPAplication =="+b);
            }
        };
        QbSdk.initX5Environment(getApplicationContext(), cb);
        QbSdk.setDownloadWithoutWifi(true);
    }
}
