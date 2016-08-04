package com.kingyon.newsdemo;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.kingyon.baseuilib.utils.CommonUtil;
import com.kingyon.newslib.nets.NewsNetCloud;

/**
 * created by arvin on 16/8/3 16:34
 * email：1035407623@qq.com
 */
public class App extends MultiDexApplication {
    private static App instance;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        NewsNetCloud.getInstance().setDeviceId(CommonUtil.getDeviceId(this));
    }

    public static App getInstance() {
        return instance;
    }
}
