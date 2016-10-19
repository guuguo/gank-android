package com.guuguo.learnsave.app

import android.app.Application

/**
 * Created by guodeqing on 16/3/7.
 */
class MyApplication : Application() {


    companion object {
        var instance: MyApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
