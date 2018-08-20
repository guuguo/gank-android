package com.guuguo.android.lib.extension

import com.google.gson.Gson
import org.junit.Assert
import org.junit.Test

/**
 * mimi 创造于 2017/10/16.
 * 项目 zueetTP
 */
class GsonExtensionKtTest {
    class User {
        var name = "";
        var age = 0
    }

    val myJson = "{\"name\":\"大哥哥\",\"age\":12}"

    var user = User().apply { name = "大哥哥";age = 12 }
    @Test
    fun testGsonFromJson() {
        val tempUser: User = Gson().fromJson(myJson)
        Assert.assertEquals(user.age, tempUser.age)
        Assert.assertEquals(user.name, tempUser.name)
    }

    @Test
    fun testGsonToJson() {
        val json = Gson().toJson(user)
        Assert.assertEquals(json, myJson)
    }
}