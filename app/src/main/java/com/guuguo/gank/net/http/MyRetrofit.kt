package com.guuguo.gank.net

import com.google.gson.Gson
import com.guuguo.android.pikacomic.net.https.TrustAllCerts
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
            .sslSocketFactory(TrustAllCerts.createSSLSocketFactory())
            .hostnameVerifier(TrustAllCerts.TrustAllHostnameVerifier())
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .also { StethoUtils.configureInterceptor(it) }
    val httpClient = commonHttpBuilder
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