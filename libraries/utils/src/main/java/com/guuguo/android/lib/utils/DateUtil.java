package com.guuguo.android.lib.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by user on 2017-02-19.
 */

public class DateUtil {
    private DateUtil(){}
    public static long toDate(String format, String dateStr) {
        try {
            return new SimpleDateFormat(format).parse(dateStr).getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    public static String fromDate(String format, long dateTime) {
        dateTime = getS2MsDateTime(dateTime);
        return new SimpleDateFormat(format).format(dateTime);
    }

    /**
     * 获取秒为单位时间戳的离现在多久
     *
     * @param format
     * @param dateTime
     * @return
     */
    public static String getTimeSpanSecond(String format, long dateTime) {
        long msTime = getS2MsDateTime(dateTime);
        return getTimeSpan(format, msTime);
    }

    /**
     * 获取毫秒为单位时间戳的离现在多久
     *
     * @param format
     * @param dateTime
     * @return
     */
    public static String getTimeSpan(String format, long dateTime) {
        long timeSpan = System.currentTimeMillis() - dateTime;
        timeSpan = timeSpan / 1000;
        if (timeSpan < 60) {
            return timeSpan + "秒前";
        }
        timeSpan /= 60;
        if (timeSpan < 60) {
            return timeSpan + "分钟前";
        }
        timeSpan /= 60;
        if (timeSpan < 24) {
            return timeSpan + "小时前";
        }
        timeSpan /= 24;
        if (timeSpan < 7) {
            return timeSpan + "天前";
        }
        timeSpan /= 7;
        if (timeSpan < 4) {
            return timeSpan + "周前";
        }
        return fromDate(format, dateTime);
    }

    public static long getS2MsDateTime(long dateTime) {
        return dateTime * 1000;
    }

    public static long getMs2SecondDateTime(long dateTime) {
        return dateTime / 1000;
    }
}
