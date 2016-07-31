package com.kingyon.netlib.callback;

import android.app.Activity;
import android.content.Intent;
import android.net.ParseException;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.kingyon.netlib.exception.ApiException;
import com.kingyon.netlib.exception.ResultException;

import org.json.JSONException;

import java.lang.reflect.Field;
import java.util.Map;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by Leo on 2016/5/4
 */
public abstract class AbsAPICallback<T> extends Subscriber<T> {

    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    //出错提示
    private final String networkMsg = "服务器开小差";
    private final String parseMsg = "数据解析出错";
    private final String unknownMsg = "未知错误";

    protected AbsAPICallback() {
    }


    @Override
    public void onError(Throwable e) {
        Throwable throwable = e;
        //获取最根源的异常
        while (throwable.getCause() != null) {
            e = throwable;
            throwable = throwable.getCause();
        }

        ApiException ex;
        if (e instanceof HttpException) {             //HTTP错误
            HttpException httpException = (HttpException) e;
            ex = new ApiException(e, httpException.code());
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                    onPermissionError(ex);          //权限错误，需要实现
                    break;
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.setDisplayMessage(networkMsg);  //均视为网络错误
                    onError(ex);
                    break;
            }
        } else if (e instanceof ResultException) {    //服务器返回的错误
            ResultException resultException = (ResultException) e;
            ex = new ApiException(resultException, resultException.getErrCode());
            ex.setDisplayMessage(resultException.getMessage());
            if (ex.getCode() == ApiException.RE_LOGIN) {
                try {
                    Activity currentActivity = getCurrentActivity();
                    if (currentActivity != null) {
                        Intent intent = new Intent(currentActivity, Class.forName("com.kingyon.fanleme.reglogin.activitys.LoginActivity"));
                        currentActivity.startActivity(intent);
                        Toast.makeText(currentActivity, "请重新登录", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            onResultError(ex);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ApiException(e, ApiException.PARSE_ERROR);
            ex.setDisplayMessage(parseMsg);            //均视为解析错误
            onError(ex);
        } else {
            ex = new ApiException(e, ApiException.UNKOWN);
            ex.setDisplayMessage(unknownMsg);          //未知错误
            onError(ex);
        }
    }

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
     * http,解析错误回调
     */
    protected void onError(ApiException ex) {
        Log.e("Dream", ex.toString());
    }

    /**
     * 权限错误，需要实现重新登录操作
     */
    protected void onPermissionError(ApiException ex) {
        Log.e("Dream", ex.toString());
    }

    /**
     * 服务器返回的错误
     */
    protected abstract void onResultError(ApiException ex);

    @Override
    public void onCompleted() {
    }

}
