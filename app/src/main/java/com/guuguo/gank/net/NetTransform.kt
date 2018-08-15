//package com.guuguo.gank.net
//
//import android.accounts.NetworkErrorException
//import com.bianla.lite.R
//import com.bianla.lite.app.App
//import com.bianla.lite.app.base.net.BaseEntity
//import com.guuguo.android.lib.extension.safe
//import com.guuguo.gank.R
//import com.guuguo.gank.app.App
//import com.guuguo.gank.model.Ganks
//import io.reactivex.Observable
//import io.reactivex.ObservableTransformer
//import retrofit2.HttpException
//import java.io.IOException
//import java.net.ConnectException
//import java.net.SocketTimeoutException
//import java.net.UnknownHostException
//
//class NetTransform<T> : ObservableTransformer<Ganks<T>, Ganks<T>> {
//
//    override fun apply(upstream: Observable<Ganks<T>>): Observable<Ganks<T>> {
//        return upstream.flatMap { data ->
//            return@flatMap Observable.create<Ganks<T>> { e ->
//                val str = checkError(data)
//                if (str != null) {
//                    try {
//                        e.onError(IOException(str))
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                    }
//                } else {
//                    e.onNext(data)
//                    e.onComplete()
//                }
//            }
//        }
//    }
//
//    private fun checkError(t: Ganks<T>): String? {
//        return if (t.code != 1) {
//            t.alertMsg
//        } else {
//            null
//        }
//    }
//}
//
//fun Throwable.netError(onApiLoadError: (msg: String, isNetError: Boolean) -> Unit = { _, _ -> }): String {
//    var message = ""
//    val errorMessage: (msg: String, isNetError: Boolean) -> Unit = { msg, b ->
//        message = msg
//        onApiLoadError(msg, b)
//    }
//
//    when (this) {
//        is SocketTimeoutException -> errorMessage(App.get().getString(R.string.state_network_timeout), true)
//        is NetworkErrorException -> errorMessage(App.get().getString(R.string.state_network_error), true)
//        is UnknownHostException -> errorMessage(App.get().getString(R.string.state_network_unknown_host), true)
//        is ConnectException -> errorMessage(App.get().getString(R.string.state_network_unknown_host), true)
//        is HttpException -> {
//            errorMessage(when (this.code()) {
//                400 -> "Bad Request 请求出现语法错误"
//                401 -> "Unauthorized 访问被拒绝"
//                403 -> "Forbidden 资源不可用"
//                404 -> "Not Found 无法找到指定位置的资源"
//                405 -> "Method Not Allowed 请求方法（GET、POST、HEAD、Delete、PUT、TRACE等）对指定的资源不适用"
//                in 406..499 -> "客户端错误"
//                500 -> "Internal Server Error 服务器遇到了意料不到的情况，不能完成客户的请求"
//                502 -> "Bad Gateway .Web 服务器用作网关或代理服务器时收到了无效响应"
//                503 -> "Service Unavailable 服务不可用,服务器由于维护或者负载过重未能应答"
//                504 -> "Gateway Timeout 网关超时"
//                505 -> "HTTP Version Not Supported 服务器不支持请求中所指明的HTTP版本"
//                in 506..599 -> "服务端错误"
//                else -> this.message().safe()
//            }, true)
//        }
//        is IOException -> {
////            val res=ResourceUtils.getResId(this.message.safe(),String::class.java)
////           val msg= if(res !=-1) App.getInstance().getString(res) else this.message.safe()
//            val msg = this.message.safe()
//            errorMessage(msg, false)
//        }
//        else -> errorMessage(this.message.safe(), false)
//    }
//    return message
//}