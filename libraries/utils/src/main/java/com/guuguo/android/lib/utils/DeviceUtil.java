package com.guuguo.android.lib.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.io.File;
import java.util.Locale;

/**
 * Created by mimi on 2017-01-19.
 */

public class DeviceUtil {
    private DeviceUtil(){}
    /**
     * The constant DEVICE_TYPE_WATCH.
     */
    public static final int DEVICE_TYPE_WATCH = 0;
    /**
     * The constant DEVICE_TYPE_PHONE.
     */
    public static final int DEVICE_TYPE_PHONE = 1;
    /**
     * The constant DEVICE_TYPE_PHABLET.
     */
    public static final int DEVICE_TYPE_PHABLET = 2;
    /**
     * The constant DEVICE_TYPE_TABLET.
     */
    public static final int DEVICE_TYPE_TABLET = 3;
    /**
     * The constant DEVICE_TYPE_TV.
     */
    public static final int DEVICE_TYPE_TV = 4;

    /**
     * The constant PHONE_TYPE_GSM.
     */
    public static final int PHONE_TYPE_GSM = 0;
    /**
     * The constant PHONE_TYPE_CDMA.
     */
    public static final int PHONE_TYPE_CDMA = 1;
    /**
     * The constant PHONE_TYPE_NONE.
     */
    public static final int PHONE_TYPE_NONE = 2;

    /**
     * The constant ORIENTATION_PORTRAIT.
     */
    public static final int ORIENTATION_PORTRAIT = 0;
    /**
     * The constant ORIENTATION_LANDSCAPE.
     */
    public static final int ORIENTATION_LANDSCAPE = 1;
    /**
     * The constant ORIENTATION_UNKNOWN.
     */
    public static final int ORIENTATION_UNKNOWN = 2;

    /**
     * Instantiates a new Easy  device mod.
     *
     * @param context the context
     */

    /**
     * Gets phone type.
     *
     * @return the phone type
     */
    public static final int getPhoneType() {
        TelephonyManager tm = (TelephonyManager) Utils.getContext().getSystemService(Context.TELEPHONY_SERVICE);

        switch (tm.getPhoneType()) {
            case TelephonyManager.PHONE_TYPE_GSM:
                return PHONE_TYPE_GSM;

            case TelephonyManager.PHONE_TYPE_CDMA:
                return PHONE_TYPE_CDMA;
            case TelephonyManager.PHONE_TYPE_NONE:
            default:
                return PHONE_TYPE_NONE;
        }
    }

    /**
     * Gets phone no.
     *
     * @return the phone no
     */
    @SuppressWarnings("MissingPermission")
    public static final String getPhoneNo() {
        TelephonyManager tm = (TelephonyManager) Utils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        String result = null;
        if (Utils.hasPermission( Manifest.permission.READ_PHONE_STATE)
                && tm.getLine1Number() != null) {
            result = tm.getLine1Number();
        }

        return CommonUtil.checkValidData(result);
    }

    /**
     * Gets product.
     *
     * @return the product
     */
    public static final String getProduct() {
        return CommonUtil.checkValidData(Build.PRODUCT);
    }

    /**
     * Gets fingerprint.
     *
     * @return the fingerprint
     */
    public static final String getFingerprint() {
        return CommonUtil.checkValidData(Build.FINGERPRINT);
    }

    /**
     * Gets hardware.
     *
     * @return the hardware
     */
    public static final String getHardware() {
        return CommonUtil.checkValidData(Build.HARDWARE);
    }

