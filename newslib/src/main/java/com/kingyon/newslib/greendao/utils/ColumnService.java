package com.kingyon.newslib.greendao.utils;

import android.content.Context;

import com.kingyon.netlib.entitys.PageListEntity;
import com.kingyon.newslib.greendao.entities.ColumnEntity;
import com.kingyon.newslib.greendao.entities.ColumnEntityDao;
import com.kingyon.newslib.nets.NewsNetCloud;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * created by arvin on 16/8/3 15:11
 * email：1035407623@qq.com
 */
public class ColumnService {
    private ColumnEntityDao columnEntityDao;
    private Context mContext;

    public ColumnService(Context context) {
        this.mContext = context;
        initColumnDao();
    }

    public void onDestroy() {
        mContext = null;
        columnEntityDao = null;
    }

    public ColumnEntityDao getColumnEntityDao() {
        if (columnEntityDao == null) {
            initColumnDao();
        }
        return columnEntityDao;
    }

    private void initColumnDao() {
        if (columnEntityDao != null) {
            return;
        }
        this.columnEntityDao = DaoUtils.getInstance().getSession(mContext).getColumnEntityDao();
    }

    public Observable<PageListEntity<ColumnEntity>> getColumns(final boolean hasLocalData) {
        return NewsNetCloud.getInstance().getNewsApi().getItems(0, 99)
                .map(new Func1<PageListEntity<ColumnEntity>, PageListEntity<ColumnEntity>>() {
                    @Override
                    public PageListEntity<ColumnEntity> call(PageListEntity<ColumnEntity> data) {
                        List<ColumnEntity> content = data.getContent();
                        if (!hasLocalData) {
                            insert(content);
                        } else {
                            updateAll(content);
                        }
                        return data;
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * @return 获取已订阅的资讯栏目
     */
    public List<ColumnEntity> getSubscribeList() {
        if (getColumnEntityDao() == null) {
            return new ArrayList<>();
        }
        return columnEntityDao.queryBuilder().where(ColumnEntityDao.Properties.IsOrder.eq(true))
                .orderAsc(ColumnEntityDao.Properties.ItemWeight).list();
    }

    /**
     * @return 获取未订阅的资讯栏目
     */
    public List<ColumnEntity> getNotSubscribeList() {
        if (getColumnEntityDao() == null) {
            return new ArrayList<>();
        }
        return columnEntityDao.queryBuilder().where(ColumnEntityDao.Properties.IsOrder.eq(false))
                .orderAsc(ColumnEntityDao.Properties.ItemWeight).list();
    }

    /**
     * 同步后台和客户端的数据
     *
     * @param content 后台数据
     */
    public boolean updateAll(List<ColumnEntity> content) {
        if (getColumnEntityDao() == null) {
            return false;
        }

        List<ColumnEntity> all = getAll();

        {//删除客户端多余的数据
            for (ColumnEntity local : all) {
                boolean isExit = false;
                for (ColumnEntity item : content) {
                    if (item.getObjectId().equals(local.getObjectId())) {
                        //TODO 后台会变的数据从这里去设置
                        local.setName(item.getName());
                        //无脑的更新一些可能会变的数据,不直接使用后台返回的数据是因为是否订阅和权重是本地定义的
                        update(local);
                        isExit = true;
                        break;
                    }
                }
                if (!isExit) {
                    delete(local);
                }
            }
        }

        {//新增客户端还没有的数据,这时候新增的数据都是未订阅的
            for (ColumnEntity entity : content) {
                boolean isNeedInsert = true;
                for (ColumnEntity item : all) {
                    if (item.getObjectId().equals(entity.getObjectId())) {
                        isNeedInsert = false;
                        break;
                    }
                }
                if (isNeedInsert) {
                    insert(entity);
                }
            }
        }
        return true;
    }

    /**
     * @return 获取所有资讯栏目
     */
    public List<ColumnEntity> getAll() {
        if (getColumnEntityDao() == null) {
            return new ArrayList<>();
        }

        return columnEntityDao.queryBuilder().list();
    }

    /**
     * @param entity 更新数据
     */
    public boolean update(ColumnEntity entity) {
        if (getColumnEntityDao() == null) {
            return false;
        }
        columnEntityDao.update(entity);
        return true;
    }

    /**
     * @param entity 插入数据
     */
    public boolean insert(ColumnEntity entity) {
        if (getColumnEntityDao() == null) {
            return false;
        }
        columnEntityDao.insertOrReplace(entity);
        return true;
    }

    /**
     * @param content 要插入的数据
     * @return 返回订阅的资讯栏目
     */
    public boolean insert(List<ColumnEntity> content) {
        if (getColumnEntityDao() == null) {
            return false;
        }
        List<ColumnEntity> orderList = new ArrayList<>();
        for (int i = 0; i < content.size(); i++) {
            ColumnEntity entity = content.get(i);
            if (isOrderEntity(entity, i)) {
                entity.setItemWeight(i + 1);
                entity.setIsOrder(true);
            } else {
                entity.setItemWeight(i - 13 + 1);
                entity.setIsOrder(false);
            }
            columnEntityDao.insertOrReplace(entity);
        }
        return true;
    }

    /**
     * @return 在没有数据, 插入时判断是否是订阅的, 不优雅, 应该后台给定一个字段来判断
     */
    public static boolean isOrderEntity(ColumnEntity entity, int pos) {
        return pos < 13;
    }

    /**
     * @param columnEntity 删除数据
     */
    public boolean delete(ColumnEntity columnEntity) {
        if (getColumnEntityDao() == null) {
            return false;
        }
        columnEntityDao.delete(columnEntity);
        return true;
    }

}
