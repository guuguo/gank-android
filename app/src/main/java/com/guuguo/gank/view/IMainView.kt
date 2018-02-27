package com.guuguo.gank.view

import com.guuguo.gank.model.entity.GankModel

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
interface IMainView : IBaseView {

    fun showMeiziList(lMeiziList: List<GankModel>)
}
