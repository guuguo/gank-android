package com.guuguo.gank

import com.guuguo.android.lib.extension.date
import com.guuguo.gank.constant.myGsonSearch
import com.guuguo.gank.model.entity.GankModel
import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).

 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    @Test
    @Throws(Exception::class)
    fun gson() {
     assertEquals("2002/01/02",myGsonSearch.fromJson("{'createdAt':'2002-01-02T05:03:12'}", GankModel::class.java).createdAt?.date())   
    }

    companion object {

        @JvmStatic fun main(s: Array<String>) {

        }
    }
}