package com.guuguo.gank.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.guuguo.android.lib.BaseApplication
import com.guuguo.android.lib.utils.LogUtil
import com.guuguo.android.lib.utils.Utils
import com.guuguo.gank.R
import com.guuguo.gank.constant.MY_DEBUG
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta

/**
 * Created by guodeqing on 16/3/7.
 */
class MyApplication : BaseApplication() {
    override fun init() {
        Utils.init(this)
        LogUtil.init(MY_DEBUG)
        instance = INSTANCE as MyApplication?;
        initBugly()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true); }

    companion object {
        var instance: MyApplication? = null
        get() = BaseApplication.get()
    }


    private fun initBugly() {
        Beta.upgradeDialogLayoutId = R.layout.dialog_upgrade
        Beta.tipsDialogLayoutId = R.layout.dialog_tips
        Bugly.init(this, "40950003e9", false);
    }
}
