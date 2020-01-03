//通过@file: JvmName("ClickUtils")注解，将生成的类名修改为ClickUtils，并且调用的时候直接调用ClickUtils.即可
//放在文件顶部，在package声明的前面
@file:JvmName("ClickUtils")

package com.guuguo.android.lib.extension

import android.view.View

/**
 * Created by xwh on 2018/5/17.
 * view点击事件的扩展函数
 */

/**
 * 延迟时间
 */
private var <T : View> T.triggerDelay: Long
    get() = if (getTag(1123461123) != null) getTag(1123461123) as Long else -1
    set(value) {
        setTag(1123461123, value)
    }

/**
 * 上一次点击时间
 */
private var <T : View> T.triggerLastTime: Long
    get() = if (getTag(1123460103) != null) getTag(1123460103) as Long else 0
    set(value) {
        setTag(1123460103, value)
    }


/**
 * 设置延迟时间的view扩展
 * @param delay 延迟时间 默认600毫秒
 * @return T
 */
fun <T : View> T.withTrigger(delay: Long = 600): T {
    triggerDelay = delay
    return this
}

/**
 * 点击事件的view扩展
 * @param block unit函数
 */
@Suppress("UNCHECKED_CAST")
fun <T : View> T.click(block: (T) -> Unit) = setOnClickListener {
    if (clickEnable()) {
        block(it as T)
    }
}

/**
 * 带延迟过滤点击事件的view扩展
 * @param delay 延迟时间 默认600毫秒
 * @param block  unit 函数
 */
@Suppress("UNCHECKED_CAST")
fun <T : View> T.clickAvoidDouble(delay: Long = 600, block: (T) -> Unit) = setOnClickListener {
    triggerDelay = delay
    if (clickEnable()) {
        block(it as T)
    }
}

fun <T : View> T.doAvoidDouble(delay: Long = 600, doIt: (T) -> Unit){
    triggerDelay = delay
    if (clickEnable()) {
        doIt(this)
    } else {
        "点的太快了".log(this.toString())/*.toast()*/
    }
}

private fun <T : View> T.clickEnable(): Boolean {
    var flag = false
    val currentClickTime = System.currentTimeMillis()
    if (currentClickTime - triggerLastTime >= triggerDelay) {
        flag = true
    }
    triggerLastTime = currentClickTime
    return flag
}





