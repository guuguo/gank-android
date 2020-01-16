package com.guuguo.gank.presenter;

import android.content.Context;

import com.guuguo.gank.view.IBaseView;

import io.reactivex.disposables.Disposable;

/**
 * 基础presenter
 * Created by panl on 15/12/24.
 */
public abstract class BasePresenter<T extends IBaseView> {
    protected Disposable subscription;
    protected Context context;
    protected T iView;

    public BasePresenter(Context context, T iView) {
        this.context = context;
        this.iView = iView;
    }

    public void init() {
        iView.initIView();
    }

    public void release() {
        if (subscription != null && !subscription.isDisposed())
            subscription.dispose();
    }

    ;

}
