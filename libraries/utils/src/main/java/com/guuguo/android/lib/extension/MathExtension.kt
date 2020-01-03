package com.guuguo.android.lib.extension

/**
 * Created by 大哥哥 on 2016-11-21.
 */

fun squareRoot(a: Int, b: Int): Float {
    return Math.sqrt(Math.pow(a.toDouble(), 2.0) + Math.pow(b.toDouble(), 2.0)).toFloat()
}