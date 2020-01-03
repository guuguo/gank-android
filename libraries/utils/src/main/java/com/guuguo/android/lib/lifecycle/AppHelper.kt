package com.guuguo.android.lib.lifecycle

import android.app.Application
import android.os.Debug
import com.guuguo.android.lib.utils.LogUtil
import com.guuguo.android.lib.utils.Utils

/**
 * Project: androidLib
 *
 * @author : guuguo
 * @since 2018/8/21
 */
object AppHelper {
    lateinit var app: Application
        private set

    fun init(app: Application,debug: Boolean=false) {
        AppHelper.app = app
        initLyfecycle()
        Utils.init(app)
        LogUtil.init(debug,false)
    }

    lateinit var mActivityLifecycle: ActivityLifecycle

    fun getCurrentActivity() = mActivityLifecycle.mActivityList.lastOrNull()

    private fun initLyfecycle() {
        mActivityLifecycle = ActivityLifecycle()
        app.registerActivityLifecycleCallbacks(mActivityLifecycle)
    }

}