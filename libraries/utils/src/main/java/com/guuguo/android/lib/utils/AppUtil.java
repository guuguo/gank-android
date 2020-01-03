package com.guuguo.android.lib.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import static com.guuguo.android.lib.utils.CommonUtil.checkValidData;

/**
 * Created by 大哥哥 on 2016/8/26 0026.
 */

public class AppUtil {

    private static final String NAME_NOT_FOUND_EXCEPTION = "Name Not Found Exception";

    /**
     * Gets activity name.
     *
     * @return the activity name
     */
    public static final String getActivityName() {
        return checkValidData(Utils.getContext().getClass().getSimpleName());
    }

    /**
     * Gets package name.
     *
     * @return the package name
     */
    public static final String getPackageName() {
        return checkValidData(Utils.getContext().getPackageName());
    }

    /**
     * Gets store.
     *
     * @return the store
     */
    public static final String getStore() {
        String result = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            result = Utils.getContext().getPackageManager().getInstallerPackageName(Utils.getContext().getPackageName());
        }
        return checkValidData(result);
    }

    /**
     * Gets app name.
     *
     * @return the app name
     */
    public static final String getAppName() {
        String result;
        final PackageManager pm = Utils.getContext().getPackageManager();
        ApplicationInfo ai = null;
        try {
            ai = pm.getApplicationInfo(Utils.getContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
        }
        result = ai != null ? (String) pm.getApplicationLabel(ai) : null;
        return checkValidData(result);
    }

    /**
     * Gets app version.
     *
     * @return the app version
     */
    public static final String getAppVersion() {
        String result = null;
        try {
            result = Utils.getContext().getPackageManager().getPackageInfo(Utils.getContext().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return checkValidData(result);
    }

    /**
     * Gets app version code.
     *
     * @return the app version code
     */
    public static final int getAppVersionCode() {
        int result = 0;
        try {
            result = Utils.getContext().getPackageManager()
                    .getPackageInfo(Utils.getContext().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * Check if the app with the specified packagename is installed or not
     *
     * @param packageName the package name
     * @return the boolean
     */
    public static final boolean isAppInstalled(String packageName) {
        return Utils.getContext().getPackageManager().getLaunchIntentForPackage(packageName) != null;
    }
}
