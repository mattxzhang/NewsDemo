package com.kingyon.baseuilib.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.kingyon.baseuilib.entities.CommonEntity;

/**
 * Created by arvin on 2016/2/1 17:24.
 * 最基本的BaseActivity，其他所有Activity都需继承它，方便管理
 */
public abstract class BaseActivity extends AppCompatActivity{
    /**
     * 大多通用的数据都可以在这里边找到
     */
    protected CommonEntity mUtil;
    protected ProgressDialog progressDialog;
    protected final String FINISH_ALL_ACTION = "finish_all";
    protected final String FINISH_ACTION = "finish_app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewID());
        mUtil = new CommonEntity(this);
        registerQuitBroadcast(FINISH_ALL_ACTION);
        init(savedInstanceState);
    }

    @SuppressWarnings("unchecked")
    protected final <T extends View> T getView(int id) {
        try {
            return (T) findViewById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    protected final <T extends ViewGroup> T getLayout(int layoutResId) {
        return (T) mUtil.getInflater().inflate(layoutResId, null);
    }

    @Override
    protected void onDestroy() {
        mUtil.onDestory();
        unregisterQuitAllBroadcast();
        super.onDestroy();
    }

    public void showToast(String message) {
        mUtil.showToast(message);
    }

    public void showProgress(String message) {
        if (progressDialog != null) {
            progressDialog.setMessage(message != null ? message
                    : "处理中...");
            progressDialog.show();
        } else {
            progressDialog = new ProgressDialog(BaseActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.setMessage(message != null ? message
                    : "处理中...");
            progressDialog.show();
        }
    }

    public void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    protected abstract int getContentViewID();

    protected abstract void init(Bundle savedInstanceState);

    protected BroadcastReceiver finishAppReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };
    protected BroadcastReceiver finishAllReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    protected void registerQuitBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(FINISH_ACTION);
        this.registerReceiver(this.finishAppReceiver, filter);
    }

    protected void unregisterQuitBroadcast() {
        this.unregisterReceiver(this.finishAppReceiver);
    }

    protected void finishApp() {
        Intent intent = new Intent();
        intent.setAction(FINISH_ACTION);
        this.sendBroadcast(intent);
    }

    protected void registerQuitBroadcast(String action) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(action);
        this.registerReceiver(this.finishAllReceiver, filter);
    }

    protected void unregisterQuitAllBroadcast() {
        this.unregisterReceiver(this.finishAllReceiver);
    }

    protected void finishApp(String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        this.sendBroadcast(intent);
    }
}
