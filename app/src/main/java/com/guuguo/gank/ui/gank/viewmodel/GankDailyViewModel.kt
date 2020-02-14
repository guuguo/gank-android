package com.guuguo.gank.ui.gank.viewmodel

import androidx.lifecycle.MutableLiveData
import com.google.gson.reflect.TypeToken
import com.guuguo.android.lib.extension.safe
import com.guuguo.baselib.mvvm.BaseListViewModel
import com.guuguo.gank.constant.AppLocalData
import com.guuguo.gank.constant.MEIZI_COUNT
import com.guuguo.gank.constant.myGson
import com.guuguo.gank.model.Ganks
import com.guuguo.gank.model.entity.GankModel
import com.guuguo.gank.model.other.RefreshListModel
import com.guuguo.gank.net.ApiServer
import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import java.util.*


/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class GankDailyViewModel : BaseListViewModel() {
    override fun refreshData(refresh: Boolean) {
        Flowable.zip(ApiServer.getGankData(ApiServer.TYPE_FULI, MEIZI_COUNT, page), ApiServer.getGankData(ApiServer.TYPE_REST, MEIZI_COUNT, page),
                BiFunction<Ganks<MutableList<GankModel>>, Ganks<MutableList<GankModel>>, MutableList<GankModel>> { t1, t2 ->
                    t1.results?.zip(t2.results!!) { a: GankModel, b: GankModel ->
                        a.desc = b.desc
                        a.who = b.who
                        a
                    }.safe().toMutableList()
                })
                .subscribe({
                    it.let {
                        if (page == 1)
                            AppLocalData.gankDaily = myGson.toJson(it)
                        setUpMeiziList(it)
                    }
                }, {
                    isLoading.value = false
                    isError.value = it
                }).isDisposed
    }

    private val refreshListModel = RefreshListModel<GankModel>()
    val ganksListLiveData = MutableLiveData<RefreshListModel<GankModel>>()

    fun getMeiziData() {
        val tempStr = AppLocalData.gankDaily
        if (tempStr.isNotEmpty())
            setUpMeiziList(myGson.fromJson(tempStr, object : TypeToken<ArrayList<GankModel>>() {}.type))
    }

    fun setUpMeiziList(lMeiziList: MutableList<GankModel>) {
        isLoading.value = false
        if (lMeiziList.size < MEIZI_COUNT) {
            refreshListModel.isEnd = true
        }
        if (page == 1) {
            refreshListModel.setRefresh(lMeiziList)
        } else {
            refreshListModel.setUpdate(lMeiziList)
        }
        ganksListLiveData.value = refreshListModel
    }
}

