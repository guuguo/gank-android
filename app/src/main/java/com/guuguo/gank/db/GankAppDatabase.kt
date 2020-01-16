package com.guuguo.gank.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import androidx.annotation.VisibleForTesting
import com.guuguo.gank.db.converter.RoomDataConverter
import com.guuguo.gank.db.dao.GankDao
import com.guuguo.gank.model.entity.GankModel
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.migration.Migration


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

        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {

            }
        }

        operator fun get(context: Context): GankAppDatabase {
            if (INSTANCE == null) {
                synchronized(GankAppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context, GankAppDatabase::class.java, DATABASE_NAME)
                                .addMigrations(MIGRATION_1_2)
                                .build()
                        INSTANCE!!.updateDatabaseCreated(context)
                    }
                }
            }
            return INSTANCE!!
        }


    }
}
