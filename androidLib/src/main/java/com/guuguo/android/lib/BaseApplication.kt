package com.guuguo.android.lib

import android.app.Application
import android.os.Build
import android.os.Handler
import com.guuguo.android.lib.app.ActivityLifecycle
import com.guuguo.android.lib.utils.Utils


/**
 * Created by guodeqing on 16/3/7.
 */
abstract class BaseApplication : Application(), Thread.UncaughtExceptionHandler {
    init {
        INSTANCE = this
    }

    lateinit var mActivityLifecycle: ActivityLifecycle

    override fun onCreate() {

        Thread.setDefaultUncaughtExceptionHandler(this)
        Utils.init(this)
        initThread()
        initLyfecycle()
        init()
        super.onCreate()
    }

    private fun initThread() {
        sHandler = Handler(mainLooper)
        mMainThread = Thread.currentThread()
    }

    open fun getCurrentActivity() = mActivityLifecycle.mActivityList.lastOrNull()

    private fun initLyfecycle() {
        mActivityLifecycle = ActivityLifecycle()
        registerActivityLifecycleCallbacks(mActivityLifecycle)
    }


    override fun uncaughtException(t: Thread, e: Throwable) {
        val sb = StringBuilder()
        sb.append("MODEL: ").append(Build.MODEL).append('\n')
        sb.append("SDK: ").append(Build.VERSION.SDK_INT).append('\n')
        sb.append("RELEASE: ").append(Build.VERSION.RELEASE).append('\n')
        sb.append('\n').append(e.localizedMessage).append('\n')
        for (element in e.stackTrace) {
            sb.append('\n')
            sb.append(element.toString())
        }
        try {
//            val doc = getDocumentFile()
//            val dir = DocumentUtils.getOrCreateSubDirectory(doc, "log")
//            val file = DocumentUtils.getOrCreateFile(dir, StringUtils.getDateStringWithSuffix("log"))
//            DocumentUtils.writeStringToFile(contentResolver, file, sb.toString())
        } catch (ex: Exception) {
        }

        mActivityLifecycle.clear()
        System.exit(1)
    }

    protected abstract fun init()


    companion object {
        private lateinit var mMainThread: Thread
        private lateinit var sHandler: Handler
        fun getHandler(): Handler {
            return sHandler
        }

        fun runOnUiThread(runnable: Runnable) {
            if (Thread.currentThread() !== mMainThread) {
                sHandler.post(runnable)
            } else {
                runnable.run()
            }
        }

        private lateinit var INSTANCE: BaseApplication
        fun get(): BaseApplication {
            return INSTANCE
        }
    }

}
