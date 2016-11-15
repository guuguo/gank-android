package com.guuguo.learnsave.presenter

import android.content.Context
import com.guuguo.learnsave.model.GankDays
import com.guuguo.learnsave.model.Ganks
import com.guuguo.learnsave.model.entity.GankModel
import com.guuguo.learnsave.model.retrofit.ApiServer
import com.guuguo.learnsave.view.IDateGankView
import com.guuguo.learnsave.view.IMainView
import rx.functions.Action1
import java.util.*

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
class DateGankPresenter(context: Context, iView: IDateGankView) : BasePresenter<IDateGankView>(context, iView) {

    fun fetchDate(date: Date) {
        iView.showProgress()
        subscription = ApiServer.getGankOneDayData(date)
                .subscribe(object : Action1<GankDays> {
                    override fun call(gankDays: GankDays) {
                        iView.showDate(getMergeAllGanks(gankDays))
                        iView.hideProgress()
                    }
                }, Action1<kotlin.Throwable> { error ->
                    iView.showErrorView(error)
                    iView.hideProgress()
                })
    }

    fun getMergeAllGanks(gankDays: GankDays): ArrayList<GankModel> {
        var list = ArrayList<GankModel>()
        list.addAllSafe(gankDays.results!!.Android)
        list.addAllSafe(gankDays.results!!.iOS)
        list.addAllSafe(gankDays.results!!.recommend)
        list.addAllSafe(gankDays.results!!.extend)
        list.addAllSafe(gankDays.results!!.APP)
        list.addAllSafe(gankDays.results!!.web)
        list.addAllSafe(gankDays.results!!.rest)
        return list
    }

    fun <T> ArrayList<T>.addAllSafe(list: List<T>?) {
        if (list != null)
            addAll(list!!)
    }
}
