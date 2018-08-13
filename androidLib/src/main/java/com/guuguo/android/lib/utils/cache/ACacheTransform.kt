package com.guuguo.android.lib.utils.cache

import com.guuguo.android.lib.utils.Utils
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableTransformer
import java.io.Serializable

/** 转成带缓存的网络请求,Pair<T,Boolean> Boolean代表是否是从缓存读取的的结果*/
class ACacheTransform<T : Serializable>(var key: String) : ObservableTransformer<T, Pair<T, Boolean>> {
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
        val getFromCache = { e: ObservableEmitter<Pair<T, Boolean>> ->
            val res = try {
                aCache.getAsObject(key) as T
            } catch (e: Exception) {
                null
            }
            if (res != null)
                e.onNext(res to true)
            res
        }
        return when (fromType) {
            FROM_NET -> upstream.flatMap {
                Observable.create<Pair<T, Boolean>> { e ->
                    aCache.put(key, it)
                    e.onNext(it to false)
                    e.onComplete()
                }
            }
            FROM_CACHE -> {
                Observable.create<Pair<T, Boolean>> { getFromCache(it);it.onComplete() }
            }
            FROM_CACHE_IF_VALIDE -> {
                val res = try {
                    aCache.getAsObject(key) as T
                } catch (e: Exception) {
                    null
                }
                if (res != null)
                    Observable.create<Pair<T, Boolean>> { it.onNext(res to true);it.onComplete() }
                else
                    upstream.flatMap {
                        Observable.create<Pair<T, Boolean>> { e ->
                            e.onNext(it to false)
                            e.onComplete()
                        }
                    }
            }
            FROM_CACHE_AND_NET -> {
                Observable.merge(upstream.flatMap {
                    Observable.create<Pair<T, Boolean>> { e ->
                        aCache.put(key, it)
                        e.onNext(it to false)
                        e.onComplete()
                    }
                }, Observable.create {
                    getFromCache(it); it.onComplete()
                })
            }
            NO_CACHE -> {
                upstream.flatMap {
                    Observable.create<Pair<T, Boolean>> { e ->
                        e.onNext(it to false)
                        e.onComplete()
                    }
                }
            }
            else -> upstream.flatMap {
                Observable.create<Pair<T, Boolean>> { e ->
                    e.onNext(it to false)
                    e.onComplete()
                }
            }
        }
    }
}
