package com.guuguo.gank.db

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import android.support.annotation.VisibleForTesting
import com.guuguo.gank.db.converter.RoomDataConverter
import com.guuguo.gank.db.dao.GankDao
import com.guuguo.gank.model.entity.GankModel

@Database(entities = [(GankModel::class)], version = 1, exportSchema = false)
@TypeConverters(RoomDataConverter::class)
abstract class GankAppDatabase : RoomDatabase() {

    private val isDatabaseCreated = MutableLiveData<Boolean>()

    val databaseCreated: LiveData<Boolean>
        get() = isDatabaseCreated

    abstract fun gankDao(): GankDao

    private fun updateDatabaseCreated(context: Context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            isDatabaseCreated.postValue(true)
        }
    }

    companion object {

        @VisibleForTesting
        val DATABASE_NAME = "gank-db"

        private var INSTANCE: GankAppDatabase? = null


        operator fun get(context: Context): GankAppDatabase {
            if (INSTANCE == null) {
                synchronized(GankAppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context, GankAppDatabase::class.java, DATABASE_NAME)
                                .build()
                        INSTANCE!!.updateDatabaseCreated(context)
                    }
                }
            }
            return INSTANCE!!
        }
    }
}
