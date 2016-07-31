package com.zhy.adapter.recyclerview.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kingyon.baseuilib.R;

/**
 * Created by arvin on 2016/5/24
 */
public class GlideUtils {

    public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).centerCrop().placeholder(R.drawable.img_loading).error(R.drawable.img_default_avatar).into(imageView);
    }

    public static void loadCircleImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).centerCrop().placeholder(R.drawable.img_default_avatar).error(R.drawable.img_default_avatar).
                transform(new GlideCircleTransform(context)).into(imageView);
    }

    public static void loadLocalImage(Context context, int resId, ImageView imageView) {
        Glide.with(context).load(resId).centerCrop().placeholder(R.drawable.img_loading).error(R.drawable.img_loading).into(imageView);
    }

}
