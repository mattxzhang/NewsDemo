package com.kingyon.newslib.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kingyon.newslib.greendao.entities.ColumnEntity;
import com.kingyon.newslib.views.DragGridView;

/**
 * created by arvin on 16/8/4 14:17
 * email：1035407623@qq.com
 */
public class DragViewUtil {
    public static void MoveAnim(View moveView, int[] startLocation,int[] endLocation, final ColumnEntity moveChannel,
                          final GridView clickGridView,final ViewGroup moveViewGroup, Animation.AnimationListener listener,Activity activity) {
        int[] initLocation = new int[2];
        moveView.getLocationInWindow(initLocation);
        final View mMoveView = getMoveView(moveViewGroup, moveView,
                initLocation);
        TranslateAnimation moveAnimation = new TranslateAnimation(
                startLocation[0], endLocation[0], startLocation[1],
                endLocation[1]);
        moveAnimation.setDuration(300L);
        AnimationSet moveAnimationSet = new AnimationSet(true);
        moveAnimationSet.setFillAfter(false);
        moveAnimationSet.addAnimation(moveAnimation);
        mMoveView.startAnimation(moveAnimationSet);
        moveAnimationSet.setAnimationListener(listener);
    }

    /**
     * @param viewGroup
     * @param view
     * @param initLocation
     * @return
     */
    private static View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
        int x = initLocation[0];
        int y = initLocation[1];
        viewGroup.addView(view);
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mLayoutParams.leftMargin = x;
        mLayoutParams.topMargin = y;
        view.setLayoutParams(mLayoutParams);
        return view;
    }

    /**
     */
    public static ViewGroup getMoveViewGroup(Activity activity) {
        ViewGroup moveViewGroup = (ViewGroup) activity.getWindow().getDecorView();
        LinearLayout moveLinearLayout = new LinearLayout(activity);
        moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        moveViewGroup.addView(moveLinearLayout);
        return moveLinearLayout;
    }

    /**
     * @param view
     * @return
     */
    public static ImageView getView(View view,Activity activity) {
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        ImageView iv = new ImageView(activity);
        iv.setImageBitmap(cache);
        return iv;
    }
}
