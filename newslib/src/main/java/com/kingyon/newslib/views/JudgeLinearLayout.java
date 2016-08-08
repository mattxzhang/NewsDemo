package com.kingyon.newslib.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Leo on 2016/5/25
 */
public class JudgeLinearLayout extends LinearLayout {

    protected int max = 0;
    protected int up = 0;

    public JudgeLinearLayout(Context context) {
        super(context);
    }

    public JudgeLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JudgeLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if ( max != 0  && b > up && b == max){
            setVisibility(GONE);
        }
        max = Math.max(b,max);
        up = b;
    }
}
