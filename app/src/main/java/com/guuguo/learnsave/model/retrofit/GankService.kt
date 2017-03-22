package com.guuguo.learnsave.model.retrofit

import com.guuguo.learnsave.model.GankDays
import com.guuguo.learnsave.model.Ganks
import com.guuguo.learnsave.model.SearchResult
import com.guuguo.learnsave.model.entity.SearchResultModel
import io.reactivex.Single

import retrofit2.http.GET
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
