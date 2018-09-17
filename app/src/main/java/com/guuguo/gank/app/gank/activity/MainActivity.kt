package com.guuguo.gank.app.gank.activity

import com.guuguo.gank.R
import com.guuguo.gank.base.BaseActivity
import com.guuguo.gank.app.gank.fragment.HomeFragment

class MainActivity : BaseActivity() {
    override fun getLayoutResId() = R.layout.activity_main
    override val backExitStyle = BACK_WAIT_TIME
}
