package com.guuguo.android.lib.extension

import com.guuguo.android.lib.ktx.isToday
import com.guuguo.android.lib.ktx.toDate
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by 大哥哥 on 2016/10/21 0021.
 */

/**
 * 获取该时间离现在多久

 * @param format
 * *
 * @param dateTime
 * *
 * @return
 */
fun Date.getTimeSpan(timeStamp: Long = System.currentTimeMillis(), suffix: String = "前",prefix:String=""): String {
    var timeSpan = timeStamp - time
    timeSpan /= 1000
    if (timeSpan < 60)
        return prefix+timeSpan.toString() + "秒$suffix"
    timeSpan /= 60
    if (timeSpan < 60) {
        return prefix+timeSpan.toString() + "分钟$suffix"
    }
    timeSpan /= 60
    if (timeSpan < 24) {
        return prefix+timeSpan.toString() + "小时$suffix"
    }
    timeSpan /= 24
    if (timeSpan < 30) {
        return prefix+timeSpan.toString() + "天$suffix"
    }
    timeSpan = (timeSpan / 30.42).toLong()
    if (timeSpan < 12) {
        return prefix+timeSpan.toString() + "月$suffix"
    }
    timeSpan /= 12
    return prefix+timeSpan.toString() + "年$suffix"
}

/**
 * 获取毫秒为单位时间戳的离现在多久

 * @param format
 * *
 * @param dateTime
 * *
 * @return
 */
fun Date.getTimeSpanUntilDay(): String {
    val timeSpan = getTimeSpan()
    if (timeSpan.contains("月") || timeSpan.contains("年"))
        return date('-')
    else return timeSpan
}

fun Date.date(char: Char = '/'): String {
    return SimpleDateFormat("yyyy${char}MM${char}dd").format(this)
}

fun Date.year(): String {
    return SimpleDateFormat("yyyy").format(this)
}

fun Date.month(): String {
    return SimpleDateFormat("MM").format(this)
}

fun Date.day(): String {
    return SimpleDateFormat("dd").format(this)
}
fun Date.hour(): String {
    return SimpleDateFormat("HH").format(this)
}
fun Date.minute(): String {
    return SimpleDateFormat("mm").format(this)
}
fun Date.second(): String {
    return SimpleDateFormat("ss").format(this)
}
fun Date?.formatTime(format: String): String {
    if (this == null)
        return ""
    return SimpleDateFormat(format).format(this)
}
fun Long?.formatTime(format: String): String {
    if (this == null)
        return ""
    return SimpleDateFormat(format).format(Date(this))
}
fun Long?.formatTimeBySecond(format: String): String {
    if (this == null)
        return ""
    return (this * 1000).formatTime(format)
}
fun Date.formatTime(format: SimpleDateFormat): String {
    return format.format(this)
}
fun String.isTodayDate(format: String): Boolean? {
    return this.toDate(format)?.isToday()
}