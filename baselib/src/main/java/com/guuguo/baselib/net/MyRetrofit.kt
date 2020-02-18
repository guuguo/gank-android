package com.guuguo.fuliba.data.source.remote

import android.util.Log
import com.google.gson.Gson
import com.guuguo.android.lib.extension.log
import com.guuguo.android.pikacomic.net.https.TrustAllCerts
import com.guuguo.baselib.utils.MyHttpLoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import top.guuguo.stethomodule.StethoUtils
import java.util.concurrent.TimeUnit

/**
 * Created by gaohailong on 2016/5/17.
 */
object MyRetrofit {
    val commonHttpBuilder = OkHttpClient.Builder()
        .sslSocketFactory(TrustAllCerts.createSSLSocketFactory(), TrustAllCerts())
        .hostnameVerifier(TrustAllCerts.TrustAllHostnameVerifier())
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .also { StethoUtils.configureInterceptor(it) }
    val httpClient = commonHttpBuilder
        .addInterceptor(MyHttpLoggingInterceptor(MyHttpLoggingInterceptor.Logger { Log.i("网络",it) }).setLevel(MyHttpLoggingInterceptor.Level.BODY))
        .retryOnConnectionFailure(false)
        .build()

    fun getGsonConverter(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    private const val GANHUO_API = "https://gank.io/api/"

    fun myRetrofit(gson: Gson): Retrofit =
        retrofit2.Retrofit.Builder()
            .baseUrl(GANHUO_API)
            .addConverterFactory(getGsonConverter(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build()

}