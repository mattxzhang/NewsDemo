package com.kingyon.newslib.uis.adapters;

import com.kingyon.baseuilib.utils.TimeUtil;
import com.kingyon.newslib.R;
import com.kingyon.newslib.entities.NewsEntity;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import rx.Single;

/**
 * Created by arvin on 2016/7/29 17:35
 */
public class SingleItem implements ItemViewDelegate<NewsEntity> {
    private int layoutId;
    private String matchType;

    public SingleItem(int layoutId, String matchType) {
        this.layoutId = layoutId;
        this.matchType = matchType;
    }

    @Override
    public int getItemViewLayoutId() {
        return layoutId;
    }

    @Override
    public boolean isForViewType(NewsEntity item, int position) {
        return item.getType().equals(matchType);
    }

    @Override
    public void convert(ViewHolder holder, NewsEntity newsEntity, int position) {
        holder.setText(R.id.tv_news_title,newsEntity.getTitle());
        holder.setText(R.id.tv_news_source,newsEntity.getSource());
        holder.setText(R.id.tv_news_comment_count,newsEntity.getCommentCount()+"评论");
        holder.setText(R.id.tv_news_time, TimeUtil.getRecentlyTime(newsEntity.getTime()));

        int mainImgId = R.id.img_news_main;
        if(newsEntity.getMainImg()!=null) {
            holder.setVisible(mainImgId,false);
            holder.setImage(mainImgId, newsEntity.getMainImg());
        }else{
            holder.setVisible(mainImgId,true);
        }
    }
}
