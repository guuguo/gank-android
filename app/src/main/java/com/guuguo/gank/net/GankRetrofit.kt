package com.guuguo.gank.net

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.guuguo.gank.app.GANHUO_API
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by gaohailong on 2016/5/17.
 */
object GankRetrofit {
    val pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    fun getGsonConver(gsonBuilder: com.google.gson.GsonBuilder?): retrofit2.converter.gson.GsonConverterFactory {
        var gson: com.google.gson.Gson = com.google.gson.GsonBuilder().setDateFormat(com.guuguo.gank.net.GankRetrofit.pattern).create();
        if (gsonBuilder != null)
            gson = gsonBuilder.setDateFormat(com.guuguo.gank.net.GankRetrofit.pattern).create()
        return retrofit2.converter.gson.GsonConverterFactory.create(gson)
    }

    fun getRetrofit(gsonBuilder: com.google.gson.GsonBuilder? = null): retrofit2.Retrofit {
        return retrofit2.Retrofit.Builder()
                .baseUrl(com.guuguo.gank.app.GANHUO_API)
                .addConverterFactory(com.guuguo.gank.net.GankRetrofit.getGsonConver(gsonBuilder))
                .addCallAdapterFactory(com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory.create())
                .build()
    }
}
