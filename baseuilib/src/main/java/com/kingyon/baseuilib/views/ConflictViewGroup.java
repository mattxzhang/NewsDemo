package com.kingyon.baseuilib.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * created by arvin on 16/8/7 22:39
 * email：1035407623@qq.com
 * 外部拦截发,使用时,需要父元素实现,只需将onInterceptTouchEvent按照需求重写isNeedIntercepting方法
 */
public abstract class ConflictViewGroup extends ViewGroup {
    private int mLatestX;
    private int mLatestY;

    public ConflictViewGroup(Context context) {
        super(context);
    }

    public ConflictViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ConflictViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLatestX;
                int deltaY = y - mLatestY;
                intercepted = isNeedIntercepting();
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
            default:
                break;
        }

        mLatestX = x;
        mLatestY = y;

        return intercepted;
    }

    protected abstract boolean isNeedIntercepting();
}
