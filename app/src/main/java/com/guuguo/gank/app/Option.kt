package com.guuguo.gank.app

import com.guuguo.android.util.Preference
import com.guuguo.gank.app.MyApplication


/**
 * Created by guodeqing on 16/3/7.
 */

object Option {
    var context = MyApplication.instance!!
    var userName: String by Preference(context, "")
}
