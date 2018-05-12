package com.xingmei.administrator.xingmei.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xingmei.administrator.xingmei.R;
import com.xingmei.administrator.xingmei.utils.Utils;

/**
 * Created by Administrator on 2018/3/6.
 */

public class Image_ItemAdapter extends BaseAdapter {

    @Override
    public int getCount() {
        return 50;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = new Utils().inflate(R.layout.item_image);
        return convertView;
    }
}
