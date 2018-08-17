package com.guuguo.gank.presenter

import android.content.Context
import com.guuguo.android.lib.extension.safe
import com.guuguo.gank.model.GankDays
import com.guuguo.gank.model.GankSection
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
                .subscribe({
                    gankDays ->
                    iView.showDate(getMergeAllGanks(gankDays))
                    iView.hideProgress()
                }, { error ->
                    iView.showErrorView(error)
                    iView.hideProgress()
                })
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
