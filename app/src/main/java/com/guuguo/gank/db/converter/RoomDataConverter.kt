package com.guuguo.gank.db.converter

import androidx.room.TypeConverter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import org.w3c.dom.Comment

import java.lang.reflect.Array
import java.util.ArrayList
import java.util.Date


class RoomDataConverter {
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return if (timestamp == null) null else Date(timestamp)
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toStringList(json: String): List<String> {
        return Gson().fromJson(json, object : TypeToken<List<String>>() {

        }.type)
    }
//
//    @TypeConverter
//    fun toStringArrayList(json: String): ArrayList<String> {
//        return Gson().fromJson(json, object : TypeToken<ArrayList<String>>() {
//
//        }.type)
//    }
//
//    @TypeConverter
//    fun toStringArrayListJson(list: ArrayList<String>): String {
//        return Gson().toJson(list)
//    }

    @TypeConverter
    fun toStringListJson(list: List<String>): String {
        return Gson().toJson(list)
    }


}
