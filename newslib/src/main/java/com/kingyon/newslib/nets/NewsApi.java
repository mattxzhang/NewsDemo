package com.kingyon.newslib.nets;

import com.kingyon.netlib.entitys.PageListEntity;
import com.kingyon.newslib.entities.CommentEntity;
import com.kingyon.newslib.greendao.entities.ColumnEntity;
import com.kingyon.newslib.greendao.entities.NewsEntity;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by arvin on 2016/7/29 15:53
 */
public interface NewsApi {
    @GET("cms/category/getCategory")
    Observable<PageListEntity<ColumnEntity>> getItems(@Query("page") int page,@Query("size") int size);

    @GET("cms/content/findCategoryContents")
    Observable<PageListEntity<NewsEntity>> getNews(@Query("categoryId") long categoryId, @Query("page") int page);

    @GET("cms/content/getDetails")
    Observable<NewsEntity> getNewsDetail(@Query("id") long objectId);

    @POST("cms/content/comment/ReplyComment")
    Observable<CommentEntity> sendReply(@Query("contentId") long objectId, @Query("parentCommentId") int parentCommentId,
                                        @Query("commentContent") String commentContent);

    @POST("cms/content/comment/publishContent")
    Observable<CommentEntity> sendComment(@Query("contentId") long objectId, @Query("commentContent") String commentContent);

    @GET("cms/content/social/favourate")
    Observable<Object> collection(@Query("targetId") long objectId);

    @GET("cms/content/social/cancelFavourite")
    Observable<Object> cancelCollection(@Query("targetId") long objectId);


}
