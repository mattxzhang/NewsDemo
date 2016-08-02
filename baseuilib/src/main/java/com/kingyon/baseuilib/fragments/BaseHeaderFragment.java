package com.kingyon.baseuilib.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kingyon.baseuilib.R;
import com.kingyon.baseuilib.activities.BaseActivity;


/**
 * Created by arvin on 2016/2/3 14:27.
 * 使用这个类，需要自己引入header，且header里的id需要和下边初始化view里的一样
 */
public abstract class BaseHeaderFragment extends BaseFragment {
    protected TextView tvHeaderTitle;
    protected View vHeaderLeft;

    @Override
    protected void init(Bundle savedInstanceState) {
        initHeader();
    }

    protected void initHeader() {
        try {
            initHeaderView();
            initHeaderEvent();
        } catch (Exception e) {
            Log.e("exception", "never set header");
        }
    }

    protected void initHeaderView() {
        tvHeaderTitle = getView(R.id.pre_tv_header_title);
        vHeaderLeft = getView(R.id.pre_v_header_left);
        tvHeaderTitle.setText(getTitleText());
        if (!isShowLeftView()) {
            vHeaderLeft.setVisibility(View.GONE);
        }
        setLeftDrawable();
    }

    protected void initHeaderEvent() {
        vHeaderLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLeftClicked();
            }
        });
    }

    //默认直接关闭
    protected void onLeftClicked() {
        getActivity().onBackPressed();
    }

    protected boolean isShowLeftView() {
        return true;
    }

    protected abstract String getTitleText();

    public void setLeftDrawable() {
//        Drawable wrappedDrawable = DrawableCompat.wrap(getResources().getDrawable(R.drawable.nav_back));
//        DrawableCompat.setTint(wrappedDrawable, Color.WHITE);
//        wrappedDrawable.setBounds(0, 0, wrappedDrawable.getMinimumWidth(), wrappedDrawable.getMinimumHeight());
//        ((TextView) vHeaderLeft).setCompoundDrawables(wrappedDrawable, null, null, null);
    }

}
