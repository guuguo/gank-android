package com.guuguo.gank.constant

import com.guuguo.android.util.Preference
import com.guuguo.gank.app.MyApplication


/**
 * Created by guodeqing on 16/3/7.
 */

object Option {
    var context = com.guuguo.gank.app.MyApplication.Companion.instance!!
    var userName: String by com.guuguo.android.util.Preference(context, "")
}
