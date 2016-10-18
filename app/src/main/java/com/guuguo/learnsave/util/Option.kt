package com.guuguo.learnsave.util

import com.guuguo.androidlib.BaseApplication

/**
 * Created by guodeqing on 16/3/7.
 */

object Option {
    var context = BaseApplication.getInstance()
    
    var userName: String by Preference(context, userName, "")
}
