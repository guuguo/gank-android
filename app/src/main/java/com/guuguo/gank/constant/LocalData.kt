package com.guuguo.gank.constant

import com.guuguo.android.util.Preference
import com.guuguo.gank.app.MyApplication


/**
 * Created by guodeqing on 16/3/7.
 */

object LocalData {
    private val mContext by lazy { MyApplication.instance!! }
    var userName by Preference(mContext, "")
    var gankDaily by Preference(mContext, "")

}
