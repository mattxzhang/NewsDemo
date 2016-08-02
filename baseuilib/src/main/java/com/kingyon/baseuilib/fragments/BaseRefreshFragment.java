package com.kingyon.baseuilib.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kingyon.baseuilib.R;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arvin on 2016/7/29 16:24
 * Attention please!Using this class must make contentView add "<include layout="@layout/ui_layout_refresh"/>" or
 * define by yourself,but also must include SwipeRefreshLayout(id must be pre_layout_refresh) and RecyclerView(id must be pre_rc_list).
 */
public abstract class BaseRefreshFragment<T> extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        MultiItemTypeAdapter.OnItemClickListener {
    protected SwipeRefreshLayout mRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected MultiItemTypeAdapter<T> mAdapter;

    protected EmptyWrapper mEmptyWrapper;
    protected LoadMoreWrapper mLoadMoreWrapper;

    protected List<T> mItems = new ArrayList<>();
    protected int mCurrPage;
    protected boolean isLoadMore = true;
    private View view;

    protected void resetCurrPage() {
        mCurrPage = 0;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        resetCurrPage();
        initRefresh(savedInstanceState);
    }

    protected void initRefresh(Bundle savedInstanceState) {
        mRefreshLayout = getView(R.id.pre_layout_refresh);
        mRecyclerView = getView(R.id.pre_rc_list);
        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        if (isShowDivider()) {
            setDivider();
        }

        mRecyclerView.setLayoutManager(getLayoutManager());
        initAdapter();
        mRecyclerView.setAdapter(mLoadMoreWrapper);
        mRefreshLayout.setOnRefreshListener(this);

        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        }, 500);
    }

    protected boolean isShowDivider() {
        return true;
    }

    protected void setDivider() {
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mUtil.mContext)
                .color(getResources().getColor(R.color.black_divider))
                .sizeResId(R.dimen.spacing_divider)
                .build());
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(mUtil.mContext);
    }

    private void initAdapter() {
        mAdapter = getAdapter();
        mAdapter.setOnItemClickListener(this);
        initEmptyView();
        initLoadMoreView();
    }

    private void initEmptyView() {
        mEmptyWrapper = new EmptyWrapper(mAdapter);
        mEmptyWrapper.setEmptyView(mUtil.getInflater().inflate(getEmptyViewId(), mRecyclerView, false));
    }

    private void initLoadMoreView() {
        mLoadMoreWrapper = new LoadMoreWrapper(mEmptyWrapper);
        mLoadMoreWrapper.setLoadMoreView(getLoadMoreView());

        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadData(++mCurrPage);
            }
        });
    }

    @Override
    public void onRefresh() {
        resetCurrPage();
        loadData(mCurrPage);
    }

    protected void refreshOk(boolean hasMore) {
        mRefreshLayout.setRefreshing(false);
        if(mCurrPage>0&&!hasMore){
            noDataLoad();
        }
    }

    /**
     * 加载时没有更多数据,目前没有想到优雅的方法的去处理
     */
    protected void noDataLoad() {
        mCurrPage--;
        mLoadMoreWrapper.setLoadMoreView(null);
        mLoadMoreWrapper.notifyDataSetChanged();
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadMoreWrapper.setLoadMoreView(getLoadMoreView());
                mLoadMoreWrapper.notifyDataSetChanged();
            }
        },200);
    }

    private View getLoadMoreView() {
        if(view == null) {
            view = mUtil.getInflater().inflate(getLoadMoreViewId(), mRecyclerView, false);
        }
        return view;
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, Object item, int position) {
    }

    protected abstract int getEmptyViewId();

    protected abstract int getLoadMoreViewId();

    protected abstract MultiItemTypeAdapter<T> getAdapter();

    /**
     * 加载完后使用mLoadMoreWrapper.notifyDataSetChanged(); 刷新列表
     * @param page 加载第几页的数据
     */
    protected abstract void loadData(int page);
}
