package com.kingyon.newslib.uis.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kingyon.baseuilib.fragments.BaseRefreshFragment;
import com.kingyon.baseuilib.utils.ConstantUtil;
import com.kingyon.netlib.callback.AbsAPICallback;
import com.kingyon.netlib.entitys.PageListEntity;
import com.kingyon.netlib.exception.ApiException;
import com.kingyon.newslib.R;
import com.kingyon.newslib.greendao.entities.ColumnEntity;
import com.kingyon.newslib.greendao.entities.NewsEntity;
import com.kingyon.newslib.greendao.utils.NewsService;
import com.kingyon.newslib.uis.activities.NewsDetailActivity;
import com.kingyon.newslib.uis.adapters.NewsAdapter;
import com.kingyon.newslib.utils.NewsTypeUtil;
import com.kingyon.refresh.recyclerview.RecyclerAdapterWithHF;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by arvin on 2016/7/29 16:08
 */
public class NewsFragment extends BaseRefreshFragment<NewsEntity> {
    private ColumnEntity mColumnEntity;
    private NewsService newsService;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsService = new NewsService(getActivity());
    }

    @Override
    protected MultiItemTypeAdapter<NewsEntity> getAdapter() {
        newsService.getObservableCacheNews(mColumnEntity.getObjectId()).subscribe(new Action1<List<NewsEntity>>() {
            @Override
            public void call(List<NewsEntity> newsEntities) {
                if (mItems.size() == 0) {
                    mItems.addAll(newsEntities);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
        return new NewsAdapter(getActivity(), mItems);
    }

    @Override
    protected void loadData(final int page) {
        newsService.getNews(mColumnEntity.getObjectId(), page).subscribe(new AbsAPICallback<PageListEntity<NewsEntity>>() {
            @Override
            protected void onResultError(ApiException ex) {
                mUtil.showToast(ex.getDisplayMessage());
                refreshOk(false);
            }

            @Override
            public void onNext(PageListEntity<NewsEntity> data) {
                boolean hasMore = false;
                List<NewsEntity> temp = data.getContent();
                if (page == 0) {
                    mItems.clear();
                }
                if (temp != null && temp.size() > 0) {
                    mItems.addAll(temp);
                    hasMore = true;
                    mAdapter.notifyDataSetChanged();
                }
                refreshOk(hasMore);
            }
        });
    }

    @Override
    public void onItemClick(RecyclerAdapterWithHF adapter, RecyclerView.ViewHolder vh, int position) {
        super.onItemClick(adapter, vh, position);
        Bundle data = new Bundle();
        data.putInt(ConstantUtil.PASS_EXTRA, NewsDetailActivity.ARTICLE);
        data.putParcelable(ConstantUtil.PASS_VALUE, mItems.get(position));
        mUtil.startActivityWithAnim(NewsDetailActivity.class, data);
    }
}
