package com.kingyon.baseuilib.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.kingyon.baseuilib.R;
import com.kingyon.baseuilib.adapters.TabPagerAdapter;
import com.kingyon.baseuilib.entities.TabPagerEntity;
import com.kingyon.baseuilib.listeners.ITabPager;
import com.kingyon.baseuilib.utils.ScreenUtil;
import com.kingyon.baseuilib.views.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 * created by arvin on 16/8/2 14:55
 * email：1035407623@qq.com
 */
public abstract class BaseTabActivity extends BaseHeaderActivity implements ITabPager,ViewPager.OnPageChangeListener{
    protected PagerSlidingTabStrip mTabLayout;
    protected ViewPager mPager;
    protected List<TabPagerEntity> mItems;
    protected TabPagerAdapter mAdapter;
    protected int selectedIndex = -1;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mPager = getView(R.id.pre_pager);
        mTabLayout = getView(R.id.pre_tab_layout);

        mItems = new ArrayList<>();
        getData();
    }

    protected void initTab(){
        mTabLayout.setTextColor(getTabTextColor());
        mTabLayout.setSelectedTextColorResource(getSelectedTabTextColor());
        mTabLayout.setIndicatorColorResource(getSelectedTabTextColor());
        mTabLayout.setIndicatorHeight(ScreenUtil.dp2px(2));
        mTabLayout.setUnderlineColor(Color.TRANSPARENT);
    }

    protected int getSelectedTabTextColor() {
        return R.color.colorAccent;
    }

    protected int getTabTextColor() {
        return R.color.black_normal;
    }

    protected void initPager(){
        if(mItems==null||mItems.size()==0){
            return ;
        }
        mAdapter = new TabPagerAdapter(getSupportFragmentManager(),mItems,this);
        mPager.setAdapter(mAdapter);
        mTabLayout.setViewPager(mPager);
        mTabLayout.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onPageSelected(int position) {
        selectedIndex = position;
    }

    /**
     * 获取完数据后回调设置pager
     */
    protected abstract void getData();
}
