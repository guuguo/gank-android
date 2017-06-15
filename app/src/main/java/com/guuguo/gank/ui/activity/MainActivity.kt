package com.guuguo.gank.ui.activity

import com.guuguo.gank.R
import com.guuguo.gank.base.BaseActivity
import com.guuguo.gank.ui.fragment.HomeFragment

class MainActivity : BaseActivity() {
    override fun getLayoutResId()=R.layout.activity_main
    override fun initView() {
        super.initView()
        loadRootFragment(R.id.container_view, HomeFragment())
    }
}
