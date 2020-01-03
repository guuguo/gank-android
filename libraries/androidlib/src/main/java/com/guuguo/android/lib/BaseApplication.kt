package com.guuguo.android.lib

import android.app.Application
import android.os.Build
import android.os.Handler
import com.guuguo.android.lib.lifecycle.AppHelper


/**
 * Created by guodeqing on 16/3/7.
 */
abstract class BaseApplication : Application(), Thread.UncaughtExceptionHandler {
    init {
        INSTANCE = this
    }


    override fun onCreate() {

        Thread.setDefaultUncaughtExceptionHandler(this)
        AppHelper.init(this,debug = false)
        init()
        super.onCreate()
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

        AppHelper.mActivityLifecycle.clear()
        System.exit(1)
    }

    protected abstract fun init()

    companion object {

        private lateinit var INSTANCE: BaseApplication
        fun get(): BaseApplication {
            return INSTANCE
        }
    }

}
