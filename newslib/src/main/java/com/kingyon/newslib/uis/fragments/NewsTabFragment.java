package com.kingyon.newslib.uis.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.kingyon.baseuilib.fragments.BaseTabFragment;
import com.kingyon.baseuilib.utils.ConstantUtil;
import com.kingyon.netlib.callback.AbsAPICallback;
import com.kingyon.netlib.entitys.PageListEntity;
import com.kingyon.netlib.exception.ApiException;
import com.kingyon.newslib.R;
import com.kingyon.newslib.greendao.entities.ColumnEntityDao;
import com.kingyon.newslib.greendao.utils.ColumnService;
import com.kingyon.newslib.greendao.entities.ColumnEntity;
import com.kingyon.newslib.nets.NewsNetCloud;
import com.kingyon.newslib.uis.activities.ColumnManagerActivity;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * created by arvin on 16/8/2 15:23
 * email：1035407623@qq.com
 */
public class NewsTabFragment extends BaseTabFragment<ColumnEntity> {
    ColumnService columnService;

    @Override
    protected int getContentViewID() {
        return R.layout.nl_fragment_news_tab;
    }

    @Override
    protected int getTabTextColor() {
        return R.color.white_normal;
    }

    @Override
    protected int getSelectedTabTextColor() {
        return R.color.white_normal;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        columnService = new ColumnService(getActivity());
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        getView(R.id.img_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();
                data.putParcelableArrayList(ConstantUtil.PASS_VALUE, mItems);
                mUtil.startActivityWithAnim(ColumnManagerActivity.class, data);
            }
        });
    }

    @Override
    protected void getData() {
        mItems.addAll(columnService.getSubscribeList());
        initPager();
        final boolean hasData = mItems.size() > 0;

        columnService.getColumns(hasData).subscribe(new AbsAPICallback<PageListEntity<ColumnEntity>>() {
            @Override
            protected void onResultError(ApiException ex) {
                mUtil.showToast(ex.getDisplayMessage());
            }

            @Override
            public void onNext(PageListEntity<ColumnEntity> data) {
                mItems.clear();
                mItems.addAll(columnService.getSubscribeList());
                initPager();
            }
        });
    }

    @Override
    public String getTitle(int position) {
        return mItems.get(position).getName();
    }

    @Override
    public Fragment getItem(int position) {
        return new NewsFragment(mItems.get(position));
    }

    @Override
    public void onDestroyView() {
        columnService.onDestroy();
        super.onDestroyView();
    }
}
