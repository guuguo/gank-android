package com.guuguo.android.lib.extension

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.roundToInt

fun JSONObject.getJSONObjectSafe(str: String) = try {
    getJSONObject(str)
} catch (e: Exception) {
    null
}

fun JSONObject.getStringSafe(str: String) = try {
    getString(str)
} catch (e: Exception) {
    ""
}

fun JSONObject.getIntSafe(str: String) = try {
    getInt(str)
} catch (e: Exception) {
    null
}

public inline fun JSONArrayOf(vararg elements: JSONObject): JSONArray {
    val jsonArray = JSONArray()
    jsonArray.put(elements)
    return jsonArray;
}

public inline fun Map<String, Any>.toJSONObject() :JSONObject{
    val obj = JSONObject()
    this.forEach { (k, v) ->
        obj.put(k, v)
    }
    return obj
}