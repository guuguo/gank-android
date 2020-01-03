package com.guuguo.android.lib.ktx


/**
 * Description: 字符串处理相关
 * Create by lxj, at 2018/12/7
 */

/**
 * 是否是手机号严格到第二位
 */
fun String?.isPhoneStrict() = if (this == null) false else "(\\+\\d+)?1[345789]\\d{9}$".toRegex().matches(this)
/*超过设定数量变为...*/
fun String.limitSizeWithEllips(limit: Int) = if (this.length > limit) take(limit) + "..." else this
/**
 * 是否是手机号 1开头的十一位数字
 */
fun String?.isPhone() = if (this == null) false else "(\\+\\d+)?1\\d{10}$".toRegex().matches(this)

/**
 * 是否是邮箱地址
 */
fun String?.isEmail() = if (this == null) false else "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?".toRegex().matches(this)

/**
 * 是否是身份证号码
 */
fun String?.isIDCard() = if (this == null) false else "[1-9]\\d{16}[a-zA-Z0-9]".toRegex().matches(this)

/**
 * 是否是中文字符
 */
fun String?.isChinese() = if (this == null) false else "^[\u4E00-\u9FA5]+$".toRegex().matches(this)
