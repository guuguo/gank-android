package com.guuguo.android.lib.extension

import android.app.Activity
import android.support.v7.widget.Toolbar

/**
 * Created by 大哥哥 on 2016/10/24 0024.
 */
fun Toolbar.initNav(activity:Activity, backIcon:Int) {
    setNavigationIcon(backIcon)
    setNavigationOnClickListener { activity.onBackPressed() }
}