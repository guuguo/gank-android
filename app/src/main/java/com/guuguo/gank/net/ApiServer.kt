package com.guuguo.gank.net

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.guuguo.android.lib.extension.date
import com.guuguo.gank.R.id.date
import com.guuguo.gank.constant.dataPattern
import com.guuguo.gank.constant.myGson
import com.guuguo.gank.model.*
import com.guuguo.gank.model.entity.GankModel
import com.guuguo.gank.net.ApiServer.gankServer
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
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
