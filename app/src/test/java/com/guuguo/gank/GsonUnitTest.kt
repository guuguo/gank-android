package com.guuguo.gank

import com.guuguo.android.lib.extension.date
import com.guuguo.gank.constant.myGson
import com.guuguo.gank.model.entity.GankModel
import org.junit.Test

import org.junit.Assert.assertEquals

/**
 * Example local unit test, which will execute on the development machine (host).

 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class GsonUnitTest {
    @Test
    @Throws(Exception::class)
    fun gson() {
        assertEquals("2017/06/13", myGson.fromJson("{'createdAt':'2017-06-13T07:15:29.423Z'}", GankModel::class.java).createdAt?.date())
    }

    fun transport(weight: Int, lenght: Int) {}

    companion object {

        @JvmStatic fun main(s: Array<String>) {

        }
    }
}