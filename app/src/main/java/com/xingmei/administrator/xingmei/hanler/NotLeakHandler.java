package com.xingmei.administrator.xingmei.hanler;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

public class NotLeakHandler extends Handler {
    private WeakReference<Fragment> mWeakReference;
    private int code = 0;

    public NotLeakHandler(Fragment reference) {
        mWeakReference = new WeakReference<>(reference);
    }

    @Override
    public void handleMessage(Message msg) {
        Fragment reference = mWeakReference.get();
        if (reference == null) { // the referenced object has been cleared
            return;
        }
        if (msg.what == 0) {
//            getThreadPool().onExecute();
        } else if (msg.what == 1) {
            setCode(200);
//            getThreadPool().onInBackground("");
        } else if (msg.what == 2) {
            setCode(201);
//            getThreadPool().onCliable();
        }
    }

    private void setCode(int code){
        this.code = code;
    }

    public int getCode(){
        return code;
    }

}
