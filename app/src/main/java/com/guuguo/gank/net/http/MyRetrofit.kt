package com.guuguo.gank.net

import com.guuguo.android.pikacomic.net.https.TrustAllCerts
import com.guuguo.gank.constant.GANHUO_API
import com.guuguo.gank.constant.myGson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by gaohailong on 2016/5/17.
 */
object MyRetrofit {
    val commonHttpBuilder = OkHttpClient.Builder()
            .sslSocketFactory(TrustAllCerts.createSSLSocketFactory())
            .hostnameVerifier(TrustAllCerts.TrustAllHostnameVerifier())
            .connectTimeout(10, TimeUnit.MINUTES)
            .readTimeout(10, TimeUnit.MINUTES)
    val httpClient = commonHttpBuilder
            .retryOnConnectionFailure(false)
            .build()

    fun getGsonConverter(): GsonConverterFactory {
        return retrofit2.converter.gson.GsonConverterFactory.create(myGson)
    }


    val myRetrofit: Retrofit by lazy {
        retrofit2.Retrofit.Builder()
                .baseUrl(GANHUO_API)
                .addConverterFactory(getGsonConverter())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(MyRetrofit.httpClient)
                .build()
    }

}