package com.xingmei.administrator.xingmei.carousel;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xingmei.administrator.xingmei.R;
import com.xingmei.administrator.xingmei.hanler.ViewPagerHandler;

import java.util.List;

/**
 * 创建时间 2018/3/10.
 */

public class ViewPagerIndicator extends LinearLayout {
    /**
     * tab上的内容
     */
    protected List<String> mTabsImagePath;
    /**
     * 与之绑定的ViewPager
     */
    public ViewPager mViewPager;

    protected int mPosition = 0;

    protected ViewPagerHandler mViewPagerHander;

    public ViewPagerIndicator(Context context) {
        this(context,null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
//        timer.schedule(task, 5000, 8000);// 1s后执行task,经过1s再次执行
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // 设置点击事件
        setItemClickEvent();
    }

    public  void setTabItemImagecator(List<String> datas){
        int i = 0;
        if (datas!=null&&datas.size()>0){
            this.removeAllViews();
            this.mTabsImagePath=datas;

            while (i < mTabsImagePath.size()){
                addView(generateImageView());
                i++;
            }
            // 设置item的click事件
            setItemClickEvent();
        }
    }

    private View generateImageView() {
        ImageView imageView=new ImageView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                25,25);
        lp.setMargins(5,5,5,5);
        imageView.setImageResource(R.drawable.ic_focus_select);
        imageView.setLayoutParams(lp);
        return imageView;
    }
    /**
     * 对外的ViewPager的回调接口
     *
     * @author zhy
     *
     */
    public interface PageChangeListener
    {
        void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels);

        void onPageSelected(int position);

        void onPageScrollStateChanged(int state);
    }

    // 对外的ViewPager的回调接口?
    private PageChangeListener onPageChangeListener;

    // 对外的ViewPager的回调接口的设置
    public void setOnPageChangeListener(PageChangeListener pageChangeListener)
    {
        this.onPageChangeListener = pageChangeListener;
    }

    // 设置关联的ViewPager
    public void setViewPager(final ViewPager mViewPager)
    {
        final int pos = 0;
        this.mViewPager = mViewPager;

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // 设置字体颜色高亮
                resetImageViewColor();
                highLightImageView();

                // 回调
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
//                if (position==mTabsImagePath.size()-1){
//                    SystemClock.sleep(1000);
//                    mViewPager.setCurrentItem(0);
//                }
                mPosition = position;
                // 回调
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrolled(position,
                            positionOffset, positionOffsetPixels);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // 回调
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrollStateChanged(state);
                }

            }
        });
        mViewPagerHander = new ViewPagerHandler(this,mViewPager);
        mViewPagerHander.sendEmptyMessageDelayed(0,3000);
        // 设置当前页
        mViewPager.setCurrentItem(pos);
        // 高亮
        highLightImageView();
    }

    /**
     * 高亮文本
     *
     */

    protected void highLightImageView()
    {
        View view = getChildAt(mViewPager.getCurrentItem()%mTabsImagePath.size());
        if (view instanceof ImageView)
        {
            ((ImageView) view).setImageResource(R.drawable.ic_focus);
        }

    }

    /**
     * 重置文本颜色
     */

    private void resetImageViewColor()
    {
        for (int i = 0; i < getChildCount(); i++)
        {
            View view = getChildAt(i);
            if (view instanceof ImageView)
            {
                ((ImageView) view).setImageResource(R.drawable.ic_focus_select);
            }
        }
    }

    /**
     * 设置点击事件
     */

    public void setItemClickEvent()
    {
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++)
        {
            final int j = i;
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mViewPager.setCurrentItem(j);
                }
            });
        }
    }

}
