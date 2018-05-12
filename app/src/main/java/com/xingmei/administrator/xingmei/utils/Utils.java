package com.xingmei.administrator.xingmei.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xingmei.administrator.xingmei.MainActivity;

/**
 * Created by Administrator on 2018/3/6.
 */

public class Utils {
    public Context getContext(){
        return MainActivity.context;
    }
    public View inflate(int id){
        View view = LayoutInflater.from(getContext()).inflate(id,null);
        return view;
    }
}
