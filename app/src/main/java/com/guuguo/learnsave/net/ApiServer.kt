package com.guuguo.learnsave.net

import com.guuguo.learnsave.bean.GankDays
import com.guuguo.learnsave.bean.Ganks
import com.guuguo.learnsave.net.retrofit.GankRetrofit
import com.guuguo.learnsave.net.retrofit.GankService
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

    fun getGankData(type: String, count: Int, page: Int): Observable<Ganks> {
        return GankRetrofit.getRetrofit().create(GankService::class.java)
                .getGanHuo(type, count, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getGankOneDayData(date: Date): Observable<GankDays> {
        val dateStr = SimpleDateFormat("yyyy/MM/dd").format(date)
        return GankRetrofit.getRetrofit().create(GankService::class.java)
                .getGankOneDay(dateStr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
