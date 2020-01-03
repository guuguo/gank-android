package com.guuguo.android.lib.ktx

import android.util.Log
import com.guuguo.android.lib.utils.LogUtil
import com.guuguo.android.lib.utils.Utils

/**
 * Description: log相关，日志的开关和默认tag通过Utils来配置
 * Create by lxj, at 2018/12/5
 */

private enum class LogLevel {
    Verbose, Debug, Info, Warn, Error
}

fun Any.logv(tag: String, msg: String) {
    intervalLog(LogLevel.Verbose, tag, msg)
}

fun Any.logv(msg: String) {
    intervalLog(LogLevel.Verbose, LogUtil.TAG, msg)
}

fun Any.logd(tag: String, msg: String) {
    intervalLog(LogLevel.Debug, tag, msg)
}

fun Any.logd(msg: String) {
    intervalLog(LogLevel.Debug, LogUtil.TAG, msg)
}

fun Any.logi(tag: String, msg: String) {
    intervalLog(LogLevel.Info, tag, msg)
}

fun Any.logi(msg: String) {
    intervalLog(LogLevel.Info, LogUtil.TAG, msg)
}

fun Any.logw(tag: String, msg: String) {
    intervalLog(LogLevel.Warn, tag, msg)
}

fun Any.logw(msg: String) {
    intervalLog(LogLevel.Warn, LogUtil.TAG, msg)
}


fun Any.loge(tag: String, msg: String) {
    intervalLog(LogLevel.Error, tag, msg)
}

fun Any.loge(msg: String) {
    intervalLog(LogLevel.Error, LogUtil.TAG, msg)
}

private fun intervalLog(level: LogLevel, tag: String, msg: String) {
    if (LogUtil.debug) {
        when (level) {
            LogLevel.Verbose -> Log.v(tag, msg)
            LogLevel.Debug -> Log.d(tag, msg)
            LogLevel.Info -> Log.i(tag, msg)
            LogLevel.Warn -> Log.w(tag, msg)
            LogLevel.Error -> Log.e(tag, msg)
        }
    }
}
