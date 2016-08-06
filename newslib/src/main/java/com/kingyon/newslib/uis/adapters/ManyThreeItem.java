package com.kingyon.newslib.uis.adapters;

import android.view.View;

import com.kingyon.baseuilib.utils.TimeUtil;
import com.kingyon.newslib.R;
import com.kingyon.newslib.greendao.entities.AttachmentEntity;
import com.kingyon.newslib.greendao.entities.NewsEntity;
import com.kingyon.newslib.utils.NewsTypeUtil;
import com.kingyon.newslib.views.ThreeImageLayout;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * created by arvin on 16/7/30 22:51
 * email：1035407623@qq.com
 */
public class ManyThreeItem implements ItemViewDelegate<NewsEntity> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.nl_item_many_three;
    }

    @Override
    public boolean isForViewType(NewsEntity item, int position) {
        return item.getRealType().equals(NewsTypeUtil.MULTI_THREE_ARTICLE) ||
                item.getRealType().equals(NewsTypeUtil.MULTI_THREE_ATLAS);
    }

    @Override
    public void convert(ViewHolder holder, NewsEntity newsEntity, int position) {
        holder.setText(R.id.tv_news_title, newsEntity.getTitle());
        holder.setText(R.id.tv_news_source, newsEntity.getRealSource());
        if (newsEntity.getContentSocail() != null) {
            holder.setText(R.id.tv_news_comment_count, newsEntity.getContentSocail().getCommentedCount() + "评论");
        }
        holder.setText(R.id.tv_news_time, TimeUtil.getRecentlyTime(newsEntity.getPublishTime()));

        if (newsEntity.getType().equals(NewsTypeUtil.MULTI_THREE_ATLAS)) {
            holder.setVisible(R.id.tv_news_tag, true);
        } else {
            holder.setVisible(R.id.tv_news_tag, false);
        }

        setImages(holder, newsEntity);
    }

    private void setImages(ViewHolder holder, NewsEntity newsEntity) {
        ThreeImageLayout threeImageLayout = holder.getView(R.id.layout_images);
        List<AttachmentEntity> images = newsEntity.getImages();
        if (images != null) {
            threeImageLayout.setVisibility(View.VISIBLE);
            if (images.size() >= 3) {
                threeImageLayout.setImages(images.get(0).getUrl(), images.get(1).getUrl(), images.get(2).getUrl());
            } else if (images.size() == 2) {
                threeImageLayout.setImages(images.get(0).getUrl(), images.get(1).getUrl(), null);
            } else if (images.size() == 1) {
                threeImageLayout.setImages(images.get(0).getUrl(), null, null);
            } else {
                threeImageLayout.setImages(null, null, null);
            }
        } else {
            threeImageLayout.setVisibility(View.GONE);
        }
    }
}
