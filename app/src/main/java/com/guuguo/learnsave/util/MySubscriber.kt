package com.guuguo.learnsave.util

import com.guuguo.learnsave.model.GankResult

import rx.Subscriber

/**
 * Created by guodeqing on 7/24/16.
 */

abstract class MySubscriber<T : GankResult> : Subscriber<T>() {
    override fun onCompleted() {
    }

    override fun onError(e: Throwable) {
        onApiError(e.toString())
    }

    override fun onNext(gankResult: T) {
        if (gankResult.error)
            onApiError("参数或者什么地方出错了")
        else {
            onApiSuccess(gankResult)
        }
    }

    abstract fun onApiSuccess(result: T)

    abstract fun onApiError(str: String)
}
