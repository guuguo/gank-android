package com.guuguo.android.lib.utils.cache;

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.guuguo.android.lib.BaseApplication
import com.guuguo.android.lib.extension.safe
import java.io.Serializable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Project: bianla_android
 *
 * @author : guuguo
 * @since 2018/05/30
 */
class AppACache<T>(var default: T? = null, var typeToken: TypeToken<T>? = null) : ReadWriteProperty<Any?, T> {

    companion object {
        val USER_INFO_TAB_NAME = "user_tab"
        var appName = USER_INFO_TAB_NAME
        var dateFormat = "yyyy-MM-dd hh:mm:ss"
        var gson = GsonBuilder().setDateFormat(dateFormat).create()
        fun init(appName: String) {
            Companion.appName = appName
        }
    }

    val aCache by lazy {
        ACache.get(BaseApplication.get())
    }
    var mValue: T? = null
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if (mValue == null)
            mValue = findPreference(property.name, default)
        return mValue!!
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(property.name, value)
        mValue = value
    }

    private fun <U> findPreference(name: String, default: U?): U? = with(aCache) {
        val res: Any? = when (default) {
            is Long -> getAsString(name).toLong().safe()
            is String -> getAsString(name).toString().safe()
            is Int -> getAsString(name).toInt().safe()
            is Boolean -> getAsString(name).toBoolean().safe()
            is Float -> getAsString(name).toFloat().safe()
            is Serializable -> getAsObject(name) ?: default
            else -> {
                if (typeToken != null) {
                    gson.fromJson<U>(getAsString(name), typeToken!!.type) ?: default
                } else {
                    throw IllegalArgumentException("typeToken is null")
                }
            }
        }
        res as U?
    }

    private fun putPreference(name: String, value: T) = with(aCache)
    {
        when (value) {
            is Long -> put(name, value)
            is String -> put(name, value)
            is Int -> put(name, value)
            is Boolean -> put(name, value)
            is Float -> put(name, value)
            is Serializable -> put(name, value)
            else -> {
                put(name, gson.toJson(value))
            }
        }
    }
}