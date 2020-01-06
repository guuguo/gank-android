package com.bianla.commonlibrary.extension

import android.app.Activity
import android.graphics.drawable.Drawable
import androidx.appcompat.widget.Toolbar

/**
 * Created by 大哥哥 on 2016/10/24 0024.
 */

fun Toolbar.initNav(activity:Activity,backIcon:Int) {
    setNavigationIcon(backIcon)
    setNavigationOnClickListener { activity.onBackPressed() }
}

fun Toolbar.initNav(activity:Activity, backIcon: Drawable?) {
    navigationIcon = backIcon
    setNavigationOnClickListener { activity.onBackPressed() }
}