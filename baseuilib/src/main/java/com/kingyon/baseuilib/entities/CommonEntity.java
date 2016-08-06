package com.kingyon.baseuilib.entities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kingyon.baseuilib.utils.CommonUtil;

/**
 * Created by arvin on 2016/2/1 17:31.
 * 常用的变量集合
 */
@SuppressWarnings("all")
public class CommonEntity {
    public Context mContext;
    private Gson gson;
    private LayoutInflater mInflater;

    public CommonEntity(Activity activity) {
        this.mContext = activity;
    }

    public Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    public LayoutInflater getInflater() {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(mContext);
        }
        return mInflater;
    }

    public void onDestory() {
        mInflater = null;
        mContext = null;
    }

    public void startActivityWithAnim(Class target) {
        if (mContext instanceof Activity) {
            CommonUtil.startActivityWithAnim((Activity) mContext, null, target);
        }
    }

    public void startActivityWithAnim(Class target, Bundle data) {
        if (mContext instanceof Activity) {
            CommonUtil.startActivityWithAnim((Activity) mContext, data, target);
        }
    }

    public void startActivityForResultWithAnim(Class target, int requestCode) {
        if (mContext instanceof Activity) {
            CommonUtil.startActivityForResultWithAnim((Activity) mContext, null, requestCode, target);
        }
    }

    public void startActivityForResultWithAnim(Class target, Bundle data, int requestCode) {
        if (mContext instanceof Activity) {
            CommonUtil.startActivityForResultWithAnim((Activity) mContext, data, requestCode, target);
        }
    }

    public void showToast(String message) {
        if (message == null) {
            return;
        }
        if (mContext == null) {
            return;
        }
        if (message.length() < 8) {
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
        }
    }
}
