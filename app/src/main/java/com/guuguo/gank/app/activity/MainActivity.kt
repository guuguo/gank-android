package com.guuguo.gank.app.activity

import com.guuguo.gank.R
import com.guuguo.gank.base.BaseActivity
import com.guuguo.gank.app.fragment.HomeFragment

class MainActivity : BaseActivity() {
    override fun getLayoutResId() = R.layout.activity_main
    override val backExitStyle = BACK_WAIT_TIME
    override fun initView() {
        super.initView()
        loadRootFragment(R.id.container_view, HomeFragment.getInstance())
    }
}
