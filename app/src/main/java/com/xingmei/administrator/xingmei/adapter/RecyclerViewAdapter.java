package com.xingmei.administrator.xingmei.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.xingmei.administrator.xingmei.R;
import com.xingmei.administrator.xingmei.utils.MoreTypeBean;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter {
    //定义三种常量  表示三种条目类型
    public static final int TYPE_PULL_IMAGE = 0;//无图片界面
    public static final int TYPE_RIGHT_IMAGE = 1;//一张图片界面
    public static final int TYPE_THREE_IMAGE = 2;//三张图片界面
    public static final int TYPE_IMAGE_IMAGE = 3;//无文字，图片界面

    private int type;
    private List<MoreTypeBean> mData;

    public RecyclerViewAdapter(int type){
        this.type = type;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == TYPE_PULL_IMAGE) {
            view =View.inflate(parent.getContext(), R.layout.home_list_item1,null);
            return new PullImageHolder(view);
        } else if (viewType == TYPE_RIGHT_IMAGE) {
            view =View.inflate(parent.getContext(),R.layout.home_list_item1,null);
            return new RightImageHolder(view);
        } else if (viewType == TYPE_THREE_IMAGE){
            view =View.inflate(parent.getContext(),R.layout.home_list_item1,null);
            return new ThreeImageHolder(view);
        }else {
            view =View.inflate(parent.getContext(),R.layout.home_list_item1,null);
            return new ImageImageHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        MoreTypeBean moreTypeBean = mData.get(position);
        if (moreTypeBean.type == 0) {
            return TYPE_PULL_IMAGE;
        } else if (moreTypeBean.type == 1) {
            return TYPE_RIGHT_IMAGE;
        } else if (moreTypeBean.type == 2){
            return TYPE_THREE_IMAGE;
        } else {
            return TYPE_IMAGE_IMAGE;
        }
    }

    /**
     * 创建四种ViewHolder
     */
    private class PullImageHolder extends RecyclerView.ViewHolder {

        public PullImageHolder(View itemView) {
            super(itemView);
        }
    }

    private class RightImageHolder extends RecyclerView.ViewHolder {

        public RightImageHolder(View itemView) {
            super(itemView);
        }
    }

    private class ThreeImageHolder extends RecyclerView.ViewHolder {

        public ThreeImageHolder(View itemView) {
            super(itemView);
        }
    }

    private class ImageImageHolder extends RecyclerView.ViewHolder {

        public ImageImageHolder(View itemView) {
            super(itemView);
        }
    }
}
