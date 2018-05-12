package com.xingmei.administrator.xingmei.hanler;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.xingmei.administrator.xingmei.carousel.ViewPagerIndicator;

import java.lang.ref.WeakReference;

public  class ViewPagerHandler extends Handler {
    private WeakReference<ViewPagerIndicator> weakReference;
    private ViewPager viewPager = null;

    public ViewPagerHandler(ViewPagerIndicator viewPagerIndicator, ViewPager viewPager){
        weakReference = new WeakReference<>(viewPagerIndicator);
        this.viewPager = viewPager;
    }

    @Override
    public void handleMessage(Message msg) {
        ViewPagerIndicator indicator = weakReference.get();
        if (indicator == null){
            Log.e("ViewPagerHandler","indicator == null");
            return;
        }
        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
        sendEmptyMessageDelayed(0,3000);
    }
}
