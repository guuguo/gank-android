package com.guuguo.fuliba.data.source

import com.guuguo.fuliba.data.bean.FulibaItemBean
import androidx.lifecycle.liveData
import com.guuguo.fuliba.data.source.remote.FulibaService
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

object FulibaRepository : FulibaDataSource {
    override suspend fun getFuliItemList(page: Int): MutableList<FulibaItemBean>? {
        return kotlin.runCatching {
            val html = FulibaService.server().getFulibaList(page).string()
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
    }
}