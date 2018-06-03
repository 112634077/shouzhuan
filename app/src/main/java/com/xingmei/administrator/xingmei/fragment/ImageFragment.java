package com.xingmei.administrator.xingmei.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xingmei.administrator.xingmei.R;
import com.xingmei.administrator.xingmei.adapter.ViewPagerAdapteer;

import java.util.ArrayList;
import java.util.List;

public class ImageFragment extends Fragment implements ViewPager.OnPageChangeListener {
    protected TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<ImageTabFragment> mFragment = new ArrayList<>();
    private ViewPagerAdapteer mImageViewPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        viewUtils();
        initKong();
    }

    private void initView(){
        mTabLayout = getActivity().findViewById(R.id.image_tabLayout);
        mViewPager = getActivity().findViewById(R.id.image_vp);

    }

    private void viewUtils(){
        for (int i = 1; i <= 8; i++){
            ImageTabFragment fragment = new ImageTabFragment();
            Bundle bundle = new Bundle();
            bundle.putString("url","url"+i);
            fragment.setArguments(bundle);
            mFragment.add(fragment);
        }
    }

    protected void initKong(){
//        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mImageViewPagerAdapter = new ViewPagerAdapteer(getChildFragmentManager(),mFragment);
        mViewPager.setAdapter(mImageViewPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        mTabLayout.setupWithViewPager(mViewPager);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    //ViewPager滑动监听事件
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
// position :当前页面，及你点击滑动的页面；positionOffset:当前页面偏移的百分比；positionOffsetPixels:当前页面偏移的像素位置
    }

    @Override
    public void onPageSelected(int position) {
        // position是当前选中的页面的Position
    }

    @Override
    public void onPageScrollStateChanged(int state) {
//state ==1的时表示正在滑动，arg0==2的时表示滑动完毕了，arg0==0的时表示什么都没
    }
}
