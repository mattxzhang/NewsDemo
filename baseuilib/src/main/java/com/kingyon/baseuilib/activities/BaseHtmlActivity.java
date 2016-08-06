package com.kingyon.baseuilib.activities;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.kingyon.baseuilib.R;
import com.kingyon.baseuilib.views.ProgressWebView;


/**
 * Created by arvin on 2016/2/16 14:32
 * .
 */
public abstract class BaseHtmlActivity extends BaseSwipeBackActivity {
    private final String UP_FILE_TAG = "UP_FILE";
    public static final String TITLE = "title";
    public static final String URL = "url";
    protected ProgressWebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWebView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        mWebView = getView(R.id.pre_web_view);

        final WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setDomStorageEnabled(true);
        settings.setGeolocationEnabled(true);

        mWebView.setWebViewClient(getWebClient());
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    protected abstract WebViewClient getWebClient();

    public class UIWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i("WebView", "shouldOverrideUrlLoading URL" + url);
            mWebView.loadUrl(url);
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        /**
         * 若访问https的网页需要重写下边两个方法
         */
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

    /**
     * webView上传文件需要重写如下几个方法
     */
    protected class UIWebChromeClient extends WebChromeClient {
        /**
         * For Android 3.0+
         */
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            Log.i(UP_FILE_TAG, "in openFile Uri Callback");
            openFileChoose(uploadMsg, "");
        }

        /**
         * For Android 3.0+
         */
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            Log.i(UP_FILE_TAG, "in openFile Uri Callback has accept Type" + acceptType);
            openFileChoose(uploadMsg, acceptType);
        }

        /**
         * For Android 4.1
         */
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            Log.i(UP_FILE_TAG, "in openFile Uri Callback has accept Type" + acceptType + "has capture" + capture);
            openFileChoose(uploadMsg, acceptType);
        }

        //Android 5.0+
        @Override
        @SuppressLint("NewApi")
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            return true;
        }
    }

    /**
     * 这个在要用到打开文件时，需子View实现，打开文件选择器，
     */
    protected void openFileChoose(ValueCallback<Uri> uploadMsg, String acceptType) {
    }

}
