package com.kingyon.newslib.uis.fragments;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kingyon.baseuilib.fragments.BaseRefreshFragment;
import com.kingyon.newslib.R;
import com.kingyon.newslib.entities.NewsEntity;
import com.kingyon.newslib.greendao.entities.ColumnEntity;
import com.kingyon.newslib.uis.adapters.NewsAdapter;
import com.kingyon.newslib.utils.NewsTypeUtil;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

/**
 * Created by arvin on 2016/7/29 16:08
 */
public class NewsFragment extends BaseRefreshFragment<NewsEntity> {
    private ColumnEntity mColumnEntity;

    public NewsFragment() {
    }

    @SuppressLint("ValidFragment")
    public NewsFragment(ColumnEntity columnEntity) {
        this.mColumnEntity = columnEntity;
    }

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
        //NewsService getCacheData
        return new NewsAdapter(getActivity(), mItems);
    }

    @Override
    protected void loadData(final int index) {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean hasMore = false;
                if (index == 0) {
                    mItems.add(new NewsEntity(NewsTypeUtil.SINGLE_NONE, "资讯标题", "Arvin"));
                    mItems.add(new NewsEntity(NewsTypeUtil.SINGLE_NONE, "资讯标题", "Arvin"));
                    mItems.add(new NewsEntity(NewsTypeUtil.SINGLE_NONE, "资讯标题", "Arvin"));
                    mItems.add(new NewsEntity(NewsTypeUtil.SINGLE_NONE, "资讯标题", "Arvin"));
                    hasMore = true;
                    mLoadMoreWrapper.notifyDataSetChanged();
                }
                refreshOk(hasMore);
            }
        }, 500);
    }
}
