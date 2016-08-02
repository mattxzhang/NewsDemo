package com.kingyon.newslib.uis.fragments;

import android.support.v4.app.Fragment;

import com.kingyon.baseuilib.entities.TabPagerEntity;
import com.kingyon.baseuilib.fragments.BaseTabFragment;
import com.kingyon.newslib.R;

/**
 * created by arvin on 16/8/2 15:23
 * email：1035407623@qq.com
 */
public class NewsTabFragment extends BaseTabFragment {
    @Override
    protected void getData() {
        mItems.add(new TabPagerEntity("tab1",null));
        mItems.add(new TabPagerEntity("tab2",null));
        mItems.add(new TabPagerEntity("tab3",null));
        mItems.add(new TabPagerEntity("tab4",null));
        mItems.add(new TabPagerEntity("tab5",null));
        initPager();
    }

    @Override
    protected String getTitleText() {
        return "";
    }

    @Override
    protected int getContentViewID() {
        return R.layout.ui_layout_tab;
    }

    @Override
    public Fragment getItem(int position) {
        return new NewsFragment();
    }
}
