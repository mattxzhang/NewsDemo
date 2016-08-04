package com.kingyon.baseuilib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * created by arvin on 16/8/3 16:37
 * email：1035407623@qq.com
 */
public class CommonUtil {
    /**
     * 获取当前显示的Activity
     *
     * @return 当前Activity
     */
    public static Activity getCurrentActivity() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            Map activities = (Map) activitiesField.get(activityThread);
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    Activity activity = (Activity) activityField.get(activityRecord);
                    return activity;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param num
     * @param ws  要保留的位数
     * @return 默认保留一位小数
     */
    public static String saveFloat(float num, int ws) {
        ws = ws <= 0 ? 1 : ws;
        String temp = "#0.";
        for (int i = 0; i < ws; i++) {
            temp += "0";
        }
        DecimalFormat df = new DecimalFormat(temp);
        return df.format(num);
    }

    public static String getDeviceId(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void openBrowser(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    public static void startActivityWithAnim(Context mContext, Bundle data, Class target) {
        Intent intent = new Intent(mContext, target);
        if (data != null) {
            intent.putExtras(data);
        }
        mContext.startActivity(intent);
    }

    public static void startActivityForResultWithAnim(Activity activity, Bundle data, int requestCode, Class target) {
        Intent intent = new Intent(activity, target);
        if (data != null) {
            intent.putExtras(data);
        }
        activity.startActivityForResult(intent, requestCode);
    }
}
