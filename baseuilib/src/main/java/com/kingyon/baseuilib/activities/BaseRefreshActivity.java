package com.kingyon.baseuilib.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kingyon.baseuilib.R;
import com.kingyon.baseuilib.fragments.BaseFragment;
import com.kingyon.refresh.loadmore.OnLoadMoreListener;
import com.kingyon.refresh.loadmore.SwipeRefreshHelper;
import com.kingyon.refresh.recyclerview.RecyclerAdapterWithHF;
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
public abstract class BaseRefreshActivity<T> extends BaseSwipeBackActivity implements SwipeRefreshHelper.OnSwipeRefreshListener, OnLoadMoreListener,
        MultiItemTypeAdapter.OnItemClickListener {
    protected SwipeRefreshLayout mRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected SwipeRefreshHelper mSwipeRefreshHelper;

    protected MultiItemTypeAdapter<T> mInnerAdapter;
    protected EmptyWrapper mEmptyWrapper;
    protected RecyclerAdapterWithHF mAdapter;

    protected List<T> mItems = new ArrayList<>();
    protected int mCurrPage;

    protected void resetCurrPage() {
        mCurrPage = 0;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
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
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshHelper = new SwipeRefreshHelper(mRefreshLayout);
        mSwipeRefreshHelper.setOnSwipeRefreshListener(this);
        mSwipeRefreshHelper.setOnLoadMoreListener(this);

        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshHelper.autoRefresh();
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
        mInnerAdapter = getAdapter();
        mInnerAdapter.setOnItemClickListener(this);
        initEmptyView();
        initLoadMoreView();
    }

    private void initEmptyView() {
        mEmptyWrapper = new EmptyWrapper(mInnerAdapter);
        mEmptyWrapper.setEmptyView(mUtil.getInflater().inflate(getEmptyViewId(), mRecyclerView, false));
    }

    private void initLoadMoreView() {
        mAdapter = new RecyclerAdapterWithHF(mEmptyWrapper);
    }

    @Override
    public void onRefresh() {
        resetCurrPage();
        loadData(mCurrPage);
    }

    @Override
    public void loadMore() {
        loadData(++mCurrPage);
    }

    protected void refreshOk(boolean hasMore) {
        if (!hasMore && mCurrPage > 0) {
            mCurrPage--;
        }
        mSwipeRefreshHelper.refreshComplete();
        mSwipeRefreshHelper.setLoadMoreEnable(mItems.size() >= 8);
        if (mCurrPage > 0) {
            mSwipeRefreshHelper.loadMoreComplete(true);
        }
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, Object item, int position) {
    }

    protected abstract int getEmptyViewId();

    protected abstract MultiItemTypeAdapter<T> getAdapter();

    /**
     * 加载完后使用mAdapter.notifyDataSetChanged(); 刷新列表
     *
     * @param page 加载第几页的数据
     */
    protected abstract void loadData(int page);
}

