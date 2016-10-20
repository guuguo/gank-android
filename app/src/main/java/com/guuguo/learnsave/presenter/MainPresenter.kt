package com.guuguo.learnsave.presenter

import android.content.Context
import com.guuguo.learnsave.bean.Ganks
import com.guuguo.learnsave.net.ApiServer
import com.guuguo.learnsave.view.IMainView
import rx.functions.Action1

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
class MainPresenter(context: Context, iView: IMainView) : BasePresenter<IMainView>(context, iView) {

    override fun release() {
        subscription.unsubscribe()
    }

    fun fetchMeiziData(page: Int) {
        iView.showProgress()
        subscription = ApiServer.getGankData(ApiServer.TYPE_FULI, 12, page)
                .subscribe(object : Action1<Ganks> {
                    override fun call(meiziData: Ganks) {
                        if (meiziData.results.size === 0) {
                            iView.showNoMoreData()
                        } else {
                            iView.showMeiziList(meiziData.results)
                        }
                        iView.hideProgress()
                    }
                }, Action1<kotlin.Throwable> {error->
                    iView.showErrorView(error)
                    iView.hideProgress()
                })

    }

    private fun createMeiziDataWithRestDesc(meiziData: Ganks, data: Ganks): Ganks {
        val size = Math.min(meiziData.results.size, data.results.size)
        for (i in 0..size - 1) {
            meiziData.results[i].desc = meiziData.results[i].desc + "，" + data.results[i].desc
            meiziData.results[i].who = data.results[i].who
        }
        return meiziData
    }


}
