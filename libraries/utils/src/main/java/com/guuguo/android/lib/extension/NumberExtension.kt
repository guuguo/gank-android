package com.guuguo.android.lib.extension

import android.content.Context
import android.content.res.Resources
import android.graphics.Color

/**
 * Created by 大哥哥 on 2016/10/21 0021.
 */


fun Int.pxToDp(): Int {
    return (this / Resources.getSystem().displayMetrics.density + 0.5f).toInt()
}

fun Int.dpToPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
}

fun Int.dpToPxF(): Float {
    return (this * Resources.getSystem().displayMetrics.density + 0.5f)
}

fun Float.pxToDp(): Int {
    return (this / Resources.getSystem().displayMetrics.density + 0.5f).toInt()
}

fun Float.dpToPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
}

fun Float.spToPx(): Int {
    return (this * Resources.getSystem().displayMetrics.scaledDensity + 0.5f).toInt()
}


fun Int?.safe(default: Int = 0) = this ?: default
fun Long?.safe(default: Long = 0) = this ?: default
fun Boolean?.safe(default: Boolean = false) = this ?: default
fun Float?.safe(default: Float = 0f) = this ?: default

fun Int?.safeNoZero(default: Int = 0) = if (this == 0) default else this ?: default
fun Long?.safeNoZero(default: Long = 0) = if (this == 0L) default else this ?: default
fun Float?.safeNoZero(default: Float = 0f) = if (this == 0f) default else this ?: default
//fun Long.getFitSize(byte2FitMemorySize: Int = 1): String = FileUtil.byte2FitMemorySize(this, byte2FitMemorySize)
//color
/**
 * 修改颜色透明度
 * @param color
 * *
 * @param alpha
 * *
 * @return
 */
fun Int.changeAlpha(alpha: Int): Int {
    val red = Color.red(this)
    val green = Color.green(this)
    val blue = Color.blue(this)

    return Color.argb(alpha, red, green, blue)
}

/**
 * 获取格式化 保留[decimalLength] 位数的小数点，[intOptimization]true 代表整数不显示小数点
 */
fun Number.formatDecimal(decimalLength: Int = 1, intOptimization: Boolean = true): String {
    if (intOptimization && this.toFloat() - this.toInt() == 0f)
        return this.toInt().toString()
    return String.format("%.${decimalLength}f", this.toDouble())
}