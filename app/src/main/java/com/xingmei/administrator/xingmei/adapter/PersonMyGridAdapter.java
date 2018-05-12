package com.xingmei.administrator.xingmei.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xingmei.administrator.xingmei.R;
import com.xingmei.administrator.xingmei.utils.Utils;

/**
 * Created by Administrator on 2018/3/17.
 */

public class PersonMyGridAdapter extends BaseAdapter {
    int count;
    public PersonMyGridAdapter(int count){
        this.count = count;
    }
    @Override
    public int getCount() {
        return count;
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
        convertView = new Utils().inflate(R.layout.person_grid_item);
        return convertView;
    }
}
