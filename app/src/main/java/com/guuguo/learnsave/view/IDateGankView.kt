package com.guuguo.learnsave.view

import com.guuguo.learnsave.model.GankDays
import com.guuguo.learnsave.model.entity.GankModel

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
interface IDateGankView : IBaseView {
    fun showProgress()
    fun hideProgress()
    fun showErrorView(e: Throwable)
    fun showDate(date:GankDays)
}
