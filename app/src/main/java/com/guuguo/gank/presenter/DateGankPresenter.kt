package com.guuguo.gank.presenter

import android.content.Context
import com.guuguo.gank.model.GankDays
import com.guuguo.gank.model.entity.GankModel
import com.guuguo.gank.net.ApiServer
import com.guuguo.gank.view.IDateGankView
import io.reactivex.functions.Consumer
import java.util.*

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
class DateGankPresenter(context: Context, iView: IDateGankView) : BasePresenter<IDateGankView>(context, iView) {

    fun fetchDate(date: Date) {
        iView.showProgress()
        subscription = ApiServer.getGankOneDayData(date)
                .subscribe(Consumer {
                    gankDays ->
                    iView.showDate(getMergeAllGanks(gankDays))
                    iView.hideProgress()
                }, Consumer<kotlin.Throwable> { error ->
                    iView.showErrorView(error)
                    iView.hideProgress()
                })
    }

    fun getMergeAllGanks(gankDays: GankDays): ArrayList<GankModel> {
        var list = ArrayList<GankModel>()
        list.addAllSafe(gankDays.results!!.rest)
        list.addAllSafe(gankDays.results!!.Android)
        list.addAllSafe(gankDays.results!!.iOS)
        list.addAllSafe(gankDays.results!!.recommend)
        list.addAllSafe(gankDays.results!!.extend)
        list.addAllSafe(gankDays.results!!.APP)
        list.addAllSafe(gankDays.results!!.web)
        return list
    }

    fun <T> ArrayList<T>.addAllSafe(list: List<T>?) {
        if (list != null)
            addAll(list!!)
    }
}
