package com.kingyon.newslib.nets;

import com.kingyon.netlib.BaseNetCloud;
import com.kingyon.netlib.apis.BaseApi;
import com.kingyon.netlib.converters.ResponseConverterFactory;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by arvin on 2016/7/29 15:51
 */
public class NewsNetCloud extends BaseNetCloud {
    private static NewsNetCloud instance;
    private NewsApi newsApi;

    public static NewsNetCloud getInstance() {
        if (instance == null) {
            instance = new NewsNetCloud();
        }
        return instance;
    }

    private NewsNetCloud() {
        converterFactory = ResponseConverterFactory.create();
        rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
    }

    public NewsApi getNewsApi() {
        if (newsApi == null) {
            initHttpClient();
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BaseApi.API_SERVER)
                    .addConverterFactory(converterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            newsApi = retrofit.create(NewsApi.class);
        }
        return newsApi;
    }

    @Override
    protected void clear() {
        newsApi = null;
    }
}
