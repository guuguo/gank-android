package com.guuguo.gank.net

import com.guuguo.gank.model.GankDays
import com.guuguo.gank.model.Ganks
import com.guuguo.gank.model.GankNetResult
import com.guuguo.gank.model.entity.GankModel
import io.reactivex.Observable
import java.util.*

/**
 * Created by gaohailong on 2016/5/17.
 */
interface GankService {
    @retrofit2.http.GET("data/{type}/{count}/{page}")
    fun getGanHuo(@retrofit2.http.Path("type") type: String, @retrofit2.http.Path("count") count: Int, @retrofit2.http.Path("page") page: Int)
            : Observable<Ganks<ArrayList<GankModel>>>

    @retrofit2.http.GET("day/{date}")
    fun getGankOneDay(@retrofit2.http.Path("date") date: String): io.reactivex.Single<GankDays>

    @retrofit2.http.GET("search/query/{query}/category/{category}/count/{count}/page/{page} ")
    fun getGankSearchResult(@retrofit2.http.Path("query") query: String, @retrofit2.http.Path("category") category: String, @retrofit2.http.Path("count") count: Int, @retrofit2.http.Path("page") page: Int): io.reactivex.Single<GankNetResult>
}
