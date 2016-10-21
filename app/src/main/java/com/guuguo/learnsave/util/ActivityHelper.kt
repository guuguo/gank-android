package com.guuguo.learnsave.util

import android.app.Activity
import android.content.Intent

import com.guuguo.learnsave.app.activity.DateGankActivity

/**
 * Created by guodeqing on 7/24/16.
 */

object ActivityHelper {
    val ACTIVITY_DATE_GANK = 0

    fun startDateGank(activity: Activity) {
        val intent = Intent(activity, DateGankActivity::class.java)
        activity.startActivityForResult(intent, ACTIVITY_DATE_GANK)
    }
}
