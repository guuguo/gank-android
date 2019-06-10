package com.guuguo.gank.util

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.guuguo.gank.app.App
import com.lxj.androidktx.AndroidKtxConfig.context
import com.lxj.androidktx.core.mmkv
import com.tencent.mmkv.MMKV
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by 大哥哥 on 2016/10/15 0015.
 */
class KV<T>(var default: T, var typeToken: TypeToken<T>? = null) : ReadWriteProperty<Any?, T> {

    companion object {
        var appName = App.get().packageName.replace('.', '_')
        var dateFormat = "yyyy-MM-dd hh:mm:ss"
        var gson = GsonBuilder().setDateFormat(dateFormat).create()
        fun init(appName: String) {
            Companion.appName = appName
        }
    }

    val mmkv by lazy {
        MMKV.initialize(App.get())
        mmkv()
    }
    var mValue: T? = null
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if (mValue == null)
            mValue = findPreference(property.name, default)
        return mValue!!
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (mValue != value || when (value) {is Long, is String, is Int, is Boolean, is Float -> false
            else -> true
        }) putPreference(property.name, value)
        mValue = value
    }

    private fun <U> findPreference(name: String, default: U): U = with(mmkv) {
        val res: Any? = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> {
                if (typeToken != null) {
                    gson.fromJson<U>(getString(name, ""), typeToken!!.type) ?: default
                } else {
                    throw IllegalArgumentException("typeToken is null")
                }
            }
        }
        res as U
    }

    private fun putPreference(name: String, value: T) = with(mmkv.edit())
    {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> {
                putString(name, gson.toJson(value))
            }
        }.apply()
    }
}