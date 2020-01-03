package com.guuguo.android.lib.utils;

import android.os.Looper;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by guodeqing on 7/24/16.
 */

public class CommonUtil {
    public static final String NOT_FOUND_VAL = "unknown";

    //============================集合========================================
    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public static int log(int n) {
        int a = 0;
        while (n % 2 == 0) {
            if (n % 2 != 0) {
                return -1;
            }
            n = n / 2;
            a++;
        }
        return a;
    }


    //============================字符串============================================
    public static boolean isEmpty(String s) {
        return TextUtils.isEmpty(s);
    }

    /**
     * 只encode中文
     */
    public static final Pattern ENCODE_PATTERN = Pattern.compile("[\\u4e00-\\u9fa5]+");
    public static String encode(String sourceString) {
        Pattern pattern = ENCODE_PATTERN;
        Matcher matcher = pattern.matcher(sourceString);
        while (matcher.find()) {
            String s1 = matcher.group();
            sourceString = sourceString.replace(s1, urlEncode(s1));
        }

        return sourceString;
    }


    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return URLEncoder.encode(s);
        }
    }

    public static String urlDecode(String s) {
        try {
            return URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return URLDecoder.decode(s);
        }
    }

    //============================ELSE============================================

    //生存uuid
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    //判断是否在主线程
    public boolean isUIThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }


    /**
     * 现在的几分钟前
     *
     * @param date
     * @return
     */
    public static int getMinutesBefore(Date date) {
        return (int) (System.currentTimeMillis() - date.getTime()) / 1000 / 60;
    }

    public static int getMinutesBefore(Long date) {
        return (int) (System.currentTimeMillis() - date) / 1000 / 60;
    }

    //如果字符串为空，返回“”
    public static String getSafeString(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        } else {
            return str;
        }
    }

    //如果列表为null，返回空链表
    public static List getSafeList(List list) {
        if (list == null) {
            return new ArrayList<>();
        } else {
            return list;
        }
    }

    //返回数字字符串，如果等于整数返回不保留的整数，不等于保留retain位
    public static String getGoodFloatString(float num, int retain) {
        if (num == (int) num) {
            return (int) num + "";
        } else {
            return getDoubleString(num, retain);
        }
    }

    // double2 string boliu retain位小数
    public static String getDoubleString(double num, int retain) {
        return String.format("%." + retain + "f", num);
    }



    /**
     * 如果是空，返回unknown
     *
     * @param data the data
     * @return the string
     */
   public static String checkValidData(final String data) {
        String tempData = data;
        if (TextUtils.isEmpty(data)) {
            tempData = NOT_FOUND_VAL;
        }
        return tempData;
    }

    /**
     * 空格换为——
     *
     * @param result the result
     * @return the string
     */
    static String handleIllegalCharacterInResult(final String result) {
        String tempResult = result;
        if (tempResult != null && tempResult.contains(" ")) {
            tempResult = tempResult.replaceAll(" ", "_");
        }
        return tempResult;
    }
   
}
