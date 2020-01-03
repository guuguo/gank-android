package com.guuguo.android.lib.extension

import com.guuguo.android.lib.ktx.isPhone
import org.junit.Assert
import org.junit.Test

/**
 * @Author mimi
 */
class StringExtensionKtTest {
    @Test
    fun isEmail() {
        Assert.assertTrue(!"17006694256".isEmail())
        Assert.assertTrue("1152168009@qq.com".isEmail())
        Assert.assertTrue("guodeqing@qq.com".isEmail())
        Assert.assertTrue("guodeqing@qq.cm".isEmail())
        Assert.assertTrue(!"guodeqing@qq".isEmail())
    }

    @Test
    fun isUrl() {
        Assert.assertTrue("http://192.168.1.1:100".isUrl())
        Assert.assertTrue("http://192.168.1.1".isUrl())
        Assert.assertTrue("http://baidu.com".isUrl())
        Assert.assertTrue("http://www.baidu.com".isUrl())
        Assert.assertTrue(!"http:www.baidu.com".isUrl())
        Assert.assertTrue(!"http:/www.baidu.com".isUrl())
        Assert.assertTrue(!"htp:/www.baidu.com".isUrl())
        Assert.assertTrue(!"www.baidu.com".isUrl())
        Assert.assertTrue(!"192.168.1.1:100".isUrl())
    }

    @Test
    fun isPhone() {
        Assert.assertTrue("17006694256".isPhone())
        Assert.assertTrue(!"1700669425".isPhone())
        Assert.assertTrue(!"25215215121".isPhone())
        Assert.assertTrue(!"64680118".isPhone())
    }

    @Test
    fun isNumeric() {

    }

}