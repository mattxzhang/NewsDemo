package com.kingyon.baseuilib.listeners;

import android.support.v4.app.Fragment;

/**
 * Created by arvin on 2016/2/4 15:23
 * .
 */
public interface ITabPager {
    Fragment getItem(int position);

    String getTitle(int position);
}
