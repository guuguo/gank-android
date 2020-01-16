package com.guuguo.gank.util

import com.guuguo.gank.model.GankResult
import io.reactivex.SingleObserver


/**
 * Created by guodeqing on 7/24/16.
 */

abstract class MySubscriber<T : GankResult> : SingleObserver<T> {
    override fun onError(e: Throwable) {
        onApiError(e.toString())
    }

    override fun onSuccess(gankResult: T) {
        if (gankResult.error)
            onApiError("参数或者什么地方出错了")
        else {
            onApiSuccess(gankResult)
        }
    }

    abstract fun onApiSuccess(result: T)

    abstract fun onApiError(str: String)
}
