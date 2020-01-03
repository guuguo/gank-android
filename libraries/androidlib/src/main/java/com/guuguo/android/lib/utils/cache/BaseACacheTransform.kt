package com.guuguo.android.lib.utils.cache

import com.guuguo.android.lib.utils.Utils
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableTransformer
import io.reactivex.internal.util.BackpressureHelper.add
import java.io.Serializable

/** 转成带缓存的网络请求,Pair<T,Boolean> Boolean代表是否是从缓存读取的的结果*/
class BaseACacheTransform<T : Serializable>(var key: String) : ObservableTransformer<T, Pair<T, Boolean>> {
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
    override fun apply(upstream: Observable<T>): Observable<Pair<T, Boolean>> {
        val getCacheRes: () -> T? = {
            try {
                aCache.getAsObject(key) as T
            } catch (e: Exception) {
                null
            }
        }
        return when (fromType) {
            ///从网络获取，并缓存
            FROM_NET -> upstream.map { aCache.put(key, it);it to false }
            ///只从缓存获取
            FROM_CACHE -> {
                getCacheRes()?.let { Observable.just(it to true) }
                        ?: Observable.empty()
            }
            ///如果缓存有数据，则从缓存获取，没有数据则从网络获取,通常是为了节省流量，不主动刷新就不获取网络数据
            FROM_CACHE_IF_VALIDE ->
                getCacheRes().let {
                    if (it != null) Observable.just(it to true)
                    else upstream.map { aCache.put(key, it);it to false }
                }
            ///先从缓存取数据，然后再去网络取数据
            FROM_CACHE_AND_NET -> {
                Observable.concatEager(arrayListOf<Observable<Pair<T, Boolean>>>().apply {
                    val res = getCacheRes()
                    if (res != null && checkResult(res)) add(Observable.just(res to true))
                    add(upstream.map { aCache.put(key, it);it to false })
                })
            }
            ///不参与缓存
            NO_CACHE -> upstream.map { it to false }
            else -> upstream.map { it to false }
        }
    }

    open fun checkResult(bean: T) = true
}
