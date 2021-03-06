package com.kingyon.newslib.uis.adapters;

import com.kingyon.baseuilib.utils.TimeUtil;
import com.kingyon.newslib.R;
import com.kingyon.newslib.greendao.entities.AttachmentEntity;
import com.kingyon.newslib.greendao.entities.NewsEntity;
import com.kingyon.newslib.utils.NewsTypeUtil;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * created by arvin on 16/7/30 22:51
 * email：1035407623@qq.com
 */
public class ManyBigItem implements ItemViewDelegate<NewsEntity> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.nl_item_many_big;
    }

    @Override
    public boolean isForViewType(NewsEntity item, int position) {
        return item.getRealType().equals(NewsTypeUtil.MULTI_MAIN_ATLAS);
    }

    @Override
    public void convert(ViewHolder holder, NewsEntity newsEntity, int position) {
        holder.setText(R.id.tv_news_title, newsEntity.getTitle());
        holder.setText(R.id.tv_news_source, newsEntity.getRealSource());
        if (newsEntity.getContentSocail() != null) {
            holder.setText(R.id.tv_news_comment_count, newsEntity.getContentSocail().getCommentedCount() + "评论");
        }
        holder.setText(R.id.tv_news_time, TimeUtil.getRecentlyTime(newsEntity.getPublishTime()));
        holder.setVisible(R.id.tv_news_tag, true);

        List<AttachmentEntity> images = newsEntity.getImages();
        if(images!=null&&images.size()>=1) {
            holder.setVisible(R.id.layout_images,true);
            holder.setImage(R.id.img_news_main, images.get(0).getUrl());
            holder.setText(R.id.tv_images_count, images.size() + "张");
        }else{
            holder.setVisible(R.id.layout_images,false);
        }
    }
}
