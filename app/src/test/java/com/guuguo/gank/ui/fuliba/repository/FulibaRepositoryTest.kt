package com.guuguo.gank.ui.fuliba.repository

import com.google.gson.GsonBuilder
import org.junit.Assert
import org.junit.Test

data class TestGson(val name: String, val sex: Boolean, val age: Int) {
    override fun toString(): String {
        return "TestGson(name='$name', sex=$sex, age=$age)"
    }

    override fun equals(other: Any?): Boolean {
        return toString() == other?.toString()
    }
}

class FulibaRepositoryTest {
    @Test
    fun gsonLenientTest() {
        val json = "{name:\"guuguo\",sex:true,age:\"0\"}"
        val gson = GsonBuilder().setLenient().create()
        Assert.assertTrue(TestGson("guuguo", true, 0) == gson.fromJson(json, TestGson::class.java))
        Assert.assertFalse(TestGson("guuguo", true, 1) == gson.fromJson(json, TestGson::class.java))
    }
}