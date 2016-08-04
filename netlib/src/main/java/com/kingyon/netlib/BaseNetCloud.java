package com.kingyon.netlib;

import android.text.TextUtils;
import android.util.Log;

import com.kingyon.netlib.utils.CertificateUtils;

import java.io.IOException;
import java.io.InputStream;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.CallAdapter;
import retrofit2.Converter;

/**
 * Created by arvin on 2016/7/29 15:41
 */
public abstract class BaseNetCloud {
    protected OkHttpClient okHttpClient;
    protected String token;
    protected String deviceId;
    private InputStream mCertificate;

    protected Converter.Factory converterFactory;
    protected CallAdapter.Factory rxJavaCallAdapterFactory;

    protected void initHttpClient() {
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request;
                    if (!TextUtils.isEmpty(token)) {
                        request = chain.request().newBuilder()
                                .addHeader("version", "1.0")
                                .addHeader("deviceId", deviceId)
                                .addHeader("token", token).build();
                    } else {
                        request = chain.request().newBuilder()
                                .addHeader("version", "1.0")
                                .addHeader("deviceId", deviceId).build();
                    }
                    Log.i("Dream", getReqInfo(request));
                    return chain.proceed(request);
                }
            });
            if (isNeedHttps()) {
                try {
                    if (mCertificate == null) {
                        throw new RuntimeException("please override setCertificateInputStream()");
                    }
                    SSLSocketFactory sslSocketFactory = CertificateUtils.getSSLSocketFactoryCertificate(mCertificate);
                    builder.socketFactory(sslSocketFactory);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            okHttpClient = builder.build();
        }
    }

    protected String getReqInfo(Request request) {
        return "[method:" + request.method() + ",url:" + request.url().toString() + "]";
    }

    /**
     * 若需要使用https请求,请设置证书信息
     */
    protected boolean isNeedHttps() {
        return false;
    }

    protected void setCertificateInputStream(InputStream certificate) {
        mCertificate = certificate;
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
