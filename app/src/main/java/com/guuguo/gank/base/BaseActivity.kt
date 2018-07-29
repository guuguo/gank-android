package com.guuguo.gank.base

import com.guuguo.android.lib.app.LBaseActivitySupport
import com.guuguo.android.lib.app.LNBaseActivity
import com.guuguo.gank.app.App

/**
 * Created by guodeqing on 7/23/16.
 */

abstract class BaseActivity : LBaseActivitySupport() {
    protected var myApplication = App.instance

    open fun initPresenter() {}
    override fun initView() {
        initPresenter()
        super.initView()
    }
}
