package com.guuguo.learnsave.util

import com.guuguo.learnsave.app.MyApplication


/**
 * Created by guodeqing on 16/3/7.
 */

object Option {
    var context = MyApplication.instance!!

    var userName: String by Preference(context, userName, "")
}
