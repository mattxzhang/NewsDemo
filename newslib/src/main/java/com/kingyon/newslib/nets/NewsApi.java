package com.kingyon.newslib.nets;

import com.kingyon.netlib.entitys.PageListEntity;
import com.kingyon.newslib.greendao.entities.ColumnEntity;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by arvin on 2016/7/29 15:53
 */
public interface NewsApi {
    @GET("cms/category/getCategory")
    Observable<PageListEntity<ColumnEntity>> getItems(@Query("page") int page,@Query("size") int size);
}
