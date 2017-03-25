package com.guuguo.learnsave.presenter

import android.content.Context
import com.guuguo.learnsave.model.Ganks
import com.guuguo.learnsave.model.retrofit.ApiServer
import com.guuguo.learnsave.app.MEIZI_COUNT
import com.guuguo.learnsave.view.IMainView
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
class MainPresenter(context: Context, iView: IMainView) : BasePresenter<IMainView>(context, iView) {

    fun fetchMeiziData(page: Int) {
        iView.showProgress()
        subscription = ApiServer.getGankData(ApiServer.TYPE_FULI, MEIZI_COUNT, page)
                .subscribe(object : Consumer<Ganks> {
                    override fun accept(meiziData: Ganks?) {
                        meiziData?.let {

                            iView.showMeiziList(meiziData.results)
                            iView.hideProgress()
                        }
                    }
                }, object : Consumer<kotlin.Throwable> {
                    override fun accept(error: Throwable) {
                        iView.showErrorView(error)
                        iView.hideProgress()
                    }
                })

    }

}
