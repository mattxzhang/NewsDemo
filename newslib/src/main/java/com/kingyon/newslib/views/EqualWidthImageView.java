package com.kingyon.newslib.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * created by arvin on 16/7/30 15:51
 * email：1035407623@qq.com
 */
public class EqualWidthImageView extends ImageView {
    public EqualWidthImageView(Context context) {
        super(context);
    }

    public EqualWidthImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EqualWidthImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