    /**
     * Gets radio ver.
     *
     * @return the radio ver
     */
    public static final String getRadioVer() {
        String result = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            result = Build.getRadioVersion();
        }
        return CommonUtil.checkValidData(result);
    }

    /**
     * Gets device.
     *
     * @return the device
     */
    public static final String getDevice() {
        return CommonUtil.checkValidData(Build.DEVICE);
    }

    /**
     * Gets bootloader.
     *
     * @return the bootloader
     */
    public static final String getBootloader() {
        return CommonUtil.checkValidData(Build.BOOTLOADER);
    }

    /**
     * Gets board.
     *
     * @return the board
     */
    public static final String getBoard() {
        return CommonUtil.checkValidData(Build.BOARD);
    }

    /**
     * Gets display version.
     *
     * @return the display version
     */
    public static final String getDisplayVersion() {
        return CommonUtil.checkValidData(Build.DISPLAY);
    }

    /**
     * Gets language.
     *
     * @return the language
     */
    public static final String getLanguage() {
        return CommonUtil.checkValidData(Locale.getDefault().getLanguage());
    }

    /**
     * Device type int.
     * Based on metric : https://design.google.com/devices/
     *
     * @param activity the activity
     * @return the int
     */
    public static final int getDeviceType(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
        if (diagonalInches > 10.1) {
            return DEVICE_TYPE_TV;
        } else if (diagonalInches <= 10.1 && diagonalInches > 7) {
            return DEVICE_TYPE_TABLET;
        } else if (diagonalInches <= 7 && diagonalInches > 6.5) {
            return DEVICE_TYPE_PHABLET;
        } else if (diagonalInches <= 6.5 && diagonalInches >= 2) {
            return DEVICE_TYPE_PHONE;
        } else {
            return DEVICE_TYPE_WATCH;
        }
    }

    /**
     * Gets manufacturer.
     *
     * @return the manufacturer
     */
    public static final String getManufacturer() {
        return CommonUtil.checkValidData(
                CommonUtil.handleIllegalCharacterInResult(Build.MANUFACTURER));
    }

    /**
     * Gets model.
     *
     * @return the model
     */
    public static final String getModel() {
        return CommonUtil.checkValidData(
                CommonUtil.handleIllegalCharacterInResult(Build.MODEL));
    }

    /**
     * Gets build brand.
     *
     * @return the build brand
     */
    public static final String getBuildBrand() {
        return CommonUtil.checkValidData(
                CommonUtil.handleIllegalCharacterInResult(Build.BRAND));
    }

    /**
     * Gets build host.
     *
     * @return the build host
     */
    public static final String getBuildHost() {
        return CommonUtil.checkValidData(Build.HOST);
    }

    /**
     * Gets build tags.
     *
     * @return the build tags
     */
    public static final String getBuildTags() {
        return CommonUtil.checkValidData(Build.TAGS);
    }

    /**
     * Gets build time.
     *
     * @return the build time
     */
    public static final long getBuildTime() {
        return Build.TIME;
    }

    /**
     * Gets build user.
     *
     * @return the build user
     */
    public static final String getBuildUser() {
        return CommonUtil.checkValidData(Build.USER);
    }

    /**
     * Gets build version release.
     *
     * @return the build version release
     */
    public static final String getBuildVersionRelease() {
        return CommonUtil.checkValidData(Build.VERSION.RELEASE);
    }

    /**
     * Gets screen display id.
     *
     * @return the screen display id
     */
    public static final String getScreenDisplayID() {
        WindowManager wm = (WindowManager) Utils.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return CommonUtil.checkValidData(String.valueOf(display.getDisplayId()));
    }

    /**
     * Gets build version codename.
     *
     * @return the build version codename
     */
    public static final String getBuildVersionCodename() {
        return CommonUtil.checkValidData(Build.VERSION.CODENAME);
    }

    /**
     * Gets build version incremental.
     *
     * @return the build version incremental
     */
    public static final String getBuildVersionIncremental() {
        return CommonUtil.checkValidData(Build.VERSION.INCREMENTAL);
    }

    /**
     * Gets build version sdk.
     *
     * @return the build version sdk
     */
    public static final int getBuildVersionSDK() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * Gets build id.
     *
     * @return the build id
     */
    public static final String getBuildID() {
        return CommonUtil.checkValidData(Build.ID);
    }

    /**
     * Return the android id of device.
     *
     * @return the android id of device
     */
    @SuppressLint("HardwareIds")
    public static String getAndroidID() {
        String id = Settings.Secure.getString(
                Utils.getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID
        );
        return id == null ? "" : id;
    }
    /**
     * Is Device rooted boolean.
     *
     * @return the boolean
     */
    public static final boolean isDeviceRooted() {
        String su = "su";
        String[] locations = {
                "/sbin/", "/system/bin/", "/system/xbin/", "/system/sd/xbin/", "/system/bin/failsafe/",
                "/data/local/xbin/", "/data/local/bin/", "/data/local/"
        };
        for (String location : locations) {
            if (new File(location + su).exists()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets imei.
     *
     * @return the imei
     */
    @SuppressWarnings("MissingPermission")
    public static final String getIMEI() {
        TelephonyManager tm = (TelephonyManager) Utils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        String result = null;
        if (Utils.hasPermission(Manifest.permission.READ_PHONE_STATE)) {
            result = tm.getDeviceId();
        }

        return CommonUtil.checkValidData(result);
    }

    /**
     * Gets serial.
     *
     * @return the serial
     */
    public static final String getSerial() {
        return CommonUtil.checkValidData(Build.SERIAL);
    }

    /**
     * Gets os codename.
     *
     * @return the os codename
     */
    public static final String getOSCodename() {
        String codename;
        switch (Build.VERSION.SDK_INT) {
            case Build.VERSION_CODES.BASE:
                codename = "First Android Version. Yay !";
                break;
            case Build.VERSION_CODES.BASE_1_1:
                codename = "Base Android 1.1";
                break;
            case Build.VERSION_CODES.CUPCAKE:
                codename = "Cupcake";
                break;
            case Build.VERSION_CODES.DONUT:
                codename = "Donut";
                break;
            case Build.VERSION_CODES.ECLAIR:
            case Build.VERSION_CODES.ECLAIR_0_1:
            case Build.VERSION_CODES.ECLAIR_MR1:

                codename = "Eclair";
                break;
            case Build.VERSION_CODES.FROYO:
                codename = "Froyo";
                break;
            case Build.VERSION_CODES.GINGERBREAD:
            case Build.VERSION_CODES.GINGERBREAD_MR1:
                codename = "Gingerbread";
                break;
            case Build.VERSION_CODES.HONEYCOMB:
            case Build.VERSION_CODES.HONEYCOMB_MR1:
            case Build.VERSION_CODES.HONEYCOMB_MR2:
                codename = "Honeycomb";
                break;
            case Build.VERSION_CODES.ICE_CREAM_SANDWICH:
            case Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1:
                codename = "Ice Cream Sandwich";
                break;
            case Build.VERSION_CODES.JELLY_BEAN:
            case Build.VERSION_CODES.JELLY_BEAN_MR1:
            case Build.VERSION_CODES.JELLY_BEAN_MR2:
                codename = "Jelly Bean";
                break;
            case Build.VERSION_CODES.KITKAT:
                codename = "Kitkat";
                break;
            case Build.VERSION_CODES.KITKAT_WATCH:
                codename = "Kitkat Watch";
                break;
            case Build.VERSION_CODES.LOLLIPOP:
            case Build.VERSION_CODES.LOLLIPOP_MR1:
                codename = "Lollipop";
                break;
            case Build.VERSION_CODES.M:
                codename = "Marshmallow";
                break;
            case Build.VERSION_CODES.N:
                codename = "Nougat";
                break;
            default:
                codename = CommonUtil.NOT_FOUND_VAL;
                break;
        }
        return codename;
    }

    /**
     * Gets os version.
     *
     * @return the os version
     */
    public static final String getOSVersion() {
        return CommonUtil.checkValidData(Build.VERSION.RELEASE);
    }

    /**
     * Gets orientation.
     *
     * @param activity the activity
     * @return the orientation
     */
    public static final int getOrientation(final Activity activity) {
        switch (activity.getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                return ORIENTATION_PORTRAIT;
            case Configuration.ORIENTATION_LANDSCAPE:
                return ORIENTATION_LANDSCAPE;
            default:
                return ORIENTATION_UNKNOWN;
        }
    }
}
