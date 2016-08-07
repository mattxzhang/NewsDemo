package com.kingyon.baseuilib.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * created by arvin on 16/8/7 22:47
 * email：1035407623@qq.com
 * 内部拦截法,需要子元素实现,父元素需要重写
 * public boolean onInterceptTouchEvent(MotionEvent ev) {
 *     return ev.getAction() != MotionEvent.ACTION_DOWN;
 * }
 */
public abstract class ConflictView extends ViewGroup {
    private int mLatestX;
    private int mLatestY;

    public ConflictView(Context context) {
        super(context);
    }

    public ConflictView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ConflictView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLatestX;
                int deltaY = y - mLatestY;
                if (isNeedIntercepting()) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        mLatestX = x;
        mLatestY = y;

        return super.dispatchTouchEvent(event);
    }

    protected abstract boolean isNeedIntercepting();

}
