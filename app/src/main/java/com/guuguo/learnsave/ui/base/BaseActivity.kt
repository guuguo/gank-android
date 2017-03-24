package com.guuguo.learnsave.ui.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import com.guuguo.android.lib.app.LBaseActivity
import com.guuguo.learnsave.app.MyApplication

/**
 * Created by guodeqing on 7/23/16.
 */

abstract class BaseActivity : LBaseActivity() {
    protected var myApplication = MyApplication.instance

    open fun initPresenter() {}
    override fun init() {
        initPresenter()
        super.init()
    }
}
