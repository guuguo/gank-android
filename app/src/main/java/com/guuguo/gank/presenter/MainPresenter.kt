package com.guuguo.gank.presenter

import android.content.Context
import com.guuguo.gank.constant.MEIZI_COUNT
import com.guuguo.gank.net.ApiServer
import com.guuguo.gank.view.IMainView

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
class MainPresenter(context: Context, iView: IMainView) : BasePresenter<IMainView>(context, iView) {

    fun fetchMeiziData(page: Int) {
        iView.showProgress()
        subscription = ApiServer.getGankData(ApiServer.TYPE_FULI, MEIZI_COUNT, page)
                .subscribe({ meiziData ->
                    meiziData.let {
                        iView.showMeiziList(meiziData.results!!)
                        iView.hideProgress()
                    }
                }, { error ->
                    iView.showErrorView(error)
                    iView.hideProgress()
                })

    }

}
