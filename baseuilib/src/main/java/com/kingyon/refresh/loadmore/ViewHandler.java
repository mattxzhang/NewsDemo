package com.kingyon.refresh.loadmore;

import android.view.View;
import android.view.View.OnClickListener;

import com.kingyon.refresh.loadmore.ILoadViewMoreFactory.ILoadMoreView;

public interface ViewHandler {

	/**
	 * 
	 * @param loadMoreView
	 * @return 是否有 init ILoadMoreView
	 */
	public boolean handleSetAdapter(View contentView, ILoadMoreView loadMoreView, OnClickListener onClickLoadMoreListener);

	public void setOnScrollBottomListener(View contentView, OnScrollBottomListener onScrollBottomListener);

}
