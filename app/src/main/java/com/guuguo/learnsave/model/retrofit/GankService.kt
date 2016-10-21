package com.guuguo.learnsave.model.retrofit

import com.guuguo.learnsave.model.GankDays
import com.guuguo.learnsave.model.Ganks

import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 * Created by gaohailong on 2016/5/17.
 */
interface GankService {
    @GET("data/{type}/{count}/{page}")
    fun getGanHuo(@Path("type") type: String, @Path("count") count: Int, @Path("page") page: Int): Observable<Ganks>

    @GET("day/{date}")
    fun getGankOneDay(@Path("date") date: String): Observable<GankDays>

    val gankDates: Observable<GankDays>
}
