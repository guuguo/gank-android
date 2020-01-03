package com.guuguo.android.lib.extension

import android.app.Activity
import android.graphics.drawable.Drawable
import androidx.appcompat.widget.Toolbar
import com.guuguo.android.lib.app.LBaseFragment

/**
 * Created by 大哥哥 on 2016/10/24 0024.
 */
fun Toolbar.initNav(activity: Activity, backIcon: Int) {
    kotlin.runCatching {
        setNavigationIcon(backIcon)
        setNavigationOnClickListener { activity.onBackPressed() }
    }.onFailure {
        it.message.log()
    }
}

fun Toolbar.initNav(fragment: LBaseFragment, backIcon: Int) {
    setNavigationIcon(backIcon)
    setNavigationOnClickListener { if (!fragment.onBackPressed()) fragment.activity?.onBackPressed() }
}