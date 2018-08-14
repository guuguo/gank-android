package com.guuguo.gank.source

import com.guuguo.gank.app.App
import com.guuguo.gank.db.GankAppDatabase
import com.guuguo.gank.model.entity.GankModel

import io.reactivex.Flowable
import org.w3c.dom.Comment

/**
 * Copyright ©2017 by ruzhan
 */

object GankRepository {

    private val gankAppDatabase: GankAppDatabase

    init {
        gankAppDatabase = GankAppDatabase.get(App.get())
    }

    fun getGankById(id: String): Flowable<GankModel> {
        return gankAppDatabase.gankDao().getGankById(id)
    }

    fun insertGank(bean: GankModel) {
        gankAppDatabase.gankDao().insertGank(bean)
    }

}
