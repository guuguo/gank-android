package com.guuguo.learnsave.model.entity

import java.io.Serializable
import java.util.*

/**
 * Created by guodeqing on 7/24/16.
 */
class SearchResultModel : Serializable {
    /**
     * desc : 下拉刷新上拉加载更多 支持listview recyclerview gridview
     * ganhuo_id : 56cc6d29421aa95caa708172
     * publishedAt : 2016-01-15T04:30:14.281000
     * readability :
     * type : Android
     * url : https://github.com/Chanven/CommonPullToRefresh
     * who : Jason
     */

    var desc: String? = null
    var ganhuo_id: String? = null
    var publishedAt: String? = null
    var readability: String? = null
    var type: String? = null
    var url: String? = null
    var who: String? = null

}
