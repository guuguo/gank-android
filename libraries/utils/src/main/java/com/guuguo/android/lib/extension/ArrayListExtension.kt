package com.guuguo.android.lib.extension

import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by 大哥哥 on 2016/10/21 0021.
 */

fun <T> List<T>?.safe(): List<T> {
    if (this == null)
        return ArrayList()
    else {
        return this;
    }
}
fun <T> ArrayList<T>?.safe(): ArrayList<T> {
    if (this == null)
        return ArrayList()
    else {
        return this;
    }
}
fun <T> MutableList<T>?.safeMl(): MutableList<T> {
    if (this == null)
        return mutableListOf()
    else {
        return this;
    }
}

fun <K, V> Map<K, V>?.safe(): Map<K, V> {
    if (this == null)
        return HashMap()
    else {
        return this;
    }
}

fun <T> List<T>?.toListString(separate: String = "  "): String {
    if (this == null)
        return ""
    val str = StringBuilder()
    repeat(this.size - 1)
    {
        str.append(this[it].toString() + separate)
    }
    str.append(this.last().toString())
    return str.toString()
}
