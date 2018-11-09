package com.guuguo.gank.app.gank.viewmodel

import androidx.lifecycle.MutableLiveData
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.lib.utils.cache.ACacheTransform
import com.guuguo.gank.base.BaseViewModel
import com.guuguo.gank.constant.ACacheTransformF
import com.guuguo.gank.model.GankDays
import com.guuguo.gank.model.GankSection
import com.guuguo.gank.model.entity.GankModel
import com.guuguo.gank.net.ApiServer
import com.guuguo.gank.net.EmptyConsumer
import com.guuguo.gank.net.ErrorConsumer
import io.reactivex.disposables.Disposable
import java.util.*

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
class DateGankViewModel : BaseViewModel() {

    val gankDayLiveData = MutableLiveData<ArrayList<GankSection>>()

    var disposeble: Disposable? = null
    fun fetchDate(date: Date) {
        if (disposeble?.isDisposed == false)
            disposeble?.dispose()
        disposeble = ApiServer.getGankOneDayData(date)
                .compose(ACacheTransformF<GankDays>("getGankOneDayData${date.time}").fromCacheIfValide())
                .doOnSubscribe { isLoading.value = true }
                .doOnTerminate { isLoading.value = false }
                .doOnError { isError.value = it }
                .doOnNext { gankDayLiveData.value = getMergeAllGanks(it.first) }
                .subscribe(EmptyConsumer(), ErrorConsumer())
    }

    fun getMergeAllGanks(gankDays: GankDays): ArrayList<GankSection> {
        var list = ArrayList<GankSection>()
        gankDays.results!!.rest.addSection(list)
        gankDays.results!!.Android.addSection(list)
        gankDays.results!!.iOS.addSection(list)
        gankDays.results!!.recommend.addSection(list)
        gankDays.results!!.extend.addSection(list)
        gankDays.results!!.APP.addSection(list)
        gankDays.results!!.web.addSection(list)
        return list
    }

    fun ArrayList<GankModel>?.addSection(list: ArrayList<GankSection>) {
        this?.let {
            if (it.isNotEmpty()) {
                list.add(GankSection(it[0].type))
                list.addAll(it.map { GankSection(it) }.safe())
            }
        }
    }
}
