package com.kingyon.newslib.uis.adapters;

import android.view.View;

import com.kingyon.baseuilib.utils.TimeUtil;
import com.kingyon.newslib.R;
import com.kingyon.newslib.entities.NewsEntity;
import com.kingyon.newslib.utils.NewsTypeUtil;
import com.kingyon.newslib.views.ThreeImageLayout;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * created by arvin on 16/8/1 20:33
 * email：1035407623@qq.com
 */
public class AdThreeItem implements ItemViewDelegate<NewsEntity> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.nl_item_ad_three;
    }

    @Override
    public boolean isForViewType(NewsEntity item, int position) {
        return item.getType().equals(NewsTypeUtil.AD_THREE);
    }

    @Override
    public void convert(ViewHolder holder, NewsEntity newsEntity, int position) {
        holder.setText(R.id.tv_news_title, newsEntity.getTitle());
        holder.setText(R.id.tv_news_source, newsEntity.getSource());
        holder.setText(R.id.tv_news_comment_count, newsEntity.getCommentCount() + "评论");
        holder.setText(R.id.tv_news_time, TimeUtil.getRecentlyTime(newsEntity.getTime()));

        holder.setVisible(R.id.tv_news_tag, true);
        holder.setText(R.id.tv_news_tag,"推广");

        setImages(holder, newsEntity);
    }

    private void setImages(ViewHolder holder, NewsEntity newsEntity) {
        ThreeImageLayout threeImageLayout = holder.getView(R.id.layout_images);
        List<String> images = newsEntity.getImages();
        if (images != null) {
            threeImageLayout.setVisibility(View.VISIBLE);
            if (images.size() >= 3) {
                threeImageLayout.setImages(images.get(0), images.get(1), images.get(2));
            } else if (images.size() == 2) {
                threeImageLayout.setImages(images.get(0), images.get(1), null);
            } else if (images.size() == 1) {
                threeImageLayout.setImages(images.get(0), null, null);
            } else {
                threeImageLayout.setImages(null, null, null);
            }
        } else {
            threeImageLayout.setVisibility(View.GONE);
        }
    }
}
