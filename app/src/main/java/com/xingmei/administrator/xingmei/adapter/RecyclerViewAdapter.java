package com.xingmei.administrator.xingmei.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xingmei.administrator.xingmei.R;
import com.xingmei.administrator.xingmei.utils.MoreTypeBean;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter {
    //定义三种常量  表示三种条目类型
    public static final int TYPE_PULL_IMAGE = 0;//无图片界面
    public static final int TYPE_RIGHT_IMAGE = 1;//一张图片界面
    public static final int TYPE_THREE_IMAGE = 2;//三张图片界面
    public static final int TYPE_IMAGE_IMAGE = 3;//无文字，图片界面

    private List<MoreTypeBean> mData;
    private Context context;

    public RecyclerViewAdapter(Context context,List<MoreTypeBean> mData){
        this.context = context;
        this.mData = mData;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == TYPE_PULL_IMAGE) {
            view =View.inflate(parent.getContext(), R.layout.home_list_item2,null);
            return new PullImageHolder(view);
        } else if (viewType == TYPE_RIGHT_IMAGE) {
            view =View.inflate(parent.getContext(),R.layout.home_list_item1,null);
            return new RightImageHolder(view);
        } else if (viewType == TYPE_THREE_IMAGE){
            view =View.inflate(parent.getContext(),R.layout.home_list_item3,null);
            return new ThreeImageHolder(view);
        }else {
            view =View.inflate(parent.getContext(),R.layout.home_list_item1,null);
            return new ImageImageHolder(view);
        }

    }
  /**Glide.with(context).load(list.get(position).getImageUrl().get(0))
            .override(dpToPx(72), dpToPx(72)).centerCrop().into(viewHolder.image);
 */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MoreTypeBean moreTypeBean = mData.get(position);

        if (holder instanceof PullImageHolder){
            PullImageHolder pullImageHolder = (PullImageHolder) holder;
            pullImageHolder.title.setText(moreTypeBean.getTitleString());
            pullImageHolder.source.setText(moreTypeBean.getSource());

            setTextLines(pullImageHolder.title);
        }else if (holder instanceof RightImageHolder){
            RightImageHolder rightImageHolder = (RightImageHolder) holder;
            rightImageHolder.title.setText(moreTypeBean.getTitleString());
            Glide.with(context).load(moreTypeBean.getIconURL().get(0)).into(rightImageHolder.imageView);
            rightImageHolder.source.setText(moreTypeBean.getSource());

            setTextLines(rightImageHolder.title);
        }else if (holder instanceof ThreeImageHolder){
            ThreeImageHolder threeImageHolder = (ThreeImageHolder) holder;
            threeImageHolder.title.setText(moreTypeBean.getTitleString());
            threeImageHolder.source.setText(moreTypeBean.getSource());
            for (int i = 0; i < moreTypeBean.getIconURL().size(); i++){
                System.out.println("getICONURL======================="+moreTypeBean.getIconURL().get(i));
            }
            Glide.with(context).load(moreTypeBean.getIconURL().get(0)).into(threeImageHolder.imageView1);
            Glide.with(context).load(moreTypeBean.getIconURL().get(1)).into(threeImageHolder.imageView2);
            Glide.with(context).load(moreTypeBean.getIconURL().get(2)).into(threeImageHolder.imageView3);

            setTextLines(threeImageHolder.title);
        }
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
        if (moreTypeBean.getType() == 0) {
            return TYPE_PULL_IMAGE;
        } else if (moreTypeBean.getType() == 1) {
            return TYPE_RIGHT_IMAGE;
        } else if (moreTypeBean.getType() == 2){
            return TYPE_THREE_IMAGE;
        } else {
            return TYPE_IMAGE_IMAGE;
        }
    }

    private void setTextLines(TextView title){
        int count = title.getLayout().getLineCount();
        if (count > 1){
            title.setLines(context.getResources().getInteger(R.integer.two));
        }
    }
    /**
     * 创建四种ViewHolder
     */
    //文字，无图片界面
     class PullImageHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView source;
        public PullImageHolder(View itemView) {
            super(itemView);

            initTitle(itemView);
        }

        private void initTitle(View view){
            title = view.findViewById(R.id.monetization_item2_title);
            source = view.findViewById(R.id.monetization_item2_source);
        }
    }
    //一张图片界面
     class RightImageHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView imageView;
        private TextView source;
        public RightImageHolder(View itemView) {
            super(itemView);
            initTitle(itemView);
        }

        private void initTitle(View view){
            title = view.findViewById(R.id.monetization_item1_title);
            imageView = view.findViewById(R.id.monetization_item1_image);
            source = view.findViewById(R.id.monetization_item1_source);
        }
    }
    //三张图片界面
     class ThreeImageHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView imageView1;
        private ImageView imageView2;
        private ImageView imageView3;
        private TextView source;
        public ThreeImageHolder(View itemView) {
            super(itemView);
            initTitle(itemView);
        }

        private void initTitle(View view){
            title = view.findViewById(R.id.home_item3_title);
            imageView1 = view.findViewById(R.id.home_item3_image1);
            imageView2 = view.findViewById(R.id.home_item3_image2);
            imageView3 = view.findViewById(R.id.home_item3_image3);
            source = view.findViewById(R.id.home_item3_source);

        }
    }
    //无文字，图片界面
     class ImageImageHolder extends RecyclerView.ViewHolder {
         public TextView title;
        public ImageImageHolder(View itemView) {
            super(itemView);
        }

    }
}
