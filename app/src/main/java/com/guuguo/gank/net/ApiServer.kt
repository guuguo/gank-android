package com.guuguo.gank.net

import com.google.gson.Gson
import com.guuguo.android.lib.extension.date
import com.guuguo.gank.constant.myGson
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by guodeqing on 7/14/16.
 */
object ApiServer {
    val TYPE_FULI = "福利"
    val TYPE_ANDROID = "Android"
    val TYPE_IOS = "iOS"
    val TYPE_REST = "休息视频"
    val TYPE_EXPAND = "拓展资源"
    val TYPE_FRONT = "前端"
    val TYPE_ALL = "all"
    fun <T> Flowable<T>.api(): Flowable<T> {
        return this.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun gankServer(gson: Gson = myGson) = MyRetrofit.myRetrofit(gson).create(Service::class.java)
    fun getGankData(type: String, count: Int, page: Int) = gankServer().getGanHuo(type, count, page).api()
    fun getGankOneDayData(date: Date)= gankServer().getGankOneDay(date.date()).api()
    fun getGankSearchResult(query: String, category: String, count: Int, page: Int) = gankServer().getGankSearchResult(query, category, count, page).api()
}
