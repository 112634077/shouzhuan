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


public class JournalismFragment extends Fragment implements ViewPager.OnPageChangeListener{
    protected TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Journalism_item> mFragment = new ArrayList<>();
    private ViewPagerAdapteer mImageViewPagerAdapter;
    private String title[];
    private String soucre[];
    private Journalism_item.JournalismHandler journalismHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_journalism, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        viewUtils();
        initKong();
    }


    private void initView(){
        mTabLayout = getActivity().findViewById(R.id.journalism_tabLayout);
        mViewPager = getActivity().findViewById(R.id.journalism_vp);

    }

    private void viewUtils(){
        title = new String[]{getTitle(R.string.journalism_top),getTitle(R.string.journalism_shehui),getTitle(R.string.journalism_guonei),getTitle(R.string.journalism_guoji)
                ,getTitle(R.string.journalism_yule),getTitle(R.string.journalism_tiyu),getTitle(R.string.journalism_junshi),getTitle(R.string.journalism_keji)
                ,getTitle(R.string.journalism_caijing),getTitle(R.string.journalism_shishang)};
        soucre = new String[]{getTitle(R.string.journalism_top2),getTitle(R.string.journalism_shehui2),getTitle(R.string.journalism_guonei2),getTitle(R.string.journalism_guoji2)
                ,getTitle(R.string.journalism_yule2),getTitle(R.string.journalism_tiyu2),getTitle(R.string.journalism_junshi2),getTitle(R.string.journalism_keji2)
                ,getTitle(R.string.journalism_caijing2),getTitle(R.string.journalism_shishang2)};

        for (int i = 0; i < title.length; i++){
            Journalism_item fragment = new Journalism_item();
            if (i == 0){
                Bundle bundle = new Bundle();
                bundle.putString("soucre",soucre[0]);
                fragment.setArguments(bundle);
            }
            mFragment.add(fragment);
        }
    }

    protected void initKong() {
        mImageViewPagerAdapter = new ViewPagerAdapteer(getChildFragmentManager(), mFragment,title);
        mViewPager.setAdapter(mImageViewPagerAdapter);
//        mViewPager.setOffscreenPageLimit(mFragment.size());
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // position :当前页面，及你点击滑动的页面；positionOffset:当前页面偏移的百分比；positionOffsetPixels:当前页面偏移的像素位置
    }

    @Override
    public void onPageSelected(int position) {
        // position是当前选中的页面的Position
        Journalism_item journalism_items = mFragment.get(position);
        journalism_items.setJournalismHandler(soucre[position]);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state ==1的时表示正在滑动，arg0==2的时表示滑动完毕了，arg0==0的时表示什么都没
    }
}
