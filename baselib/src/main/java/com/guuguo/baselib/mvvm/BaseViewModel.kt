package com.guuguo.baselib.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.databinding.BaseObservable

import io.reactivex.disposables.CompositeDisposable

/**
 * Copyright ©2017 by ruzhan
 */

open class BaseViewModel : BaseObservable() {

    var isError = MutableLiveData<Throwable>()
    var isEmpty =MutableLiveData<Boolean>()
    var isLoading = MutableLiveData<Boolean>()

    protected open var disposable = CompositeDisposable()

    fun clear() {
        disposable.clear()
    }

}
