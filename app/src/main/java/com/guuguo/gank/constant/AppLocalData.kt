package com.guuguo.gank.constant

import com.guuguo.android.lib.utils.Preference
import com.guuguo.gank.app.App
import com.guuguo.gank.util.KV
import com.tencent.mmkv.MMKV


/**
 * Created by guodeqing on 16/3/7.
 */

object AppLocalData {
    var userName by KV("")
    var gankDaily by KV("")
    var isDark by KV(false)

}
