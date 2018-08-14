package com.guuguo.gank.model.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import android.support.annotation.NonNull
import com.guuguo.gank.db.converter.RoomDataConverter
import java.io.Serializable
import java.util.*

/**
 * Created by guodeqing on 7/24/16.
 */
@Entity(tableName = "gank")
class GankModel : Serializable {
    @NonNull
    @PrimaryKey
    var _id: String = ""
    var createdAt: Date? = null
    var desc: String = ""
    var publishedAt: Date? = null
    var source: String? = null
    var type: String = ""
    var url: String = ""
    var used: String? = null
    var who: String? = null
    @TypeConverters(RoomDataConverter::class)
    var images: List<String> = listOf()
    var width: Int = 0
    var height: Int = 0

    var ganhuo_id = ""
    var readability = ""

    constructor( _id: String, createdAt: Date?, desc: String, publishedAt: Date?, source: String?, type: String, url: String, used: String?, who: String?, images: ArrayList<String>, width: Int, height: Int, ganhuo_id: String, readability: String) {
        this._id = _id
        this.createdAt = createdAt
        this.desc = desc
        this.publishedAt = publishedAt
        this.source = source
        this.type = type
        this.url = url
        this.used = used
        this.who = who
        this.images = images
        this.width = width
        this.height = height
        this.ganhuo_id = ganhuo_id
        this.readability = readability
    }

    constructor()

    override fun equals(other: Any?): Boolean {
        if (other is GankModel)
            if (_id == other._id)
                return true
        return false
    }

    fun getWidthUrl(width: Int): String {
        return url + "?imageView2/0/w/" + width.toString()
    }

    override fun toString(): String {
        return "GankModel(_id=$_id, createdAt=$createdAt, desc=$desc, publishedAt=$publishedAt, source=$source, type=$type, mUrl=$url, used=$used, who=$who, width=$width, height=$height)"
    }

}
