package com.kingyon.newslib.greendao.utils;

import android.content.Context;

import com.kingyon.netlib.callback.AbsAPICallback;
import com.kingyon.netlib.entitys.PageListEntity;
import com.kingyon.netlib.exception.ApiException;
import com.kingyon.newslib.greendao.entities.AttachmentEntity;
import com.kingyon.newslib.greendao.entities.AttachmentEntityDao;
import com.kingyon.newslib.greendao.entities.DaoMaster;
import com.kingyon.newslib.greendao.entities.NewsEntity;
import com.kingyon.newslib.greendao.entities.NewsEntityDao;
import com.kingyon.newslib.greendao.entities.SocialEntity;
import com.kingyon.newslib.greendao.entities.SocialEntityDao;
import com.kingyon.newslib.nets.NewsNetCloud;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * created by arvin on 16/8/5 14:02
 * email：1035407623@qq.com
 */
public class NewsService {
    private NewsEntityDao newsEntityDao;
    private Context mContext;

    public NewsService(Context context) {
        this.mContext = context;
        initNewsEntityDao();
    }

    public void onDestroy() {
        mContext = null;
        newsEntityDao = null;
    }

    public NewsEntityDao getNewsEntityDao() {
        if (newsEntityDao == null) {
            initNewsEntityDao();
        }
        return newsEntityDao;
    }

    private void initNewsEntityDao() {
        if (newsEntityDao != null) {
            return;
        }
        this.newsEntityDao = DaoUtils.getInstance().getSession(mContext).getNewsEntityDao();
    }

    public List<NewsEntity> getCacheNews(long columnId) {
        if (getNewsEntityDao() == null) {
            return new ArrayList<>();
        }
        List<NewsEntity> latestNews = newsEntityDao.queryBuilder().orderDesc(NewsEntityDao.Properties.PublishTime).
                where(NewsEntityDao.Properties.CategoryId.eq(columnId)).list();

        AttachmentEntityDao attachmentEntityDao = DaoUtils.getInstance().getSession(mContext).getAttachmentEntityDao();
        SocialEntityDao socialEntityDao = DaoUtils.getInstance().getSession(mContext).getSocialEntityDao();

        for (NewsEntity entity : latestNews) {
            //获取主图
            List<AttachmentEntity> mainImages = attachmentEntityDao.queryBuilder().where(AttachmentEntityDao.Properties.NewsId.eq(entity.getObjectId()),
                    AttachmentEntityDao.Properties.Type.eq(AttachmentEntity.TYPE_MAIN)).list();
            if (mainImages != null && mainImages.size() > 0) {
                entity.setMainImage(mainImages.get(0));
            }

            //获取图片列表
            List<AttachmentEntity> images = attachmentEntityDao.queryBuilder().where(AttachmentEntityDao.Properties.NewsId.eq(entity.getObjectId()),
                    AttachmentEntityDao.Properties.Type.eq(AttachmentEntity.TYPE_LIST)).list();
            entity.setImages(images);

            //获取社交信息
            List<SocialEntity> socials = socialEntityDao.queryBuilder().where(SocialEntityDao.Properties.NewsId.eq(entity.getObjectId())).list();
            if (socials != null && socials.size() > 0) {
                entity.setContentSocail(socials.get(0));
            }

        }
        return latestNews;
    }

    public boolean insert(List<NewsEntity> newses, long columnId) {
        if (getNewsEntityDao() == null) {
            return false;
        }
        AttachmentEntityDao attachmentEntityDao = DaoUtils.getInstance().getSession(mContext).getAttachmentEntityDao();
        SocialEntityDao socialEntityDao = DaoUtils.getInstance().getSession(mContext).getSocialEntityDao();
        for (NewsEntity entity : newses) {
            //插入主图
            AttachmentEntity mainImage = entity.getMainImage();
            if (mainImage != null && mainImage.getObjectId() != null) {
                mainImage.setType(AttachmentEntity.TYPE_MAIN);
                mainImage.setNewsId(entity.getObjectId());
                attachmentEntityDao.insertOrReplace(mainImage);
            }

            //插入图集
            if (entity.getImages() != null) {
                for (AttachmentEntity img : entity.getImages()) {
                    img.setType(AttachmentEntity.TYPE_LIST);
                    img.setNewsId(entity.getObjectId());
                    attachmentEntityDao.insertOrReplace(img);
                }
            }

            //插入社交对象
            SocialEntity contentSocial = entity.getContentSocail();
            if (contentSocial != null && contentSocial.getObjectId() != null) {
                contentSocial.setNewsId(entity.getObjectId());
                socialEntityDao.insertOrReplace(contentSocial);
            }
            entity.setCategoryId(columnId);
        }
        return true;
    }

    public boolean deleteAll(long columnId) {
        if (getNewsEntityDao() == null) {
            return false;
        }
        List<NewsEntity> allNews = newsEntityDao.queryBuilder().orderDesc(NewsEntityDao.Properties.PublishTime).
                where(NewsEntityDao.Properties.CategoryId.eq(columnId)).list();

        AttachmentEntityDao attachmentEntityDao = DaoUtils.getInstance().getSession(mContext).getAttachmentEntityDao();
        SocialEntityDao socialEntityDao = DaoUtils.getInstance().getSession(mContext).getSocialEntityDao();
        for (NewsEntity entity : allNews) {
            //删除该资讯的图片
            List<AttachmentEntity> attachments = attachmentEntityDao.queryBuilder().where(AttachmentEntityDao.Properties.NewsId.eq(entity.getObjectId())).list();
            if (attachments != null) {
                for (AttachmentEntity attachment : attachments) {
                    attachmentEntityDao.delete(attachment);
                }
            }

            //删除社交信息
            List<SocialEntity> socials = socialEntityDao.queryBuilder().where(SocialEntityDao.Properties.NewsId.eq(entity.getObjectId())).list();
            if (socials != null) {
                for (SocialEntity social : socials) {
                    socialEntityDao.delete(social);
                }
            }
        }
        return true;
    }

    public Observable<PageListEntity<NewsEntity>> getNews(final long columnId, final int page) {
        return NewsNetCloud.getInstance().getNewsApi().getNews(columnId, page)
                .map(new Func1<PageListEntity<NewsEntity>, PageListEntity<NewsEntity>>() {
                    @Override
                    public PageListEntity<NewsEntity> call(PageListEntity<NewsEntity> data) {
                        deleteAll(columnId);
                        insert(data.getContent(), columnId);
                        return data;
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
