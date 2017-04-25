package com.guuguo.gank.model

import com.google.gson.annotations.SerializedName
import com.guuguo.gank.model.entity.GankModel
import java.util.*

/**
 * Created by guodeqing on 7/24/16.
 */
class GankDayResult {
    var date: Long = 0

    var Android: ArrayList<GankModel>? = null
   
    var APP: ArrayList<GankModel>? = null

    var iOS: ArrayList<GankModel>? = null
    
    @SerializedName("前端")
    var web: ArrayList<GankModel>? = null

    @SerializedName("休息视频")
    var rest: ArrayList<GankModel>? = null

    @SerializedName("拓展资源")
    var extend: ArrayList<GankModel>? = null

    @SerializedName("瞎推荐")
    var recommend: ArrayList<GankModel>? = null

    @SerializedName("福利")
    var welfare: ArrayList<GankModel>? = null
    
    override fun toString(): String {
        return "ResultsBean{" +
                "date=" + date +
                ", Android=" + Android +
                ", iOS=" + iOS +
                ", rest=" + rest +
                ", extend=" + extend +
                ", recommend=" + recommend +
                ", welfare=" + welfare +
                '}'
    }
}
