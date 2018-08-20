package com.guuguo.gank.util;

import android.os.Looper;
import android.text.TextUtils;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
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

    //============================集合========================================
    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }


    public static String md5(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                } else {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }
            return strBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    //============================字符串============================================
    public static boolean isEmpty(String s) {
        return TextUtils.isEmpty(s);
    }

    /**
     * The file copy buffer size (30 MB)
     */
    private static final long FILE_COPY_BUFFER_SIZE = 1024 * 1024 * 30;

    /**
     * Internal copy file method.
     *
     * @param srcFile          the validated source file, must not be {@code null}
     * @param destFile         the validated destination file, must not be {@code null}
     * @param preserveFileDate whether to preserve the file date
     * @throws IOException if an error occurs
     */
    public static void doCopyFile(File srcFile, File destFile, boolean preserveFileDate) throws IOException {
        if (destFile.exists() && destFile.isDirectory()) {
            throw new IOException("Destination '" + destFile + "' exists but is a directory");
        }

        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel input = null;
        FileChannel output = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);
            input = fis.getChannel();
            output = fos.getChannel();
            long size = input.size();
            long pos = 0;
            long count = 0;
            while (pos < size) {
                count = size - pos > FILE_COPY_BUFFER_SIZE ? FILE_COPY_BUFFER_SIZE : size - pos;
                pos += output.transferFrom(input, pos, count);
            }
        } finally {
            closeQuietly(output);
            closeQuietly(fos);
            closeQuietly(input);
            closeQuietly(fis);
        }

        if (srcFile.length() != destFile.length()) {
            throw new IOException("Failed to copy full contents from '" +
                    srcFile + "' to '" + destFile + "'");
        }
        if (preserveFileDate) {
            destFile.setLastModified(srcFile.lastModified());
        }
    }

    public static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }
    //============================URL============================================

    /**
     * 只encode中文
     */
    public static String encode(String sourceString) {
        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]+");
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
    //但如果字符串为空，返回“”
    public static String getSafeString(String str) {
        if (TextUtils.isEmpty(str))
            return "";
        else
            return str;
    }
    //但如果字符串为空，返回“”
    public static List getSafeList(List list) {
        if (list==null)
            return new ArrayList<>();
        else
            return list;
    }
    //但如果字符串为空，返回“”
    public static String getGoodNumberStr(double num) {
        if (num==(int)num)
            return (int)num+"";
        else
            return num+"";
    }
}
