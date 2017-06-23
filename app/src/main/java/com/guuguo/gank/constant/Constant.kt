package com.guuguo.gank.constant

import com.google.gson.GsonBuilder
import java.util.*

/**
 * Created by guodeqing on 7/2/16.
 */

// 默认每页加载数量
val MEIZI_COUNT = 12
val GANHUO_API = "https://gank.io/api/"

val MEIZI = "meizi"
val GANK_URL = "gank_url"
val GANK_TITLE = "gank_title"
val GANK = "gank"

val dataPattern = "yyyy-MM-dd'T'HH:mm:ss"

val myGson by lazy {
    GsonBuilder().setDateFormat(dataPattern).create()
}

