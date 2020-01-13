package com.guuguo.gank.ui.fuliba.repository

import com.guuguo.gank.net.Service

object FulibaRepository {
   suspend fun getHomeList(){
       val str= Service.server().getFulibaList()
//       val doc: Document = Jsoup.connect("http://en.wikipedia.org/").get()
    }
}