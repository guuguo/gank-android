package com.guuguo.android.lib.utils

import android.util.Log
import com.guuguo.android.lib.extension.Klog

/**
 * Created by mimi on 2017-01-02.
 */

object LogUtil {
    var debug = true
    val TAG by lazy { AppUtil.getAppName() }
    fun init(debug: Boolean, borderEnable: Boolean = true) {
        this.debug = debug
        Klog.getSettings().setLogEnable(debug).setBorderEnable(borderEnable)
    }

    fun i(tag: String = TAG, info: String) {
        if (debug)
            Log.i(tag, info)
    }

    fun i(info: String) {
        if (debug)
            Log.i(TAG, info)
    }

    fun d(tag: String = TAG, info: String) {
        if (debug)
            Log.i(tag, info)
    }

    fun d(info: String) {
        if (debug)
            Log.i(TAG, info)
    }

    fun e(msg: String, e: Throwable? = null) {
        if (debug)
            Log.e(TAG, msg, e)
    }

    fun e(tag: String, msg: String, e: Throwable? = null) {
        if (debug)
            Log.e(tag, msg, e)
    }

    fun w(tag: String, msg: String, e: Throwable? = null) {
        if (debug)
            Log.w(tag, msg, e)
    }

    fun w(msg: String, e: Throwable? = null) {
        if (debug)
            Log.w(TAG, msg, e)
    }
}
