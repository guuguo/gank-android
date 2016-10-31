package com.guuguo.learnsave.extension

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by 大哥哥 on 2016/10/21 0021.
 */
fun Date.getDateSimply(): String {

    var format = SimpleDateFormat("yyyy/MM/dd").format(this)
    when {
        format.equals(Date().date()) ->
            return "今天"
        format.equals(Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000).date()) ->
            return "昨天"
        else ->
            return format
    }
}

fun Date.date(): String {
    return SimpleDateFormat("yyyy/MM/dd").format(this)
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
