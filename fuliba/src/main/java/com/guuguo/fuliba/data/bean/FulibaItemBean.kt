package com.guuguo.fuliba.data.bean

class FulibaItemBean {
    var img: String = ""
    var title: String = ""
    var date: String = ""
    var content: String = ""
    var tag: String = ""
    var url: String = ""
    override fun hashCode(): Int {
        return url.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return this.hashCode() == other?.hashCode()
    }
}