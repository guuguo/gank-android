package com.guuguo.android.lib.ktx

import com.guuguo.android.lib.extension.date
import com.guuguo.android.lib.extension.safe
import java.text.SimpleDateFormat
import java.util.*

/**
 * Description: 时间日期相关
 * Create by lxj, at 2018/12/7
 */

/**
 *  字符串日期格式（比如：2018-4-6)转为毫秒
 *  @param format 时间的格式，默认是按照yyyy-MM-dd HH:mm:ss来转换，如果您的格式不一样，则需要传入对应的格式
 */
fun String.toDateMills(format: String = "yyyy-MM-dd HH:mm:ss") = toDate(format)?.time.safe(0)

fun String.toDate(format: String = "yyyy-MM-dd HH:mm:ss") = try {
    SimpleDateFormat(format, Locale.getDefault()).parse(this)
} catch (e: Exception) {
    null
}

fun Date.toCalendar(): Calendar {
    val calendar = Calendar.getInstance()
    calendar.setTime(this)
    return calendar
}

fun Calendar.year()=get(Calendar.YEAR)
/**获取月份 1-12*/
fun Calendar.month()=get(Calendar.MONTH)+1
fun Calendar.day()=get(Calendar.DAY_OF_MONTH)

/**
 * Long类型时间戳转为字符串的日期格式
 * @param format 时间的格式，默认是按照yyyy-MM-dd HH:mm:ss来转换，如果您的格式不一样，则需要传入对应的格式
 */
fun Long.toDateString(format: String = "yyyy-MM-dd HH:mm:ss") = SimpleDateFormat(format, Locale.getDefault()).format(Date(this))

fun Int.toDateString(format: String = "yyyy-MM-dd HH:mm:ss") = SimpleDateFormat(format, Locale.getDefault()).format(Date(this.toLong()))

fun Date.isToday() = this.date() == Date().date()

//获取某日0点距离今日0点的天数
fun Long.tillTodayDayCeil():Int{
    //当天零点
    val calendar1 = Calendar.getInstance()
    calendar1[Calendar.HOUR_OF_DAY] = 0
    calendar1[Calendar.MINUTE] = 0
    calendar1[Calendar.SECOND] = 0
    calendar1[Calendar.MILLISECOND] = 0
    val startDate = calendar1.timeInMillis

    //该天零点
    val calendar2 = Calendar.getInstance()
    calendar2.timeInMillis = this
    calendar2[Calendar.HOUR_OF_DAY] = 0
    calendar2[Calendar.MINUTE] = 0
    calendar2[Calendar.SECOND] = 0
    calendar2[Calendar.MILLISECOND] = 0
    val endTime = calendar2.timeInMillis

    return ((endTime - startDate) / (24 * 3600) / 1000).toInt()
}