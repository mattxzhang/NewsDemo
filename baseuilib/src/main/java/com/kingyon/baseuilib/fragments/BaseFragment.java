package com.kingyon.baseuilib.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kingyon.baseuilib.entities.CommonEntity;

/**
 * Created by arvin on 2016/7/29 16:19
 */
public abstract class BaseFragment extends Fragment {
    /**
     * 大多通用的数据都可以在这里边找到
     */
    protected CommonEntity mUtil;
    protected View mRoot;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUtil = new CommonEntity(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = mUtil.getInflater().inflate(getContentViewID(), null);
        init(savedInstanceState);
        return mRoot;
    }

    @SuppressWarnings("unchecked")
    protected final <T extends View> T getView(int id) {
        try {
            return (T) mRoot.findViewById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    protected final <T extends ViewGroup> T getLayout(int layoutResId) {
        return (T) mUtil.getInflater().inflate(layoutResId, null);
    }

    public void showToast(String message) {
        mUtil.showToast(message);
    }

    public void onRestart() {
    }

    @Override
    public void onDestroyView() {
        mUtil.onDestory();
        super.onDestroyView();
    }

    protected abstract int getContentViewID();

    protected abstract void init(Bundle savedInstanceState);
}
