package com.guuguo.android.lib.utils.network;

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.guuguo.android.lib.utils.CommonUtil;
import com.guuguo.android.lib.utils.Utils;

import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import static android.telephony.TelephonyManager.NETWORK_TYPE_GSM;
import static android.telephony.TelephonyManager.NETWORK_TYPE_IWLAN;
import static android.telephony.TelephonyManager.NETWORK_TYPE_TD_SCDMA;


/**
 * Created by mimi on 2017-01-19.
 */

public class NetWorkUtils {
    public static final String SOCKET_EXCEPTION = "Socket Exception";


    public enum NetworkType {
        NETWORK_WIFI,
        NETWORK_4G,
        NETWORK_3G,
        NETWORK_2G,
        NETWORK_UNKNOWN,
        NETWORK_NO
    }

    public static boolean isTypeMobile(NetworkType type) {
        return (type == NetworkType.NETWORK_4G || type == NetworkType.NETWORK_3G || type == NetworkType.NETWORK_2G);
    }
//
//    /**
//     * Instantiates a new Easy  network mod.
//     *
//     * @param
//     * @return
//     */
//    public static boolean isAvailableByPing() {
//        boolean isNet=false;
//        InetAddress in;
//        in = null;
//        // Definimos la ip de la cual haremos el ping
//        try {
//            in = InetAddress.getByName("202.108.22.5");
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//        // Definimos un tiempo en el cual ha de responder
//        try {
//            if (in.isReachable(1000)) {
//                isNet=true;
//            } else {
//                isNet=false;
//            }
//        } catch (IOException e) {
//            LogUtil.INSTANCE.i("isAvailableByPing errorMsg " +  e.getMessage());
//        }
//        return isNet;
//    }

    /**
     * 获取活动网络信息
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
     *
     * @return NetworkInfo
     */
    private static NetworkInfo getActiveNetworkInfo() {
        return ((ConnectivityManager) Utils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
    }

    /**
     * 判断网络是否连接
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
     * true 添加 ping测试, false 直接根据Android系统的结果
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isConnected(boolean needReliable) {
        NetworkInfo info = getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            return !needReliable ;
        }
        return false;
    }

    /**
     * Is wifi enabled.
     *
     * @return the boolean
     */

    public static final boolean isWifiEnabled() {
        boolean wifiState = false;

        WifiManager wifiManager = (WifiManager) Utils.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            wifiState = wifiManager.isWifiEnabled();
        }
        return wifiState;
    }

    /**
     * Is network available boolean.
     *
     * @return the boolean
     */
    @SuppressWarnings("MissingPermission")
    public static final boolean isNetworkAvailable() {

        if (Utils.hasPermission(Manifest.permission.INTERNET)
                && Utils.hasPermission(Manifest.permission.ACCESS_NETWORK_STATE)) {
            ConnectivityManager cm = (ConnectivityManager) Utils.getContext().getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnected();
        }
        return false;
    }

