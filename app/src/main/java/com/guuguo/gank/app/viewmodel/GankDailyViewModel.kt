package com.guuguo.gank.app.viewmodel

import android.databinding.BaseObservable
import com.guuguo.gank.constant.MEIZI_COUNT
import com.guuguo.gank.model.Ganks
import com.guuguo.gank.model.entity.GankModel
import com.guuguo.gank.net.ApiServer
import com.guuguo.gank.net.http.BaseCallback
import com.guuguo.gank.app.fragment.GankDailyFragment
import io.reactivex.disposables.Disposable
import java.util.*


/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class GankDailyViewModel(val fragment: GankDailyFragment) : BaseObservable() {
    val activity = fragment.activity

    fun fetchMeiziData(page: Int) {
        ApiServer.getGankData(ApiServer.TYPE_FULI, MEIZI_COUNT, page)
                .subscribe(object : BaseCallback<Ganks<ArrayList<GankModel>>>() {
                    override fun onSubscribe(d: Disposable?) {
                        fragment.addApiCall(d)
                    }

                    override fun onApiLoadError(msg: String) {
                        activity.dialogErrorShow(msg)
                    }

                    override fun onSuccess(t: Ganks<ArrayList<GankModel>>) {
                        super.onSuccess(t)
                        t.results?.let {
                            fragment.setUpMeiziList(it)
                        }
                    }
                })

    }
}

