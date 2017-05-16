package com.guuguo.gank.presenter

import android.content.Context
import com.guuguo.gank.model.Ganks
import com.guuguo.gank.net.ApiServer
import com.guuguo.gank.app.MEIZI_COUNT
import com.guuguo.gank.model.entity.GankModel
import com.guuguo.gank.view.IMainView
import io.reactivex.functions.Consumer
import java.util.*

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
class MainPresenter(context: Context, iView: IMainView) : BasePresenter<IMainView>(context, iView) {

    fun fetchMeiziData(page: Int) {
        iView.showProgress()
        subscription = ApiServer.getGankData(ApiServer.TYPE_FULI, MEIZI_COUNT, page)
                .subscribe(object : Consumer<Ganks<ArrayList<GankModel>>> {
                    override fun accept(meiziData: Ganks<ArrayList<GankModel>>?) {
                        meiziData?.let {
                            iView.showMeiziList(meiziData.results!!)
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
