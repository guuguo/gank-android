package com.guuguo.gank.base

import com.guuguo.android.lib.app.LNBaseActivity
import com.guuguo.gank.app.MyApplication

/**
 * Created by guodeqing on 7/23/16.
 */

abstract class BaseActivity : LNBaseActivity() {
    protected var myApplication = MyApplication.instance

    open fun initPresenter() {}
    override fun initView() {
        initPresenter()
        super.initView()
    }
}
