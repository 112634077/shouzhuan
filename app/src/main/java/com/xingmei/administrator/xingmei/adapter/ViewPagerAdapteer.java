package com.xingmei.administrator.xingmei.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.xingmei.administrator.xingmei.fragment.ImageTabFragment;
import com.xingmei.administrator.xingmei.fragment.Journalism_item;
import com.xingmei.administrator.xingmei.fragment.MonetiTabFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator o2018/3/15.
 */

public class ViewPagerAdapteer extends FragmentPagerAdapter {
    private List<Journalism_item> mFragment;
    private List<MonetiTabFragment> monetiFragment;
    private String title[];
    private List<String> tags;
    private FragmentManager fragmentManager;
    private int index;

    public ViewPagerAdapteer(FragmentManager fm, List<Journalism_item> mFragment, String title[]) {
        super(fm);
        this.fragmentManager = fm;
        this.mFragment = mFragment;
        this.tags = new ArrayList<>();
        this.title = title;
        index = 0;
    }

    public ViewPagerAdapteer(FragmentManager fm, List<MonetiTabFragment> monetiFragment,String title[], int index) {
        super(fm);
        this.fragmentManager = fm;
        this.monetiFragment = monetiFragment;
        this.tags = new ArrayList<>();
        this.title = title;
        this.index = index;
    }

    public void setNewFragments(List<Journalism_item> fragments) {
        if (this.tags != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            for (int i = 0; i < tags.size(); i++) {
                fragmentTransaction.remove(fragmentManager.findFragmentByTag(tags.get(i)));
            }
            fragmentTransaction.commit();
            fragmentManager.executePendingTransactions();
            tags.clear();
        }
        this.mFragment = fragments;
        notifyDataSetChanged();
    }
    @Override
    public Fragment getItem(int position) {
        if (index == 0)
        return mFragment.get(position);
        else return monetiFragment.get(position);
    }

    @Override
    public int getCount() {
        if (index == 0)
        return mFragment.size();
        else return monetiFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        tags.add(makeFragmentName(container.getId(), getItemId(position)));
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        this.fragmentManager.beginTransaction().show(fragment).commit();
        return fragment;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment;
        if (index == 0)
        fragment = mFragment.get(position);
        else fragment = monetiFragment.get(position);
        fragmentManager.beginTransaction().hide(fragment).commit();
    }

    @Override
    public int getItemPosition(Object object) {

        return POSITION_NONE;
    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }
}
