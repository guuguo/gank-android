package com.guuguo.learnsave.util;

import com.guuguo.learnsave.bean.GankResult;

import rx.Subscriber;

/**
 * Created by guodeqing on 7/24/16.
 */

public abstract class MySubscriber<T extends GankResult> extends Subscriber<T> {
    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        onApiError(e.toString());
    }

    @Override
    public void onNext(T gankResult) {
        if (gankResult.getError())
            onApiError("参数或者什么地方出错了");
        else{
            onApiSuccess(gankResult);
        }
    }

    public abstract void onApiSuccess(T result) ;

    public abstract void onApiError(String str);
}
