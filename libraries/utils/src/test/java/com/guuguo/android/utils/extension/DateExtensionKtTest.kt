package com.guuguo.android.lib.extension

import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat

/**
 * Created by mimi on 2017/9/12.
 */
class DateExtensionKtTest {
    val format = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
    val compareDate =format.parse("2017/9/12 12:25:12").time

    @Test
    fun getTimeSpan() {
        Assert.assertEquals(format.parse("2017/9/12 12:25:5").getTimeSpan(compareDate),"7秒前")
        Assert.assertEquals(format.parse("2017/9/12 12:22:5").getTimeSpan(compareDate),"3分钟前")
        Assert.assertEquals(format.parse("2017/9/12 11:22:5").getTimeSpan(compareDate),"1小时前")
        Assert.assertEquals(format.parse("2017/9/11 11:22:5").getTimeSpan(compareDate),"1天前")
        Assert.assertEquals(format.parse("2017/7/11 11:22:5").getTimeSpan(compareDate),"2月前")
        Assert.assertEquals(format.parse("2014/7/11 11:22:5").getTimeSpan(compareDate),"3年前")
    }

}