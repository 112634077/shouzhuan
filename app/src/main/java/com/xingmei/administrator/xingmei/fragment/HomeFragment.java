package com.xingmei.administrator.xingmei.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.xingmei.administrator.xingmei.R;
import com.xingmei.administrator.xingmei.adapter.ViewPagerAdapteer;
import com.xingmei.administrator.xingmei.adapter.MoetizationListAdapter;
import com.xingmei.administrator.xingmei.adapter.MoetizationPagerAdapter;
import com.xingmei.administrator.xingmei.carousel.ViewPagerIndicator;
import com.xingmei.administrator.xingmei.utils.PagerInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener{

    private ViewPager viewpager;
    private ViewPagerIndicator indicator;
    private List<String> mImagePath= Arrays.asList(
            "http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://p4.so.qhmsg.com/t0107e28d8d1fc900d9.jpg",
            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg");

    protected MoetizationPagerAdapter moetizationPagerAdapter;
    protected MoetizationListAdapter moetizationListAdapter;

    private ViewPager mViewPager;
    protected TabLayout mTabLayout;
    private List<MonetiTabFragment> mFragment = new ArrayList<>();

    private String title[];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initAdapter();

        indicator.setTabItemImagecator(mImagePath);
        indicator.setViewPager(viewpager);

        viewUtils();
    }

    protected void initAdapter(){
        List<PagerInfo> pList = new ArrayList<>();
        for (String url : mImagePath){
            pList.add(new PagerInfo(url));
        }
        moetizationPagerAdapter = new MoetizationPagerAdapter(getActivity(),pList);
        moetizationListAdapter = new MoetizationListAdapter(getActivity());
        viewpager.setAdapter(moetizationPagerAdapter);
        viewpager.setCurrentItem(Integer.MAX_VALUE / 2 - ((Integer.MAX_VALUE / 2 )% mImagePath.size()));

    }

    protected  void initView(){
        viewpager = getActivity().findViewById(R.id.viewpager);
        mViewPager = getActivity().findViewById(R.id.monetization_vp);
        indicator = getActivity().findViewById(R.id.indicator);
        mTabLayout = getActivity().findViewById(R.id.monetization_tabs);
    }

    private void viewUtils(){
        title = new String[]{getTitle(R.string.home_article),getTitle(R.string.home_image),getTitle(R.string.home_video)};

        for (int i = 1; i <= title.length; i++){
            MonetiTabFragment fragment = new MonetiTabFragment();
            Bundle bundle = new Bundle();
            bundle.putString("url","url"+i);
            fragment.setArguments(bundle);
            mFragment.add(fragment);
        }

        initKong();
    }

    protected void initKong(){
//        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        ViewPagerAdapteer mImageViewPagerAdapter = new ViewPagerAdapteer(getChildFragmentManager(),mFragment,title,2);
        mViewPager.setAdapter(mImageViewPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private  String getTitle(int id){
        return  getActivity().getResources().getString(id);
    }

    @Override
    public void onDetach() {

        super.onDetach();
    }
    //ViewPager滑动监听事件
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // position :当前页面，及你点击滑动的页面；positionOffset:当前页面偏移的百分比；positionOffsetPixels:当前页面偏移的像素位置
    }

    @Override
    public void onPageSelected(int position) {
// position是当前选中的页面的Positionnt position
    }

    @Override
    public void onPageScrollStateChanged(int state) {
//state ==1的时表示正在滑动，arg0==2的时表示滑动完毕了，arg0==0的时表示什么都没
    }

}
