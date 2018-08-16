package com.guuguo.gank.base

import com.guuguo.android.lib.extension.safe

/**
 * Copyright ©2017 by ruzhan
 */

abstract class BaseListViewModel : BaseViewModel() {

    protected var page: Int = 0
    protected var isRefresh: Boolean = false

    abstract fun refreshData(refresh: Boolean)

    fun fetchData(refresh: Boolean) {
        if (refresh) {
            page = NORMAL_PAGE
        } else {
            if (isLoading.value == true || isEmpty.value == true) {
                return
            }
            page++
        }

        isRefresh = refresh
        isLoading.value = (true)
        refreshData(refresh)
    }

    companion object {
        protected const val NORMAL_PAGE = 1
        protected const val PAGE_SIZE = 15
    }
}
