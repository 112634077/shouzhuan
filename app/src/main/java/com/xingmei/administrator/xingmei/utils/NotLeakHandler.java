package com.xingmei.administrator.xingmei.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

public class NotLeakHandler extends Handler {
    private ThreadPool threadPool;
    private WeakReference<Fragment> mWeakReference;

    public NotLeakHandler(Fragment reference) {
        mWeakReference = new WeakReference<>(reference);
    }

    @Override
    public void handleMessage(Message msg) {
        Fragment reference = mWeakReference.get();
        if (reference == null) { // the referenced object has been cleared
            return;
        }

        if (msg.what == 0){
            getThreadPool().onExecute();
        }else if (msg.what == 1){
          getThreadPool().onInBackground("");
        }else if (msg.what == 2){
            getThreadPool().onCliable();
        }
    }

    public void setThreadPool(ThreadPool threadPool){
        this.threadPool = threadPool;
    }

    private ThreadPool getThreadPool(){
        return threadPool;
    }
}
