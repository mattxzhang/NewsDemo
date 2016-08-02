package com.kingyon.baseuilib.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.kingyon.baseuilib.listeners.IWeakHandler;

import java.lang.ref.WeakReference;

/**
 * created by arvin on 16/8/2 17:17
 * email：1035407623@qq.com
 */
public class WeakHandler extends Handler {
    private WeakReference<IWeakHandler> mActivity;

    public WeakHandler(IWeakHandler activity) {
        this.mActivity = new WeakReference<>(activity);
    }

    public WeakHandler(Looper looper,IWeakHandler activity){
        super(looper);
        this.mActivity = new WeakReference<>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (mActivity != null) {
            mActivity.get().handleMessage(msg);
        }
    }
}
