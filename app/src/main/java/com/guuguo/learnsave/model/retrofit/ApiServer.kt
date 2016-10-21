package com.guuguo.learnsave.model.retrofit

import com.guuguo.learnsave.extension.date
import com.guuguo.learnsave.model.GankDays
import com.guuguo.learnsave.model.Ganks
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.text.SimpleDateFormat
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

    val gankServer by lazy { GankRetrofit.retrofit.create(GankService::class.java) }
    fun getGankData(type: String, count: Int, page: Int): Observable<Ganks> {
        return gankServer
                .getGanHuo(type, count, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getGankOneDayData(date: Date): Observable<GankDays> {
        val dateStr = date.date()
        return gankServer
                .getGankOneDay(dateStr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
