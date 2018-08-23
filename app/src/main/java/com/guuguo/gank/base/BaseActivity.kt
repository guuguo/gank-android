package com.guuguo.gank.base

import com.guuguo.android.lib.app.LBaseActivity

/**
 * Created by guodeqing on 7/23/16.
 */

abstract class BaseActivity : LBaseActivity() {

    open fun initPresenter() {}
    override fun initView() {
        initPresenter()
        super.initView()
    }
}
