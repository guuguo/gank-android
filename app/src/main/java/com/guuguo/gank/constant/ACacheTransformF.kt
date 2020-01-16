package com.guuguo.gank.constant

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.guuguo.android.lib.utils.Utils
import com.guuguo.android.lib.utils.cache.ACache
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import java.io.Serializable

class ACacheTransformF<T : Serializable>(var key: String, var typeToken: TypeToken<T>? = null) : FlowableTransformer<T, Pair<T, Boolean>> {
    val FROM_NET = 0
    val FROM_CACHE = 1
    val FROM_CACHE_AND_NET = 2
    val NO_CACHE = 3
    val FROM_CACHE_IF_VALIDE = 4
    var fromType = FROM_CACHE_AND_NET

    fun fromCache() = this.also { fromType = FROM_CACHE }
    fun noCache() = this.also { fromType = NO_CACHE }
    fun fromNet() = this.also { fromType = FROM_NET }
    fun fromCacheAndNet() = this.also { fromType = FROM_CACHE_AND_NET }
    fun fromCacheIfValide() = this.also { fromType = FROM_CACHE_IF_VALIDE }

    val aCache = ACache.get(Utils.getContext())
    override fun apply(upstream: Flowable<T>): Flowable<Pair<T, Boolean>> {

        var canGetFromCache = false
        val getFromCacheObservable = try {
            Flowable.just(getACache() as T to true).also {
                canGetFromCache = true
            }
        } catch (e: Exception) {
            Flowable.never<Pair<T, Boolean>>()
        }

        return when (fromType) {
            FROM_NET -> upstream.map { putACache(it);it to false }
            FROM_CACHE -> {
                getFromCacheObservable
            }
            FROM_CACHE_IF_VALIDE -> {
                return if (canGetFromCache)
                    getFromCacheObservable
                else upstream.map { putACache(it); it to false }
            }
            FROM_CACHE_AND_NET -> {
                Flowable.mergeDelayError(upstream.map { putACache(it);it to false }, getFromCacheObservable)
            }
            NO_CACHE -> {
                upstream.map { it to false }
            }
            else -> upstream.map { it to false }
        }
    }

    private fun getACache() = typeToken?.let { Gson().fromJson<T>(aCache.getAsString(key), it.type) }
            ?: aCache.getAsObject(key) as T

    private fun putACache(bean: T) {
        typeToken?.let { aCache.put(key, Gson().toJson(bean)) } ?: aCache.put(key, bean)
    }
}