package com.guuguo.gank.model.other
import android.support.annotation.IntDef


class RefreshListModel<T> {
    var list: List<T>? = null

    @RefreshType
    var refreshType: Int = REFRESH

    val isUpdateType: Boolean
        get() = UPDATE == refreshType

    @IntDef(REFRESH, UPDATE)
    @Retention(AnnotationRetention.SOURCE)
    annotation class RefreshType

    fun setRefresh() {
        refreshType = REFRESH
    }

    fun setUpdate() {
        refreshType = UPDATE
    }

    fun setRefresh(list: List<T>) {
        refreshType = REFRESH
        this.list = list
    }

    fun setUpdate(list: List<T>) {
        refreshType = UPDATE
        this.list = list
    }

    companion object {
        const val REFRESH = 3001 // refresh
        const val UPDATE = 3002 // update
    }
}
