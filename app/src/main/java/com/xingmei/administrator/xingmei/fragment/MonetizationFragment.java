package com.xingmei.administrator.xingmei.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.xingmei.administrator.xingmei.R;
import com.xingmei.administrator.xingmei.adapter.MoetizationListAdapter;
import com.xingmei.administrator.xingmei.adapter.MoetizationPagerAdapter;
import com.xingmei.administrator.xingmei.carousel.ViewPagerIndicator;
import com.xingmei.administrator.xingmei.utils.PagerInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MonetizationFragment extends Fragment {

    ViewPager viewpager;
    ViewPagerIndicator indicator;
    private List<String> mImagePath= Arrays.asList(
            "http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://p4.so.qhmsg.com/t0107e28d8d1fc900d9.jpg",
            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg");

    protected MoetizationPagerAdapter moetizationPagerAdapter;
    private ListView mListView;
    protected MoetizationListAdapter moetizationListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_monetization, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();

        initAdapter();

        indicator.setTabItemImagecator(mImagePath);
        indicator.setViewPager(viewpager);
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

        mListView.setAdapter(moetizationListAdapter);
    }

    protected  void initView(){
        viewpager = getActivity().findViewById(R.id.viewpager);
        indicator = getActivity().findViewById(R.id.indicator);
        mListView = getActivity().findViewById(R.id.monetization_listView);
    }

    @Override
    public void onDetach() {

        super.onDetach();
    }
}
