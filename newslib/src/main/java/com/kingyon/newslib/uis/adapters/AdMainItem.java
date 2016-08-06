package com.kingyon.newslib.uis.adapters;

import com.kingyon.baseuilib.utils.TimeUtil;
import com.kingyon.newslib.R;
import com.kingyon.newslib.greendao.entities.NewsEntity;
import com.kingyon.newslib.utils.NewsTypeUtil;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * created by arvin on 16/8/1 20:33
 * email：1035407623@qq.com
 */
public class AdMainItem implements ItemViewDelegate<NewsEntity> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.nl_item_ad_main;
    }

    @Override
    public boolean isForViewType(NewsEntity item, int position) {
        return item.getRealType().equals(NewsTypeUtil.AD_MAIN);
    }

    @Override
    public void convert(ViewHolder holder, NewsEntity newsEntity, int position) {
        holder.setText(R.id.tv_news_title,newsEntity.getTitle());
        holder.setText(R.id.tv_news_source,newsEntity.getRealSource());
        if (newsEntity.getContentSocail() != null) {
            holder.setText(R.id.tv_news_comment_count, newsEntity.getContentSocail().getCommentedCount() + "评论");
        }
        holder.setText(R.id.tv_news_time, TimeUtil.getRecentlyTime(newsEntity.getPublishTime()));

        holder.setVisible(R.id.tv_news_tag, true);
        holder.setText(R.id.tv_news_tag,"推广");

        if(newsEntity.getMainImage()!=null) {
            holder.setImage(R.id.img_news_main, newsEntity.getMainImage().getUrl());
        }
    }
}
