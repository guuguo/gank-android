package com.guuguo.gank.app.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.guuguo.gank.R
//import com.guuguo.gank.app.fragment.SearchRevealFragment
import com.guuguo.gank.app.adapter.MyFragmentPagerAdapter
import com.guuguo.gank.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_category_gank.*
import kotlinx.android.synthetic.main.layout_viewpager.*
import java.util.*


class GankCategoryFragment : BaseFragment() {
    var currentFragment: BaseFragment? = null
    val titleStrs = arrayOf("Android", "iOS", "前端")

    override fun getLayoutResId(): Int {
        return R.layout.fragment_category_gank;
    }

    override fun initView() {
        super.initView()
        viewpager.offscreenPageLimit = 3
        viewpager.adapter = object : MyFragmentPagerAdapter(activity, childFragmentManager, titleStrs) {
            override fun initNewFragment(position: Int, title: String): Fragment {
                return GankCategoryContentFragment.newInstance(title)
            }
        }
        tabLayout.setupWithViewPager(viewpager)
    }

}

