package com.guuguo.gank.app.gank.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.guuguo.android.lib.extension.safe
import com.guuguo.gank.base.BaseListViewModel
import com.guuguo.gank.constant.MEIZI_COUNT
import com.guuguo.gank.model.entity.GankModel
import com.guuguo.gank.model.other.RefreshListModel
import com.guuguo.gank.net.ApiServer
import com.guuguo.gank.net.EmptyConsumer
import com.guuguo.gank.net.ErrorConsumer


/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class SearchViewModel : BaseListViewModel() {
    private val TAG = this::class.java.name

    private val refreshListModel = RefreshListModel<GankModel>()
    val ganksListLiveData = MutableLiveData<RefreshListModel<GankModel>>()
    var searchText = ""

    override fun refreshData(refresh: Boolean) {
        ApiServer.getGankSearchResult(searchText, ApiServer.TYPE_ALL, MEIZI_COUNT, page)
                .doOnSubscribe { isLoading.value = true }
                .doOnTerminate { isLoading.value = false }
                .doOnError { isError.value = it }
                .doOnNext { searchResult ->
                    if (page == 1) {
                        if (searchResult.count == 0)
                            isEmpty.value = true
                        else {
                            isEmpty.value = false
                            refreshListModel.setRefresh(searchResult.results.safe())
                        }
                    } else {
                        isEmpty.value = false
                        refreshListModel.setUpdate(searchResult.results!!)
                    }
                    ganksListLiveData.value = refreshListModel
                }
                .subscribe(EmptyConsumer(), ErrorConsumer())
                .isDisposed

    }
}
