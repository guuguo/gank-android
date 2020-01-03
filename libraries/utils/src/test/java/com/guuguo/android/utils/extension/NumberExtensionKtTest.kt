package com.guuguo.android.lib.extension

import org.junit.Assert
import org.junit.Test

class NumberExtensionKtTest {

    @Test
    fun getFitSize() {
//        Assert.assertEquals(, )
    }

    @Test
    fun formatDecimal() {
        Assert.assertEquals(2.formatDecimal(2),"2.00" )
        Assert.assertEquals(2.1232.formatDecimal(3),"2.123" )
    }
}