package com.kingyon.baseuilib.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by arvin on 2016/2/2 16:40.
 * 时间转换相关方法
 */
@SuppressWarnings("ALL")
public class TimeUtil {
    private static final long minute = 60 * 1000; //分钟
    private static final long hour = 60 * minute; //小时
    private static final long day = 24 * hour;    //天
    private static final long week = 7 * day;     //周
    private static final long month = 31 * day;   //月
    private static final long year = 12 * month;  //年

    public static final String TIME_ALL= "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_YMD= "yyyy-MM-dd";
    public static final String TIME_MD= "MM-dd";
    public static final String TIME_HM= "HH:mm";

    public static long getCurrentTime() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static String getTimeByFormat(long time,String formatStrng){
        SimpleDateFormat format = new SimpleDateFormat(formatStrng);
        Date now = new Date(time);
        return format.format(now);
    }

    /**
     * 将time转换为 1970-1-1 00:00 格式的时间
     */
    public static String getAllTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat(TIME_ALL);
        Date now = new Date(time);
        return format.format(now);
    }

    /**
     * 将time转换为 1970-1-1 格式的时间
     */
    public static String getYMdTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat(TIME_YMD);
        Date now = new Date(time);
        return format.format(now);
    }

    /**
     * 将time转换为 1-1 格式的时间
     */
    public static String getMDTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat(TIME_MD);
        Date now = new Date(time);
        return format.format(now);
    }

    /**
     * 将time转换为 00:00 格式的时间
     */
    public static String getHmTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat(TIME_HM);
        Date now = new Date(time);
        return format.format(now);
    }

    /**
     * 获取最近时间根据差值确定时间显示的样式
     */
    public static String getRecentlyTime(long time) {
        if (time <= 0) {
            return null;
        }
        long diff = new Date().getTime() - time;
        long r = 0;
        if (diff > year) {
            return getYMdTime(time);
        }
        if (diff > day) {
            if (diff - day >= 1&&diff-day<2) {
                return "昨天";
            }
            return getMDTime(time);
        }
        if (diff > hour) {
            if (diff - hour <= 3) {
                return getHmTime(time);
            }
            r = (diff / hour);
            return r + "小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";
    }

    /**
     * 获取年纪
     */
    public static int getAge(long time) {
        if (time <= 0) {
            return 0;
        }
        Date birthday = new Date();
        birthday.setTime(time);
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthday)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthday);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                age--;
            }
        }
        return age;
    }
}
