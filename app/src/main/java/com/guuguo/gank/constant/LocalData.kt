package com.guuguo.gank.constant

import com.guuguo.android.lib.utils.Preference
import com.guuguo.gank.app.App


/**
 * Created by guodeqing on 16/3/7.
 */

object LocalData {
    private val mContext by lazy { App.get()!! }
    var userName by Preference(mContext, "")
    var gankDaily by Preference(mContext, "")

}
