package com.guuguo.fuliba.ui.fuliba.repository

import androidx.lifecycle.MutableLiveData
import com.guuguo.fuliba.model.FulibaItemBean
import com.guuguo.fuliba.repository.net.FulibaService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

object FulibaRepository {
   suspend fun getHomeList(): MutableList<FulibaItemBean>? {
//        val livedata = MutableLiveData<MutableList<FulibaItemBean>>()

//           GlobalScope.launch {
        return kotlin.runCatching {
            val html = FulibaService.server().getFulibaList().string()
            val doc: Document = Jsoup.parse(html)
            val articals = doc.select("article")
            articals.map {
                val bean = FulibaItemBean()
                bean.img = it.select("img").attr("data-src")
                bean.title = it.select("header > h2 > a").text()
                bean.content = it.select("p.note").text()
                bean.tag = it.select("header > a").text()
                bean.url = it.select("> a").attr("href")
                bean
            }.toMutableList()
        }.getOrNull()
//            livedata.postValue(res)
//        }
//        return livedata
    }
}