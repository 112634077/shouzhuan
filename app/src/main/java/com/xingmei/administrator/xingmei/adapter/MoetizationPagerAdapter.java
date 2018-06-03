package com.xingmei.administrator.xingmei.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xingmei.administrator.xingmei.R;
import com.xingmei.administrator.xingmei.utils.PagerInfo;

import java.util.List;

public class MoetizationPagerAdapter extends PagerAdapter {
    private List<PagerInfo> mList;
    private Context mContext;

    public MoetizationPagerAdapter(Context mContext,List<PagerInfo> mList){
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(mContext, R.layout.home_image_fragment,null);
        ImageView imageView = view.findViewById(R.id.monetization_fragment_image);

        PagerInfo pi = mList.get(position % mList.size());
        Glide.with(mContext).load(pi.getIconUrl()).into(imageView);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
