package com.guuguo.learnsave.model.entity

import java.io.Serializable
import java.util.*

/**
 * Created by guodeqing on 7/24/16.
 */
class GankModel : Serializable {
    var _id: String = ""
    var createdAt: Date? = null
    var desc: String=""
    var publishedAt: Date? = null
    var source: String? = null
    var type: String = ""
    var url: String = ""
    var used: String? = null
    var who: String? = null

    var width: Int = 0
    var height: Int = 0
    override fun equals(other: Any?): Boolean {
        if (other is GankModel)
            if (_id == other._id)
                return true
        return false
    }

    override fun toString(): String {
        return "GankModel(_id=$_id, createdAt=$createdAt, desc=$desc, publishedAt=$publishedAt, source=$source, type=$type, url=$url, used=$used, who=$who, width=$width, height=$height)"
    }

}
