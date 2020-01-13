package com.bianla.commonlibrary.extension

import android.graphics.Color

/**
 * 获取格式化 保留[decimalLength] 位数的小数点，[intOptimization]true 代表整数不显示小数点*/
fun Number.formatDecimal(decimalLength: Int = 1, intOptimization: Boolean = true): String {
    if (intOptimization && this.toFloat() - this.toInt() == 0f)
        return this.toInt().toString()
    return String.format("%.${decimalLength}f", this.toDouble())
}
/**
 * 获取格式化 保留[decimalLength] 位数的小数点，[intOptimization]true 代表整数不显示小数点*/
fun Number.formatDecimalToFloat(decimalLength: Int = 1, intOptimization: Boolean = true): Float {
    "".toFloatOrNull()
    if (intOptimization && this.toFloat() - this.toInt() == 0f)
        return this.toInt().toFloat()
    return String.format("%.${decimalLength}f", this.toDouble()).toFloat()
}

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
