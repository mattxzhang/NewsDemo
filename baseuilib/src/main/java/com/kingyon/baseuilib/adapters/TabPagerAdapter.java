package com.kingyon.baseuilib.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.kingyon.baseuilib.entities.TabPagerEntity;
import com.kingyon.baseuilib.listeners.ITabPager;

import java.util.List;

/**
 * Created by arvin on 2016/2/4 15:26
 * .
 */
public class TabPagerAdapter extends FragmentStatePagerAdapter {
    private List<TabPagerEntity> mList;
    private ITabPager mTabPagerListener;

    public TabPagerAdapter(FragmentManager fm, List<TabPagerEntity> list, ITabPager pagerItemListener) {
        super(fm);
        this.mList = list;
        this.mTabPagerListener = pagerItemListener;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position).getTitle();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mTabPagerListener.getItem(position);
    }
}
