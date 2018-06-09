package com.xingmei.administrator.xingmei.utils;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

public  class NotLeakHandler extends Handler {
    private WeakReference<Fragment> mWeakReference;

    public NotLeakHandler(Fragment reference) {
        mWeakReference = new WeakReference<Fragment>(reference);
    }

    @Override
    public void handleMessage(Message msg) {
        Fragment reference = (Fragment) mWeakReference.get();
        if (reference == null) { // the referenced object has been cleared
            return;
        }
    }
}
