package com.guuguo.fuliba.data.source.remote

import com.google.gson.GsonBuilder
import com.guuguo.fuliba.data.source.remote.MyRetrofit.httpClient
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by gaohailong on 2016/5/17.
 */
interface FulibaService {
    /**福利吧[page] 从第一页开始*/
    @GET("/page/{page}")
    suspend fun getFulibaList(@Path("page") page:Int): ResponseBody

    /**福利吧*/
    @GET("{path}")
    suspend fun getPath(@Path("path") path: String): ResponseBody


    companion object {
        private const val API = "https://fulibus.net"
        fun server(): FulibaService = server(API.toHttpUrlOrNull()!!)
        fun server(httpUrl: HttpUrl): FulibaService {

            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(httpClient)
                .addConverterFactory(getGsonConverter())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(FulibaService::class.java)
        }

        fun getGsonConverter(): GsonConverterFactory {
            return GsonConverterFactory
                .create(GsonBuilder().create())
        }
    }
}
