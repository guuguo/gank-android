package com.guuguo.gank.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import com.guuguo.android.lib.extension.formatTime
import com.guuguo.android.lib.extension.getTimeSpanUntilDay
import com.guuguo.android.lib.extension.safe
import com.guuguo.gank.constant.datePattern
import com.guuguo.gank.db.converter.RoomDataConverter
import java.io.Serializable
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by guodeqing on 7/24/16.
 */
@Entity(tableName = "gank")
class GankModel() : Parcelable, Serializable {
    @NonNull
    @PrimaryKey
    var _id: String? = ""
    var createdAt: Date? = null
    var desc: String? = ""
    var publishedAt: Date? = null
    var source: String? = null
    var type: String? = ""
    var url: String? = ""
    var used: String? = null
    var who: String? = null
//    @TypeConverters(RoomDataConverter::class)
    var images: List<String>? = listOf()
    var width: Int = 0
    var height: Int = 0

    var ganhuo_id: String? = ""
    var readability: String? = ""

    constructor(parcel: Parcel) : this() {
        _id = parcel.readString()
        desc = parcel.readString()
        source = parcel.readString()
        type = parcel.readString()
        url = parcel.readString()
        used = parcel.readString()
        who = parcel.readString()
        images = parcel.createStringArrayList()
        width = parcel.readInt()
        height = parcel.readInt()
        ganhuo_id = parcel.readString()
        readability = parcel.readString()
        createdAt = try {
            SimpleDateFormat(datePattern).parse(parcel.readString())
        } catch (e: Exception) {
            Date()
        }
        publishedAt = try {
            SimpleDateFormat(datePattern).parse(parcel.readString())
        } catch (e: Exception) {
            Date()
        }
    }

    fun getPublishTimeStr() = publishedAt?.getTimeSpanUntilDay()

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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(desc)
        parcel.writeString(source)
        parcel.writeString(type)
        parcel.writeString(url)
        parcel.writeString(used)
        parcel.writeString(who)
        parcel.writeStringList(images)
        parcel.writeInt(width)
        parcel.writeInt(height)
        parcel.writeString(ganhuo_id)
        parcel.writeString(readability)
        parcel.writeString(createdAt?.let { SimpleDateFormat(datePattern).format(it) }.safe())
        parcel.writeString(publishedAt?.let {
            SimpleDateFormat(datePattern).format(publishedAt).safe()
        })
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GankModel> {
        override fun createFromParcel(parcel: Parcel): GankModel {
            return GankModel(parcel)
        }

        override fun newArray(size: Int): Array<GankModel?> {
            return arrayOfNulls(size)
        }
    }

}
