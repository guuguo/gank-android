package com.guuguo.gank.base

import android.databinding.ObservableBoolean
import android.databinding.ObservableList

/**
 * Copyright ©2017 by ruzhan
 */

abstract class BaseListViewModel : BaseViewModel() {

    protected var page: Int = 0
    protected var isRefresh: Boolean = false

    var refreshing = ObservableBoolean(false) // 提供给下拉刷新

    abstract fun refreshData(refresh: Boolean)

    fun fetchData(refresh: Boolean) {
        if (refresh) {
//            if (isRunning.get()) {
//                refreshing.set(false)
//                return
//            }
            page = NORMAL_PAGE

        } else {
            if (refreshing.get() || isEmpty.get()) {
                return
            }
            page++
        }

        isRefresh = refresh
        refreshing.set(true)
        refreshData(refresh)
    }

    companion object {

        protected const val NORMAL_PAGE = 1
        protected const val PAGE_SIZE = 10
    }
}
