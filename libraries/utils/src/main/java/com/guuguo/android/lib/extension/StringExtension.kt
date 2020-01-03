package com.guuguo.android.lib.extension

import com.guuguo.android.lib.utils.ToastUtil
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.regex.Pattern

/**
 * Created by mimi on 2016-11-17.
 */

fun String?.safe(default: String = ""): String {
    return if (this.isNullOrEmpty())
        default
    else this
}

fun Double?.safe(default: Double = 0.0): Double {
    return if (this == null || this == 0.0)
        default
    else this
}

fun String?.toast(isShortToast: Boolean = true): String? {
    if (isShortToast)
        ToastUtil.showSingletonToast(this.safe("(空字符串)"))
    else
        ToastUtil.showSingleLongToast(this.safe("(空字符串)"))
    return this
}

infix fun String.toast(isShortToast: Boolean) {
    if (isShortToast)
        ToastUtil.showSingletonToast(this.safe("(空字符串)"))
    else
        ToastUtil.showSingleLongToast(this.safe("(空字符串)"))
    return
}

fun String.sha1() = encrypt(this, "SHA-1")
fun String.md5() = encrypt(this, "MD5")

fun String?.isEmail(): Boolean {
    val str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"
    val p = Pattern.compile(str)
    val m = p.matcher(this.safe())
    return m.matches()
}

fun String?.isUrl(): Boolean {
    val str = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]"
    val p = Pattern.compile(str)
    val m = p.matcher(this.safe())
    return m.matches()
}

fun String.isNumeric(): Boolean {
    val p = "^[0-9]+$".toRegex()
    return matches(p)
}

/**
 * Extension method to get encrypted string.
 */
private fun encrypt(string: String?, type: String): String {
    if (string.isNullOrEmpty()) {
        return ""
    }
    val md5: MessageDigest
    return try {
        md5 = MessageDigest.getInstance(type)
        val bytes = md5.digest(string.toByteArray())
        bytes2Hex(bytes)
    } catch (e: NoSuchAlgorithmException) {
        ""
    }
}

private fun bytes2Hex(bts: ByteArray): String {
    var des = ""
    var tmp: String
    for (i in bts.indices) {
        tmp = Integer.toHexString(bts[i].toInt() and 0xFF)
        if (tmp.length == 1) {
            des += "0"
        }
        des += tmp
    }
    return des
}
