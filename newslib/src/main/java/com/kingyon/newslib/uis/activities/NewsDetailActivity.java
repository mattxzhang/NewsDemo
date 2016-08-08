package com.kingyon.newslib.uis.activities;

import android.os.Bundle;

import com.kingyon.baseuilib.activities.BaseActivity;
import com.kingyon.baseuilib.activities.BaseSwipeBackActivity;
import com.kingyon.baseuilib.fragments.BaseFragment;
import com.kingyon.baseuilib.utils.ConstantUtil;
import com.kingyon.newslib.R;
import com.kingyon.newslib.greendao.entities.NewsEntity;
import com.kingyon.newslib.uis.fragments.ArticleNewsFragment;

/**
 * Created by arvin on 2016/8/8 10:45
 */
public class NewsDetailActivity extends BaseSwipeBackActivity {
    //默认
    public static final int ARTICLE = 0;
    public static final int IMAGES = 1;
    public static final int VIDEO = 2;
    private int type = 0;
    private NewsEntity newsEntity;

    @Override
    protected int getContentViewID() {
        return R.layout.nl_activity_news_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        if (getIntent().getExtras() != null) {
            type = getIntent().getExtras().getInt(ConstantUtil.PASS_EXTRA, 0);
            newsEntity = getIntent().getExtras().getParcelable(ConstantUtil.PASS_VALUE);
        }
        switchToFragment();
    }

    @Override
    protected String getTitleText() {
        return "";
    }

    private void switchToFragment() {
        BaseFragment fragment = new ArticleNewsFragment(newsEntity);
        switch (type) {
            case IMAGES:
                break;
            case VIDEO:
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, fragment).commit();
    }
}
