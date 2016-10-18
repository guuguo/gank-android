package com.guuguo.learnsave.bean

import com.google.gson.annotations.SerializedName
import com.guuguo.learnsave.bean.entity.GankBean
import java.util.*

/**
 * Created by guodeqing on 7/24/16.
 */
class GankDayResult {
    var date: Long = 0

    var android: ArrayList<GankBean>? = null

    var ios: ArrayList<GankBean>? = null

    @SerializedName("休息视频")
    var rest: ArrayList<GankBean>? = null

    @SerializedName("拓展资源")
    var extend: ArrayList<GankBean>? = null

    @SerializedName("瞎推荐")
    var recommend: ArrayList<GankBean>? = null

    @SerializedName("福利")
    var welfare: ArrayList<GankBean>? = null

    override fun toString(): String {
        return "ResultsBean{" +
                "date=" + date +
                ", Android=" + android +
                ", iOS=" + ios +
                ", rest=" + rest +
                ", extend=" + extend +
                ", recommend=" + recommend +
                ", welfare=" + welfare +
                '}'
    }
}
