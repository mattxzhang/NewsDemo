package com.kingyon.newslib.views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kingyon.baseuilib.utils.ScreenUtil;
import com.kingyon.newslib.R;
import com.zhy.adapter.recyclerview.utils.GlideUtils;

/**
 * created by arvin on 16/7/30 22:16
 * email：1035407623@qq.com
 */
public class ThreeImageLayout extends LinearLayout {
    private EqualWidthImageView img1;
    private EqualWidthImageView img2;
    private EqualWidthImageView img3;


    public ThreeImageLayout(Context context) {
        this(context, null);
    }

    public ThreeImageLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThreeImageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.HORIZONTAL);

        img1 = new EqualWidthImageView(getContext());
        img1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        img1.setBackgroundResource(R.drawable.img_loading);
        addView(img1, getParams(false));

        img2 = new EqualWidthImageView(getContext());
        img2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        img2.setBackgroundResource(R.drawable.img_loading);
        addView(img2, getParams(true));

        img3 = new EqualWidthImageView(getContext());
        img3.setScaleType(ImageView.ScaleType.CENTER_CROP);
        img3.setBackgroundResource(R.drawable.img_loading);
        addView(img3, getParams(false));
    }

    private LinearLayout.LayoutParams getParams(boolean isAddMargin) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        if (isAddMargin) {
            int margin = ScreenUtil.dp2px(8);
            params.setMargins(margin, 0, margin, 0);
        }
        return params;
    }

    public void setImages(String url1, String url2, String url3) {
        int goneCount = 0;
        goneCount += setImage(img1, url1);
        goneCount += setImage(img2, url2);
        goneCount += setImage(img3, url3);
        if(goneCount==3){
            setVisibility(View.GONE);
        }else{
            setVisibility(View.VISIBLE);
        }
    }

    /**
     * @param img  显示的View
     * @param url1 显示的url
     * @return 返回是未显示的数量
     */
    private int setImage(EqualWidthImageView img, String url1) {
        if (TextUtils.isEmpty(url1)) {
            img.setVisibility(View.INVISIBLE);
            return 1;
        } else {
            img.setVisibility(View.VISIBLE);
            GlideUtils.loadImage(getContext(), url1, img);
            return 0;
        }
    }
}
