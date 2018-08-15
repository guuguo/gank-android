package com.guuguo.gank.app.viewmodel

import android.databinding.BaseObservable
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableList

import io.reactivex.disposables.CompositeDisposable

/**
 * Copyright ©2017 by ruzhan
 */

open class BaseViewModel : BaseObservable() {

    var isError = ObservableField<Throwable>()
    var isEmpty = ObservableBoolean(false)
    var isRunning = ObservableBoolean(false)

    protected open var disposable = CompositeDisposable()

    protected open fun checkEmpty(`object`: Any) {
        checkEmpty(isEmpty, `object`)
    }

    protected open fun checkEmpty(isEmpty: ObservableBoolean, `object`: Any?) {
        val emptyFlag = `object` == null
        if (emptyFlag != isEmpty.get()) {
            isEmpty.set(emptyFlag)
        }
    }

    protected open fun checkEmpty(list: List<*>) {
        checkEmpty(isEmpty, list)
    }

    protected open fun checkEmpty(isEmpty: ObservableBoolean, list: List<*>?) {
        val emptyFlag = list == null || list.isEmpty()
        if (emptyFlag != isEmpty.get()) {
            isEmpty.set(emptyFlag)
        }
    }

    protected open fun <T> setDataObject(`object`: T, field: ObservableField<T>) {
        setDataObject(isEmpty, `object`, field)
    }

    protected open fun <T> setDataObject(isEmpty: ObservableBoolean?, `object`: T,
                                         field: ObservableField<T>?) {
        if (field == null || isEmpty == null) {
            return
        }
        checkEmpty(isEmpty, `object`)
        if (!isEmpty.get()) {
            field.set(`object`)
        }
    }

    protected open fun <T> setDataList(list: List<T>, observableList: ObservableList<T>?) {
        setDataList(isEmpty, list, observableList)
    }

    protected open fun <T> setDataList(isEmpty: ObservableBoolean?, list: List<T>, observableList: ObservableList<T>?) {
        if (observableList == null || isEmpty == null) {
            return
        }
        checkEmpty(isEmpty, list)
        if (!isEmpty.get()) {
            observableList.clear()
            observableList.addAll(list)
        }
    }

    fun clear() {
        disposable.clear()
    }

    companion object {

        protected val TOKEN = "f32b30c2a289bfca2c9857ffc5871ac8"
    }
}
