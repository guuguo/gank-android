package com.guuguo.gank.app.viewmodel

import android.databinding.ObservableBoolean
import android.databinding.ObservableList

/**
 * Copyright ©2017 by ruzhan
 */

abstract class BaseListViewModel : BaseViewModel() {

    protected var page: Int = 0
    protected var isRefresh: Boolean = false
        set(value) {
            field = value
            if (value)  // 网络请求开始显示下拉刷新
                refreshing.set(true)
        }
    var refreshing = ObservableBoolean(false) // 提供给下拉刷新

    abstract fun refreshData(refresh: Boolean)

    fun fetchData(refresh: Boolean) {
        if (refresh) {
            if (isRunning.get()) {
                refreshing.set(false)
                return
            }
            page = NORMAL_PAGE

        } else {
            if (isRunning.get() || isEmpty.get()) {
                return
            }
            page++
        }

        isRefresh = refresh
        refreshData(refresh)
    }

    override fun <T> setDataList(list: List<T>, observableList: ObservableList<T>?) {
        if (observableList == null) {
            return
        }
        checkEmpty(list)
        if (!isEmpty.get()) {
            if (isRefresh) {
                observableList.clear()
            }
            observableList.addAll(list)

            if (list.size < PAGE_SIZE) {
                isEmpty.set(true)
            }
        }
    }

    companion object {

        protected val NORMAL_PAGE = 1
        protected val PAGE_SIZE = 10
    }
}
