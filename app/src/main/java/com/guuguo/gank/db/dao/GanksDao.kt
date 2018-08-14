package com.guuguo.gank.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import com.guuguo.gank.model.entity.GankModel

import io.reactivex.Flowable


@Dao
interface GankDao {

    @Query("SELECT * FROM gank ORDER BY _id DESC")
    fun loadGanks(): Flowable<List<GankModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGanks(newsList: List<GankModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGank(gank: GankModel)

    @Query("SELECT * FROM gank  WHERE _id = :id")
    fun getGankById( id:String):Flowable<GankModel>
}