    /**
     * Gets ip address v4.
     *
     * @return the ip address
     */
    public static final String getIPv4Address() {
        String result = null;
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress().toUpperCase();
                        boolean isIPv4 = addr instanceof Inet4Address;
                        if (isIPv4) {
                            result = sAddr;
                        }
                    }
                }
            }
        } catch (SocketException e) {
        }
        return CommonUtil.checkValidData(result);
    }

    /**
     * Gets ip address v6.
     *
     * @return the ip address
     */
    public static final String getIPv6Address() {
        String result = null;
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress().toUpperCase();
                        boolean isIPv4 = addr instanceof Inet4Address;
                        if (!isIPv4) {
                            int delim = sAddr.indexOf('%'); // drop ip6 port suffix
                            result = delim < 0 ? sAddr : sAddr.substring(0, delim);
                        }
                    }
                }
            }
        } catch (SocketException e) {
        }
        return CommonUtil.checkValidData(result);
    }

    /**
     * 获取当前网络类型
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
     *
     * @return 网络类型
     * <ul>
     * <li>{@link NetWorkUtils.NetworkType#NETWORK_WIFI   } </li>
     * <li>{@link NetWorkUtils.NetworkType#NETWORK_4G     } </li>
     * <li>{@link NetWorkUtils.NetworkType#NETWORK_3G     } </li>
     * <li>{@link NetWorkUtils.NetworkType#NETWORK_2G     } </li>
     * <li>{@link NetWorkUtils.NetworkType#NETWORK_UNKNOWN} </li>
     * <li>{@link NetWorkUtils.NetworkType#NETWORK_NO     } </li>
     * </ul>
     */
    public static NetworkType getNetworkType() {
        NetworkType netType = NetworkType.NETWORK_NO;
        NetworkInfo info = getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {

            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                netType = NetworkType.NETWORK_WIFI;
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                switch (info.getSubtype()) {

                    case NETWORK_TYPE_GSM:
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        netType = NetworkType.NETWORK_2G;
                        break;

                    case NETWORK_TYPE_TD_SCDMA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        netType = NetworkType.NETWORK_3G;
                        break;

                    case NETWORK_TYPE_IWLAN:
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        netType = NetworkType.NETWORK_4G;
                        break;
                    default:

                        String subtypeName = info.getSubtypeName();
                        if ("TD-SCDMA".equalsIgnoreCase(subtypeName)
                                || "WCDMA".equalsIgnoreCase(subtypeName)
                                || "CDMA2000".equalsIgnoreCase(subtypeName)) {
                            netType = NetworkType.NETWORK_3G;
                        } else {
                            netType = NetworkType.NETWORK_UNKNOWN;
                        }
                        break;
                }
            } else {
                netType = NetworkType.NETWORK_UNKNOWN;
            }
        }
        return netType;
    }

    /**
     * 打开或关闭移动数据
     * <p>需系统应用 需添加权限{@code <uses-permission android:name="android.permission.MODIFY_PHONE_STATE"/>}</p>
     *
     * @param enabled {@code true}: 打开<br>{@code false}: 关闭
     */
    public static void setDataEnabled(boolean enabled) {
        try {
            TelephonyManager tm = (TelephonyManager) Utils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            Method setMobileDataEnabledMethod = tm.getClass().getDeclaredMethod("setDataEnabled", boolean.class);
            if (null != setMobileDataEnabledMethod) {
                setMobileDataEnabledMethod.invoke(tm, enabled);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取网络运营商名称
     * <p>中国移动、如中国联通、中国电信</p>
     *
     * @return 运营商名称
     */
    public static String getNetworkOperatorName() {
        TelephonyManager tm = (TelephonyManager) Utils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getNetworkOperatorName() : null;
    }

    /**
     * Gets wifi mac.
     *
     * @return the wifi mac
     */
    @SuppressWarnings("MissingPermission")
    public static final String getWifiMAC() {
        String result = "02:00:00:00:00:00";
        if (Utils.hasPermission(Manifest.permission.ACCESS_WIFI_STATE)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // Hardware ID are restricted in Android 6+
                // https://developer.android.com/about/versions/marshmallow/android-6.0-changes.html#behavior-hardware-id
                Enumeration<NetworkInterface> interfaces = null;
                try {
                    interfaces = NetworkInterface.getNetworkInterfaces();
                } catch (SocketException e) {
                }
                while (interfaces != null && interfaces.hasMoreElements()) {
                    NetworkInterface networkInterface = interfaces.nextElement();

                    byte[] addr = new byte[0];
                    try {
                        addr = networkInterface.getHardwareAddress();
                    } catch (SocketException e) {
                    }
                    if (addr == null || addr.length == 0) {
                        continue;
                    }

                    StringBuilder buf = new StringBuilder();
                    for (byte b : addr) {
                        buf.append(String.format("%02X:", b));
                    }
                    if (buf.length() > 0) {
                        buf.deleteCharAt(buf.length() - 1);
                    }
                    String mac = buf.toString();
                    String wifiInterfaceName = "wlan0";
                    result = wifiInterfaceName.equals(networkInterface.getName()) ? mac : result;
                }
            } else {
                WifiManager wm = (WifiManager) Utils.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                result = wm.getConnectionInfo().getMacAddress();
            }
        }
        return CommonUtil.checkValidData(result);
    }
}
