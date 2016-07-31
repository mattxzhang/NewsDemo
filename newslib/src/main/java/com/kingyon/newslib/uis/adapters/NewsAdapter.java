package com.kingyon.newslib.uis.adapters;

import android.content.Context;

import com.kingyon.newslib.R;
import com.kingyon.newslib.entities.NewsEntity;
import com.kingyon.newslib.utils.NewsTypeUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by arvin on 2016/7/29 17:15
 */
public class NewsAdapter extends MultiItemTypeAdapter<NewsEntity> {
    public NewsAdapter(Context context, List<NewsEntity> datas) {
        super(context, datas);
        initMultiItem();
    }

    private void initMultiItem() {
        {//单图模块
            addItemViewDelegate(new SingleItem(R.layout.nl_item_single_left, NewsTypeUtil.SINGLE_LEFT));
            addItemViewDelegate(new SingleItem(R.layout.nl_item_single_right, NewsTypeUtil.SINGLE_RIGHT));
            addItemViewDelegate(new SingleItem(R.layout.nl_item_single_top, NewsTypeUtil.SINGLE_Top));
            addItemViewDelegate(new SingleItem(R.layout.nl_item_single_bottom, NewsTypeUtil.SINGLE_BOTTOM));
            addItemViewDelegate(new SingleItem(R.layout.nl_item_single_top, NewsTypeUtil.SINGLE_NONE));

        }

        {//多图模块
            addItemViewDelegate(new ManyThreeItem());
            addItemViewDelegate(new ManyBigItem());
        }

        {//视屏模块

        }

        {//广告模块

        }
    }

}
