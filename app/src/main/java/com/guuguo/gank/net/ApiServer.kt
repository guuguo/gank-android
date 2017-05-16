package com.guuguo.gank.net

import com.guuguo.android.lib.extension.date
import com.guuguo.gank.model.*
import com.guuguo.gank.model.entity.GankModel
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

    val gankServer by lazy { GankRetrofit.getRetrofit().create(GankService::class.java) }
    fun getGankData(type: String, count: Int, page: Int): Observable<Ganks<ArrayList<GankModel>>> = gankServer
            .getGanHuo(type, count, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getGankOneDayData(date: Date): Single<GankDays> {
        val dateStr = date.date()
        return gankServer
                .getGankOneDay(dateStr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getGankSearchResult(query: String, category: String, count: Int, page: Int): Single<GankNetResult> = gankServer
            .getGankSearchResult(query, category, count, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
