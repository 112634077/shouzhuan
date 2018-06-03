package com.xingmei.administrator.xingmei.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xingmei.administrator.xingmei.R;
import com.xingmei.administrator.xingmei.utils.Utils;

/**
 * Created by Administrator 2018/3/14.
 */

public class MoetizationListAdapter extends BaseAdapter {
    private Context context;
    public MoetizationListAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return 0;
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
        return null;
    }

    //文章一  有一张图片
    private View item1(String title,String source,String pathUrl){
        View view = null;
        VIew1Holder vIew1Handler;
        if (view == null){
            vIew1Handler = new VIew1Holder();
            view = new Utils().inflate(R.layout.home_list_item1);
            vIew1Handler.imageView = view.findViewById(R.id.monetization_item1_image);
            vIew1Handler.title = view.findViewById(R.id.monetization_item1_title);
            vIew1Handler.source = view.findViewById(R.id.monetization_item1_source);

            view.setTag(vIew1Handler);
        }else {
            vIew1Handler = (VIew1Holder) view.getTag();
        }

        vIew1Handler.title.setText(title);
        vIew1Handler.source.setText(source);
        Glide.with(context).load(pathUrl).into(vIew1Handler.imageView);
        return view;
    }

    //文章2  无图片
    private View item2(String title,String source){
        View view = null;
        VIew2Holder vIew2Holder;
        if (view == null){
            vIew2Holder = new VIew2Holder();
            view = new Utils().inflate(R.layout.home_list_item2);
            vIew2Holder.title = view.findViewById(R.id.monetization_item2_title);
            vIew2Holder.source = view.findViewById(R.id.monetization_item2_source);

            view.setTag(vIew2Holder);
        }else {
            vIew2Holder = (VIew2Holder) view.getTag();
        }

        vIew2Holder.title.setText(title);
        vIew2Holder.source.setText(source);

        return view;
    }


    class VIew1Holder{
        ImageView imageView;
        TextView title;
        TextView source;
    }

    class VIew2Holder{
        TextView title;
        TextView source;
    }
}
