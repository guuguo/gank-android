package com.guuguo.gank.model.retrofit

import com.guuguo.gank.model.GankDays
import com.guuguo.gank.model.Ganks
import com.guuguo.gank.model.SearchResult
import com.guuguo.gank.model.entity.SearchResultModel
import io.reactivex.Single
import retrofit2.http.FormUrlEncoded

import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.Path

/**
 * Created by gaohailong on 2016/5/17.
 */
interface GankService {
    @GET("data/{type}/{count}/{page}")
    fun getGanHuo(@Path("type") type: String, @Path("count") count: Int, @Path("page") page: Int): Single<Ganks>

    @GET("day/{date}")
    fun getGankOneDay(@Path("date") date: String): Single<GankDays>

    @GET("search/query/{query}/category/{category}/count/{count}/page/{page} ")
    fun getGankSearchResult(@Path("query") query: String, @Path("category") category: String, @Path("count") count: Int, @Path("page") page: Int): Single<SearchResult>
}
