package com.guuguo.gank.app.gank.viewmodel

import androidx.lifecycle.MutableLiveData
import com.guuguo.gank.app.gank.fragment.GankCategoryContentFragment
import com.guuguo.gank.base.BaseListViewModel
import com.guuguo.gank.constant.ACacheTransformF
import com.guuguo.gank.constant.MEIZI_COUNT
import com.guuguo.gank.model.GankDays
import com.guuguo.gank.model.Ganks
import com.guuguo.gank.model.entity.GankModel
import com.guuguo.gank.model.other.RefreshListModel
import com.guuguo.gank.net.ApiServer
import com.guuguo.gank.net.EmptyConsumer
import com.guuguo.gank.net.ErrorConsumer
import com.guuguo.gank.source.GankRepository


/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class GankCategoryContentViewModel : BaseListViewModel() {
    var gank_type = "Android"
    private val TAG = this::class.java.name

    private val refreshListModel = RefreshListModel<GankModel>()
    val ganksListLiveData = MutableLiveData<RefreshListModel<GankModel>>()

    override fun refreshData(refresh: Boolean) {
        when (gank_type) {
            GankCategoryContentFragment.GANK_TYPE_STAR -> {
                GankRepository.getStarGanks()
                        .doOnSubscribe { isLoading.value = true }
                        .doOnSuccess(this::showMeiziList)
                        .doOnError { isError.value = it;isLoading.value = false }
                        .subscribe(EmptyConsumer(), ErrorConsumer())
            }
            else -> {
                ApiServer.getGankData(gank_type, MEIZI_COUNT, page)
                        .compose(ACacheTransformF<Ganks<ArrayList<GankModel>>>("getGankData$gank_type$page").fromCacheIfValide())
                        .doOnSubscribe { isLoading.value = true }
                        .doOnTerminate { isLoading.value = false }
                        .doOnError { isLoading.value = false;isError.value = it }
                        .doOnNext {
                            it?.let {
                                showMeiziList(it.first.results!!)
                            }
                        }
                        .subscribe(EmptyConsumer(), ErrorConsumer())
            }
        }
    }

    fun showMeiziList(lMeiziList: List<GankModel>) {
        if (lMeiziList.size < MEIZI_COUNT) {
            refreshListModel.isEnd = true
        }

        isEmpty.value = isRefresh && lMeiziList.isEmpty()

        if (page == 1) {
            refreshListModel.setRefresh(lMeiziList)
        } else {
            refreshListModel.setUpdate(lMeiziList)
        }
        ganksListLiveData.value = refreshListModel
        isLoading.value = false
    }


}

