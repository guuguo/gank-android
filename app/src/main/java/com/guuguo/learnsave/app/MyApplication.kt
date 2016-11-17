package com.guuguo.learnsave.app

import android.app.Application
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta

/**
 * Created by guodeqing on 16/3/7.
 */
class MyApplication : Application() {

    companion object {
        var instance: MyApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initBugly()
    }

    private fun initBugly() {
        Bugly.init(this, "40950003e9", false);
//        Beta.autoCheckUpgrade = true;
//        Beta.autoInit = true;
//        Beta.upgradeCheckPeriod = 1 * 60 * 1000;
    }
}
