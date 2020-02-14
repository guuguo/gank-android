package com.guuguo.gank.model.other

import androidx.annotation.IntDef


class RefreshListModel<T> {
    var list: MutableList<T>? = null

    @RefreshType
    var refreshType: Int = REFRESH

    fun isRefresh() = refreshType == REFRESH
    var isEnd: Boolean = false

    @IntDef(REFRESH, UPDATE)
    @Retention(AnnotationRetention.SOURCE)
    annotation class RefreshType

    fun setRefresh() {
        isEnd = false
        refreshType = REFRESH
    }

    fun setUpdate() {
        refreshType = UPDATE
    }

    fun setRefresh(list: MutableList<T>) {
        setRefresh()
        this.list = list
    }

    fun setUpdate(list: MutableList<T>) {
        setUpdate()
        this.list = list
    }

    companion object {
        const val REFRESH = 3001 // refresh
        const val UPDATE = 3002 // update
    }
}
