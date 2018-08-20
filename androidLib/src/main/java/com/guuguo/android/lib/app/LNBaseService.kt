package com.guuguo.android.lib.app

import android.app.Service
import com.guuguo.android.lib.BaseApplication
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 * Created by guodeqing on 16/5/31.
 */
abstract class LNBaseService : Service() {

    protected val TAG = this.javaClass.simpleName
    protected lateinit var myApplication: BaseApplication
    private val mApiCalls = CompositeDisposable()

    protected fun addDispose(call: Disposable?) {
        if (call != null)
            mApiCalls.add(call)
    }

    override fun onCreate() {
        myApplication = BaseApplication.get()
        super.onCreate()
    }

    override fun onDestroy() {
        mApiCalls.clear()
        super.onDestroy()
    }
}
