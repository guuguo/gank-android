package com.guuguo.gank.presenter

import android.content.Context
import com.guuguo.gank.model.GankDays
import com.guuguo.gank.model.Ganks
import com.guuguo.gank.model.entity.GankModel
import com.guuguo.gank.model.retrofit.ApiServer
import com.guuguo.gank.view.IBaseView
import com.guuguo.gank.view.IDateGankView
import com.guuguo.gank.view.IMainView
import java.util.*

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
class SearchRevealPresenter(context: Context, iView: IBaseView) : BasePresenter<IBaseView>(context, iView)
