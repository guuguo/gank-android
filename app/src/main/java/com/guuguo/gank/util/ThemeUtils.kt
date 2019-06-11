package com.guuguo.gank.util

import android.app.Activity
import android.content.Intent
import androidx.drawerlayout.widget.DrawerLayout
import com.guuguo.gank.R
import com.guuguo.gank.constant.AppLocalData

object ThemeUtils {

    /**
     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
     */
    fun changeToTheme(activity: Activity) {
        AppLocalData.isDark = !AppLocalData.isDark
        activity.finish()

        activity.startActivity(Intent(activity, activity.javaClass))
    }

    /**
     * Set the theme of the activity, according to the configuration.
     */
    fun onActivityCreateSetTheme(activity: Activity) {
        if (AppLocalData.isDark) {
            activity.setTheme(R.style.MyTheme_Dark)
        } else {
            activity.setTheme(R.style.MyTheme_Light)
        }
    }
}