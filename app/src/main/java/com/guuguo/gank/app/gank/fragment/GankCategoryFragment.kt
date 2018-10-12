package com.guuguo.gank.app.gank.fragment

//import com.guuguo.gank.app.fragment.SearchRevealFragment
import android.support.v4.app.Fragment
import com.guuguo.android.lib.app.LBaseFragment
import com.guuguo.gank.R
import com.guuguo.gank.app.gank.adapter.MyFragmentPagerAdapter
import kotlinx.android.synthetic.main.fragment_category_gank.*
import kotlinx.android.synthetic.main.layout_viewpager.*


class GankCategoryFragment : LBaseFragment() {
    var currentFragment: LBaseFragment? = null
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

