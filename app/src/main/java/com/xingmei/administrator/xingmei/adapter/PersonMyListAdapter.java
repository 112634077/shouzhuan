package com.xingmei.administrator.xingmei.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xingmei.administrator.xingmei.R;
import com.xingmei.administrator.xingmei.carousel.MyGridView;
import com.xingmei.administrator.xingmei.utils.Utils;

/**
 * Created by Administrator on 2018/3/17.
 */

public class PersonMyListAdapter extends BaseAdapter {
    private int count[] = {4,8,10,13,20};
    @Override
    public int getCount() {
        return 5;
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
        Holder mHolder = null;
        if (convertView == null){
            mHolder = new Holder();
            convertView = new Utils().inflate(R.layout.person_llist_item);
            mHolder.myGridView = convertView.findViewById(R.id.person_item_grid);

            convertView.setTag(mHolder);
        }else {
            mHolder = (Holder) convertView.getTag();
        }
        mHolder.myGridView.setAdapter(new PersonMyGridAdapter(count[position]));
        return convertView;
    }

    class Holder{
        MyGridView myGridView;
    }
}
