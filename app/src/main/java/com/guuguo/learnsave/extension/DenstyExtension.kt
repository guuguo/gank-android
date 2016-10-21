package com.guuguo.learnsave.extension

import android.content.Context
import com.guuguo.learnsave.app.MyApplication

/**
 * Created by 大哥哥 on 2016/10/21 0021.
 */


fun Int.pxToDp(): Int {
    return (this / MyApplication.instance!!.resources.displayMetrics.density + 0.5f).toInt()
}

fun Int.dpToPx(): Int {
    return (this * MyApplication.instance!!.resources.displayMetrics.density + 0.5f).toInt()
}
