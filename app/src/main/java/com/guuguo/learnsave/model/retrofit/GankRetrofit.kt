package com.guuguo.learnsave.model.retrofit

import com.google.gson.Gson
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
    fun getGsonConver(gsonBuilder: GsonBuilder?): GsonConverterFactory {
        var gson: Gson = GsonBuilder().setDateFormat(pattern).create();
        if (gsonBuilder != null)
            gson = gsonBuilder?.setDateFormat(pattern).create()
        return GsonConverterFactory.create(gson)
    }

    fun getRetrofit(gsonBuilder: GsonBuilder? = null): Retrofit {
        return Retrofit.Builder()
                .baseUrl(GANHUO_API)
                .addConverterFactory(getGsonConver(gsonBuilder))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
    }
}
