package com.guuguo.gank.db.converter;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Comment;

import java.util.Date;
import java.util.List;


public class RoomDataConverter {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static List<String> toStringList(String json) {
        return new Gson().fromJson(json, new TypeToken<List<String>>() {
        }.getType());
    }

    @TypeConverter
    public static String toStringListJson(List<String> list) {
        return new Gson().toJson(list);
    }


}
