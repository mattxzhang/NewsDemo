/*
Copyright 2015 Chanven

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.kingyon.refresh.loadmore;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kingyon.baseuilib.R;
import com.kingyon.baseuilib.views.MaterialProgressDrawable;

/**
 * default load more view
 */
public class DefaultLoadMoreFooter implements ILoadViewMoreFactory {

    @Override
    public ILoadMoreView madeLoadMoreView() {
        return new LoadMoreHelper();
    }

    private class LoadMoreHelper implements ILoadMoreView {

        protected TextView footerTv;
        protected ImageView footerBar;
        protected View footer;

        protected OnClickListener onClickRefreshListener;
        private MaterialProgressDrawable progress;

        @Override
        public void init(FootViewAdder footViewHolder, OnClickListener onClickRefreshListener) {
            View view = footViewHolder.addFootView(R.layout.ui_loadmore_default_footer);
            footerTv = (TextView) view.findViewById(R.id.loadmore_default_footer_tv);

            footerBar = (ImageView) view.findViewById(R.id.img_progress);
            progress = new MaterialProgressDrawable(footerBar.getContext(), footerBar);
            progress.setBackgroundColor(0xFFFAFAFA);
            progress.setAlpha(255);
            progress.setColorSchemeColors(footerBar.getContext().getResources().getColor(R.color.colorAccent));
            footerBar.setImageDrawable(progress);
            progress.start();

            footer = view.findViewById(R.id.layout_footer);
            this.onClickRefreshListener = onClickRefreshListener;
            showNormal();
        }

        @Override
        public void showNormal() {
            footerTv.setText("点击加载更多");
            footerBar.setVisibility(View.GONE);
            footer.setOnClickListener(onClickRefreshListener);
        }

        @Override
        public void showLoading() {
            footerTv.setText("正在加载中...");
            footerBar.setVisibility(View.VISIBLE);
            footer.setOnClickListener(null);
        }

        @Override
        public void showFail(Exception exception) {
            footerTv.setText("加载失败，点击重新");
            footerBar.setVisibility(View.GONE);
            footer.setOnClickListener(onClickRefreshListener);
        }

        @Override
        public void showNomore() {
            footerTv.setText("已经加载完毕");
            footerBar.setVisibility(View.GONE);
            footer.setOnClickListener(null);
        }

    }

}
