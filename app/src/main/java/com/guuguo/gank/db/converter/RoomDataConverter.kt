package com.guuguo.gank.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.collections.ArrayList


class RoomDataConverter {
    @TypeConverter
    fun froTimestampToDate(timestamp: Long?): Date? {
        return if (timestamp == null) null else Date(timestamp)
    }

    @TypeConverter
    fun fromDateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toStringList(json: String): List<String> {
        return  Gson().fromJson(json, Array<String>::class.java).toList()
    }

    @TypeConverter
    fun toStringListJson(list: List<String>): String {
        return Gson().toJson(list)
    }


}
