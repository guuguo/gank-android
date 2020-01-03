package com.guuguo.android.lib.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;


/**
 * mimi 创造于 2017-06-14. 项目 order
 */

public class Utils {

  private Utils() {
  }

  private static Context mContext;

  public static void init(Context context) {
    mContext = context;
  }

  public static Context getContext() {
    if (mContext != null) {
      return mContext;
    } else {
      Log.e("Utils", "Utils init error", new Exception("Utils还没有初始化，确认初始化了吗?"));
      return null;
    }
  }

  public static boolean hasPermission(final String permission) {
    return getContext().checkCallingOrSelfPermission(permission)
        == PackageManager.PERMISSION_GRANTED;
  }
}
