package com.guuguo.baselib.mvvm

import android.os.Bundle
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

    override fun initVariable(savedInstanceState: Bundle?) {
        super.initVariable(savedInstanceState)
    }
}
