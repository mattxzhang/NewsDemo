package com.kingyon.newslib.uis.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kingyon.baseuilib.fragments.BaseRefreshFragment;
import com.kingyon.newslib.R;
import com.kingyon.newslib.entities.NewsEntity;
import com.kingyon.newslib.uis.adapters.NewsAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

/**
 * Created by arvin on 2016/7/29 16:08
 */
public class NewsFragment extends BaseRefreshFragment<NewsEntity> {

    @Override
    protected int getContentViewID() {
        return R.layout.nl_fragment_news;
    }

    @Override
    protected int getEmptyViewId() {
        return R.layout.ui_layout_empty;
    }

    @Override
    protected int getLoadMoreViewId() {
        return R.layout.ui_layout_load_more;
    }

    @Override
    protected MultiItemTypeAdapter<NewsEntity> getAdapter() {
        return new NewsAdapter(getActivity(), mItems);
    }

    @Override
    protected void loadData(int index) {
        mLoadMoreWrapper.notifyDataSetChanged();
    }
}
