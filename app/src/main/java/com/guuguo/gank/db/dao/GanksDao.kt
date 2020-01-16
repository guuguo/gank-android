package com.guuguo.gank.db.dao

import androidx.room.*
import com.guuguo.gank.model.entity.GankModel
import io.reactivex.Maybe


@Dao
interface GankDao {

    @Query("SELECT * FROM gank ORDER BY _id DESC")
    fun getGanks(): Maybe<List<GankModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGanks(newsList: List<GankModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGank(gank: GankModel)

    @Delete()
    fun deleteGank(gank: GankModel)

    @Query("SELECT * FROM gank  WHERE _id = :id")
    fun getGankById(id: String): Maybe<GankModel>
}
