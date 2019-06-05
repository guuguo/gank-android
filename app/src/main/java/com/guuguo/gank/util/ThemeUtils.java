package com.guuguo.gank.util;

import android.app.Activity;
import android.content.Intent;
import com.guuguo.gank.R;

public class ThemeUtils {

  public static boolean sThemeDark = false;


  /**
   * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
   */
  public static void changeToTheme(Activity activity) {
    sThemeDark = !sThemeDark;
    activity.finish();

    activity.startActivity(new Intent(activity, activity.getClass()));
  }

  /**
   * Set the theme of the activity, according to the configuration.
   */
  public static void onActivityCreateSetTheme(Activity activity) {
    if (sThemeDark) {
      activity.setTheme(R.style.MyTheme_Dark);
    } else {
      activity.setTheme(R.style.MyTheme_Light);
    }
  }
}