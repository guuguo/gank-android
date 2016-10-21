package com.guuguo.learnsave.model.retrofit

import com.google.gson.GsonBuilder
import com.guuguo.learnsave.util.GANHUO_API

import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by gaohailong on 2016/5/17.
 */
object GankRetrofit {
    val pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    val gsonConver by lazy { GsonConverterFactory.create(GsonBuilder().setDateFormat(pattern).create()) }
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(GANHUO_API)
                .addConverterFactory(gsonConver)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
    }
    
}
