package com.kingyon.newslib.greendao.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.kingyon.baseuilib.utils.CommonUtil;
import com.kingyon.netlib.utils.ActivityUtil;
import com.kingyon.newslib.greendao.entities.DaoMaster;
import com.kingyon.newslib.greendao.entities.DaoSession;

/**
 * created by arvin on 16/8/3 14:55
 * email：1035407623@qq.com
 */
public class DaoUtils {
    private DaoSession daoSession;
    public static DaoUtils instance;

    private DaoUtils() {
    }

    public static DaoUtils getInstance() {
        if (instance == null) {
            instance = new DaoUtils();
        }
        return instance;
    }

    public DaoSession getSession(Context context) {
        if (daoSession == null && context != null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context.getApplicationContext(), "news-db", null);
            SQLiteDatabase db = helper.getWritableDatabase();
            DaoMaster daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }
}
