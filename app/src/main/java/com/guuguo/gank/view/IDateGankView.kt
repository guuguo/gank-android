package com.guuguo.gank.view

import com.guuguo.gank.model.GankDays
import com.guuguo.gank.model.GankSection
import com.guuguo.gank.model.entity.GankModel
import java.util.*

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
interface IDateGankView : IBaseView {

    fun showDate(date:ArrayList<GankSection>)
}
