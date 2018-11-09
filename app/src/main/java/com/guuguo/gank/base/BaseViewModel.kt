package com.guuguo.gank.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.databinding.BaseObservable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList

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
