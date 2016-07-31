package com.kingyon.netlib;

import android.util.Log;

import com.kingyon.netlib.converters.ResponseConverterFactory;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by arvin on 2016/7/29 15:41
 */
public abstract class BaseNetCloud {
    protected OkHttpClient okHttpClient;
    protected String token;
    protected String deviceId;

    protected Converter.Factory converterFactory;
    protected CallAdapter.Factory rxJavaCallAdapterFactory;

    protected void initHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request;
                    if (token != null && token.length() > 10) {
                        request = chain.request().newBuilder()
                                .addHeader("version", "1.0")
                                .addHeader("deviceId", deviceId)
                                .addHeader("token", token).build();
                    } else {
                        request = chain.request().newBuilder()
                                .addHeader("deviceId", deviceId)
                                .addHeader("version", "1.0").build();
                    }
                    Log.i("Dream", getReqInfo(request));
                    return chain.proceed(request);
                }
            }).build();
        }
    }

    protected String getReqInfo(Request request) {
        return "[method:" + request.method() + ",url:" + request.url().toString() + "]";
    }

    protected void baseClear() {
        okHttpClient = null;
        clear();
    }

    public void setToken(String token) {
        this.token = token;
        baseClear();
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    protected abstract void clear();

}
