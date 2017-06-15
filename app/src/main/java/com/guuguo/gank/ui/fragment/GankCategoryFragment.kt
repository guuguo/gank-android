package com.guuguo.gank.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.guuguo.gank.R
//import com.guuguo.gank.app.fragment.SearchRevealFragment
import com.guuguo.gank.ui.adapter.MyFragmentPagerAdapter
import com.guuguo.gank.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_category_gank.*
import kotlinx.android.synthetic.main.layout_viewpager.*


class GankCategoryFragment : BaseFragment() {
    val fragments: Array<Class<*>> = arrayOf(GankCategoryContentFragment::class.java, GankCategoryContentFragment::class.java, GankCategoryContentFragment::class.java)
    val titleStrs = arrayOf("Android", "iOS", "前端")

    //    val TYPE_ANDROID = "Android"
//    val TYPE_IOS = "iOS"
//    val TYPE_REST = "休息视频"
//    val TYPE_EXPAND = "拓展资源"
//    val TYPE_FRONT = "前端"
    override fun initPresenter() {
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_category_gank;
    }

    override fun initView() {
        super.initView()
        viewpager.offscreenPageLimit = 2
        viewpager.adapter = object : MyFragmentPagerAdapter(activity, activity.supportFragmentManager, fragments, titleStrs) {
            override fun initNewFragment(position: Int, fragment: Fragment?, title: String) {
                val bundle = Bundle()
                bundle.putString(GankCategoryContentFragment.ARG_GANK_TYPE, title)
                fragment?.arguments = bundle
            }
        }
        tabLayout.setupWithViewPager(viewpager)
    }

}

