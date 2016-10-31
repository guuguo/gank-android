package com.guuguo.learnsave.util

/**
 * Created by guodeqing on 7/2/16.
 */

// 默认每页加载数量
val DEFAULT_COUNT = 12
val GANHUO_API = "https://gank.io/api/"

val MEIZI = "meizi"
val GANK_URL = "gank_url"
val GANK_TITLE = "gank_title"
val GANK = "gank"
val TRANSLATE_GIRL_VIEW = "share_girl"

fun getJson(response: String): String {
    return response.replace("<.*?>".toRegex(), "")
}

