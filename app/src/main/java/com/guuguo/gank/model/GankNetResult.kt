package com.guuguo.gank.model

import com.guuguo.gank.model.entity.GankModel

/**
 * Created by 大哥哥 on 2016-11-19.
 */

class GankNetResult {

    /**
     * count : 10
     * error : false
     * results : [{"desc":"下拉刷新上拉加载更多 支持listview recyclerview gridview","ganhuo_id":"56cc6d29421aa95caa708172","publishedAt":"2016-01-15T04:30:14.281000","readability":"","type":"Android","url":"https://github.com/Chanven/CommonPullToRefresh","who":"Jason"}]
     */

    var count: Int = 0
    var error: Boolean = false
    var results: List<GankModel>? = null

}
