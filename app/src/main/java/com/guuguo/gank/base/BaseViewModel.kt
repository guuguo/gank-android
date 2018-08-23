package com.guuguo.gank.base

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.databinding.BaseObservable
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableList

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
