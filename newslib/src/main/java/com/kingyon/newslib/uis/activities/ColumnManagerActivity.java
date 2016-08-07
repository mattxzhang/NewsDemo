package com.kingyon.newslib.uis.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingyon.baseuilib.activities.BaseHeaderActivity;
import com.kingyon.baseuilib.activities.BaseSwipeBackActivity;
import com.kingyon.baseuilib.utils.ConstantUtil;
import com.kingyon.netlib.callback.AbsAPICallback;
import com.kingyon.netlib.entitys.PageListEntity;
import com.kingyon.netlib.exception.ApiException;
import com.kingyon.newslib.R;
import com.kingyon.newslib.greendao.entities.ColumnEntity;
import com.kingyon.newslib.greendao.utils.ColumnService;
import com.kingyon.newslib.uis.adapters.NoSubscribeAdapter;
import com.kingyon.newslib.uis.adapters.SubScribeDragAdapter;
import com.kingyon.newslib.utils.DragViewUtil;
import com.kingyon.newslib.views.DragGridView;
import com.kingyon.newslib.views.ScrollGridView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * created by arvin on 16/8/4 11:03
 * email：1035407623@qq.com
 */
public class ColumnManagerActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private DragGridView gvSubscribe;
    private SubScribeDragAdapter subscribeAdapter;
    private ArrayList<ColumnEntity> subscribeList = new ArrayList<>();

    private ScrollGridView sgvNotSubscribe;
    private NoSubscribeAdapter noSubscribeAdapter;
    private ArrayList<ColumnEntity> noSubscribeList = new ArrayList<>();

    private ColumnService columnService;
    boolean isMove = false;
    private boolean isSave = false;

    @Override
    protected String getTitleText() {
        return "栏目";
    }

    @Override
    protected int getContentViewID() {
        return R.layout.nl_activity_column_manager;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        columnService = new ColumnService(this);
        initView();
        initData();
    }

    private void initView() {
        gvSubscribe = getView(R.id.gv_subscribe);
        sgvNotSubscribe = getView(R.id.sgv_not_subscribe);
        getView(R.id.tv_header_right).setOnClickListener(this);
    }

    public void initData() {
        List<ColumnEntity> temp = getIntent().getExtras().getParcelableArrayList(ConstantUtil.PASS_VALUE);
        if (temp != null) {
            subscribeList.addAll(temp);
        }
        noSubscribeList.addAll(columnService.getNotSubscribeList());

        setAdapter();

        gvSubscribe.setOnItemClickListener(subscribeItemClick);
        sgvNotSubscribe.setOnItemClickListener(notSubscribeItemClick);

        getItems();
    }

    private void getItems() {
        columnService.getColumns(subscribeList.size() > 0).
                observeOn(Schedulers.io()).
                doOnNext(new Action1<PageListEntity<ColumnEntity>>() {
                    @Override
                    public void call(PageListEntity<ColumnEntity> columnEntityPageListEntity) {
                        subscribeList.clear();
                        noSubscribeList.clear();

                        subscribeList.addAll(columnService.getSubscribeList());
                        noSubscribeList.addAll(columnService.getNotSubscribeList());
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<PageListEntity<ColumnEntity>>() {
                    @Override
                    protected void onResultError(ApiException ex) {
                        mUtil.showToast(ex.getDisplayMessage());
                    }

                    @Override
                    public void onNext(PageListEntity<ColumnEntity> columnEntityPageListEntity) {
                        setAdapter();
                    }
                });
    }

    private void setAdapter() {
        subscribeAdapter = new SubScribeDragAdapter(this, subscribeList);
        gvSubscribe.setAdapter(subscribeAdapter);

        noSubscribeAdapter = new NoSubscribeAdapter(this, noSubscribeList);
        sgvNotSubscribe.setAdapter(noSubscribeAdapter);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.tv_header_right) {
            isSave = true;
            saveData();
            mUtil.showToast("保存成功");
        }
    }

    private void saveData() {
        ColumnEntity columnEntity;

        int len = noSubscribeList.size();
        for (int i = 0; i < len; i++) {
            columnEntity = noSubscribeList.get(i);
            columnEntity.setIsOrder(false);
            columnEntity.setItemWeight(i + 1);
            columnService.update(columnEntity);
        }

        len = subscribeList.size();
        for (int i = 0; i < len; i++) {
            columnEntity = subscribeList.get(i);
            columnEntity.setIsOrder(true);
            columnEntity.setItemWeight(i + 1);
            columnService.update(columnEntity);
        }
    }

    private AdapterView.OnItemClickListener subscribeItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            if (position == 0 || isMove) {
                return;
            }
            onDragItemClick(true, parent, view, position);
        }
    };

    private AdapterView.OnItemClickListener notSubscribeItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            if (isMove) {
                return;
            }
            onDragItemClick(false, parent, view, position);
        }
    };

    private void onDragItemClick(final boolean isSubscribe, AdapterView<?> parent, View view, final int position) {
        final ImageView moveImageView = DragViewUtil.getView(view, ColumnManagerActivity.this);
        if (moveImageView != null) {
            TextView newTextView = (TextView) view
                    .findViewById(R.id.text_item);
            final int[] startLocation = new int[2];
            newTextView.getLocationInWindow(startLocation);

            final ColumnEntity item;
            if (isSubscribe) {
                item = ((NoSubscribeAdapter) parent
                        .getAdapter()).getItem(position);
                noSubscribeAdapter.setVisible(false);
                noSubscribeAdapter.addItem(item);
            } else {
                item = ((SubScribeDragAdapter) parent
                        .getAdapter()).getItem(position);
                subscribeAdapter.setVisible(false);
                subscribeAdapter.addItem(item);
            }

            new Handler().postDelayed(new Runnable() {
                public void run() {
                    try {
                        int[] endLocation = new int[2];
                        if (isSubscribe) {
                            sgvNotSubscribe.getChildAt(sgvNotSubscribe.getLastVisiblePosition()).getLocationInWindow(endLocation);
                        } else {
                            gvSubscribe.getChildAt(gvSubscribe.getLastVisiblePosition()).getLocationInWindow(endLocation);
                        }

                        moveAnim(moveImageView, startLocation, endLocation, item, gvSubscribe);

                        if (isSubscribe) {
                            subscribeAdapter.setRemove(position);
                        } else {
                            noSubscribeAdapter.setRemove(position);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 50L);
        }
    }

    private void moveAnim(final View moveView, int[] startLocation, int[] endLocation, final ColumnEntity moveChannel, final GridView clickGridView) {
        final ViewGroup moveViewGroup = DragViewUtil.getMoveViewGroup(this);
        DragViewUtil.MoveAnim(moveView, startLocation, endLocation,
                moveChannel, sgvNotSubscribe, moveViewGroup, new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                        isMove = true;
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        moveViewGroup.removeView(moveView);
                        if (clickGridView instanceof DragGridView) {
                            noSubscribeAdapter.setVisible(true);
                            noSubscribeAdapter.notifyDataSetChanged();
                            subscribeAdapter.remove();
                        } else {
                            subscribeAdapter.setVisible(true);
                            subscribeAdapter.notifyDataSetChanged();
                            noSubscribeAdapter.remove();
                        }
                        isMove = false;
                    }
                }, this);
    }

    @Override
    public void onBackPressed() {
        if (isSave) {
            Intent data = new Intent();
            data.putParcelableArrayListExtra(ConstantUtil.PASS_VALUE, subscribeList);
            setResult(RESULT_OK, data);
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        columnService.onDestroy();
        super.onDestroy();
    }
}