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
interface FulibaRemoteDataSource {

}
