package com.guuguo.baselib.extension

import android.graphics.Typeface
import android.net.Uri
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.annotation.ColorInt
import com.google.gson.Gson
import com.guuguo.android.lib.extension.fromJson
import com.guuguo.android.lib.extension.log
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.regex.Pattern

/**
 * Created by mimi on 2016-11-17.
 */

fun String?.safe(default: String = ""): String {
    return if (this.isNullOrEmpty())
        default
    else this!!
}

fun Int?.safe(default: Int = 0) = this ?: default
fun Long?.safe(default: Long = 0) = this ?: default
fun Boolean?.safe(default: Boolean = false) = this ?: default
fun Float?.safe(default: Float = 0f) = this ?: default


fun <T> List<T>?.safe(): List<T> {
    return this ?: listOf<T>()
}

fun <T> List<T>.toArrayList(): ArrayList<T> {
    return ArrayList(this)
}

inline fun <reified T : Any> String?.fromJson(): T? {
    return this.runCatching {
        Gson().fromJson<T>(this!!)
    }.getOrNull()
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

fun String.isPhone(): Boolean {
    val p = "^1([345789])\\d{9}\$".toRegex()
    return matches(p)
}

fun String.isNumeric(): Boolean {
    val p = "^[0-9]+$".toRegex()
    return matches(p)
}

fun String.addUrlParams(vararg pairs: Pair<String, Any>): String {
    return addUrlParams(mapOf(*pairs))
}

/** 网络地址后添加参数 可防止地址后面已经拼接参数地址拼接问题 [map] Map<String, String> 参数集合  */
fun String.addUrlParams(map: Map<String, Any>?): String {
    map ?: return this
    val uri = Uri.parse(this)
    val url = StringBuffer(this)
    if (!url.contains('?')) //判断之前有无参数
        url.append("?")
    else if (url.last() != '?' && url.last() != '&') {
        url.append("&")
    }
    map.forEach { (k, v) ->
        if (uri.getQueryParameter(k) == null)
            url.append("$k=$v&")
    }
    return url.trimEnd { it == '&' }.toString()
}

///** 网络地址后添加参数 可防止地址后面已经拼接参数地址拼接问题 [map] Map<String, String> 参数集合   */
//fun String.addUrlParams(map: Map<String, Any>?): String {
//    map ?: return this
//    val oldUri = this.toUri()
//    val uriBuilder = oldUri.buildUpon()
//
//    map.forEach { (k, v) ->
//        if (oldUri.getQueryParameter(k) == null)
//            uriBuilder.appendQueryParameter(k, v.toString())
//    }
//    val uri = uriBuilder.build()
//    return uri.scheme.safe() + "://" + uri.authority + uri.path + "#" + uri.fragment + "?" + uri.query
//}

/**文字颜色设置 [tints] 文字和颜色对 first 变色文字，second 颜色*/
fun String.tintText(isBold: Boolean = false, vararg tints: Pair<String, Int>): SpannableString {
    val spannableString = SpannableString(this)
    tints.forEach {
        if (it.first.isEmpty())
            return spannableString
        var i = 0
        val colorFSpan = ForegroundColorSpan(it.second)
        val styleSpan = StyleSpan(Typeface.BOLD)
        while (i <= length - it.first.length) {
            val resIndex = indexOf(it.first, i)
            if (resIndex == -1)
                break
            else {
                spannableString.setSpan(colorFSpan, resIndex, resIndex + it.first.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                if (isBold) {
                    spannableString.setSpan(styleSpan, resIndex, resIndex + it.first.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
                i = resIndex + it.first.length
            }
        }
    }
    return spannableString
}

fun String.tintNUmberText(color: Int, isBold: Boolean = false): SpannableString {
    val p = "\\d+".toRegex()
    return tintText(isBold, *(p.findAll(this).map { it.value to color }.toList().toTypedArray()))

}

/**文字颜色设置 [tints] 文字和颜色对 first 变色文字，second 颜色*/
fun String.tintTextWithBold(vararg tints: Pair<String, Int>): SpannableString {
    val spannableString = SpannableString(this)
    tints.forEach {
        if (it.first.isEmpty())
            return spannableString
        var i = 0
        val colorFSpan = ForegroundColorSpan(it.second)
        while (i < length - it.first.length) {
            val resIndex = indexOf(it.first, i)
            if (resIndex == -1)
                break
            else {
                spannableString.setSpan(colorFSpan, resIndex, resIndex + it.first.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                i = resIndex + it.first.length
            }
        }
    }
    return spannableString
}

/**文字着色*/
fun String.tintAllText(@ColorInt color: Int): SpannableString {
    val spannableString = SpannableString(this)
    val colorFSpan = ForegroundColorSpan(color)
    spannableString.setSpan(colorFSpan, 0, this.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    return spannableString
}

/**文字加粗*/
fun String.boldAllText(): SpannableString=boldText(0,this.length)
/**文字加粗*/
fun String.boldText(start: Int, end: Int): SpannableString {
    val spannableString = SpannableString(this)
    val styleSpan = StyleSpan(Typeface.BOLD)
    spannableString.setSpan(styleSpan, start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    return spannableString
}

/**文字变细*/
fun String.slimAllText(): SpannableString {
    return slimText(0, this.length)
}

/**文字变细*/
fun String.slimText(start: Int, end: Int): SpannableString {
    val spannableString = SpannableString(this)
    val styleSpan = StyleSpan(Typeface.NORMAL)
    spannableString.setSpan(styleSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    return spannableString
}

/**文字颜色设置 [tints] 文字和颜色对 first 位置Pair Start to End，second 颜色*/
fun String.tintTextByPosition(vararg tints: Pair<Pair<Int, Int>, Int>): SpannableString {
    val spannableString = SpannableString(this)
    tints.forEach {
        if (it.first.first > it.first.second)
            return spannableString
        val colorFSpan = ForegroundColorSpan(it.second)
        spannableString.setSpan(colorFSpan, it.first.first, it.first.second, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    return spannableString
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
        val bytes = md5.digest(string!!.toByteArray())
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


