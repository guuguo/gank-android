package com.guuguo.gank.app

import android.content.Intent
import android.support.v7.app.AppCompatDelegate
import com.guuguo.android.lib.BaseApplication
import com.guuguo.android.lib.utils.LogUtil
import com.guuguo.android.lib.utils.Utils
import com.guuguo.gank.BuildConfig
import com.guuguo.gank.app.activity.UpgradeActivity
import com.guuguo.gank.constant.MY_DEBUG
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.beta.upgrade.UpgradeListener
import com.tencent.bugly.crashreport.CrashReport

/**
 * Created by guodeqing on 16/3/7.
 */
class App : BaseApplication() {
    override fun init() {
        Utils.init(this)
        LogUtil.init(MY_DEBUG)
        initBugly()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true); }


    companion object {
        fun get() = BaseApplication.get() as App
    }

    private fun initBugly() {
        Beta.upgradeListener = UpgradeListener { _, strategy, _, _ ->
            if (strategy != null) {
                val i = Intent()
                i.setClass(applicationContext, UpgradeActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(i)
            } else {
//                Toast.makeText(this, "没有更新", Toast.LENGTH_LONG).show();
            }
        }

        val strategy = CrashReport.UserStrategy(applicationContext)
        Bugly.init(this, "40950003e9",BuildConfig.DEBUG, strategy)
    }

}
