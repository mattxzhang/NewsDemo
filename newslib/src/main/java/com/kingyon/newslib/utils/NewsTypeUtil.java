package com.kingyon.newslib.utils;

/**
 * Created by arvin on 2016/7/29 17:19
 * 默认都进入文章详情
 */
public class NewsTypeUtil {
    /**
     * 单图
     */
    //单左
    public static final String SINGLE_LEFT =" 100";
    //单右
    public static final String SINGLE_RIGHT =" 101";
    //单上
    public static final String SINGLE_TOP =" 102";
    //单下
    public static final String SINGLE_BOTTOM =" 103";
    //单无
    public static final String SINGLE_NONE =" 104";

    /**
     * 多图
     */
    //显示3张图
    public static final String MULTI_THREE_ARTICLE =" 200";
    //显示3张图，并进入图集详情
    public static final String MULTI_THREE_ATLAS =" 201";
    //显示1张图，并进入图集详情
    public static final String MULTI_MAIN_ATLAS =" 202";

    /**
     * 视频
     */
    //显示视频主图，并进入视频详情
    public static final String VIDEO_MAIN =" 300";

    /**
     * 广告
     */
    //显示一张图，类似于<单下>，并进入视频详情
    public static final String AD_MAIN =" 400";
    //显示三张图，类似于多图显示3张图，并进入视频详情
    public static final String AD_THREE =" 401";
}
