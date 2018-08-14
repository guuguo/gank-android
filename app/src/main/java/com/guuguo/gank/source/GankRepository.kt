package com.guuguo.gank.source

import com.guuguo.gank.app.App
import com.guuguo.gank.db.GankAppDatabase
import com.guuguo.gank.model.entity.GankModel
import io.reactivex.Completable

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Copyright ©2017 by ruzhan
 */

object GankRepository {
    fun <T> Flowable<T>.api(): Flowable<T> {
        return this.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun Completable.api(): Completable {
        return this.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private val gankAppDatabase: GankAppDatabase = GankAppDatabase.get(App.get())

    fun getGankById(id: String): Flowable<GankModel> {
        return gankAppDatabase.gankDao().getGankById(id)
    }

    fun getStarGanks(): Flowable<List<GankModel>> = gankAppDatabase.gankDao().getGanks().api()
    fun insertGank(bean: GankModel) = Completable.create { gankAppDatabase.gankDao().insertGank(bean) }.api()

}
