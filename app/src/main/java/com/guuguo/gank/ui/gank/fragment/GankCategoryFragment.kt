package com.guuguo.gank.ui.gank.fragment

import androidx.fragment.app.Fragment
import com.guuguo.android.lib.app.LBaseFragment
import com.guuguo.gank.R
import com.guuguo.gank.ui.gank.adapter.MyFragmentPagerAdapter
import com.guuguo.gank.base.BaseFragment
import com.guuguo.gank.databinding.FragmentCategoryGankBinding
import kotlinx.android.synthetic.main.fragment_category_gank.*


class GankCategoryFragment : BaseFragment<FragmentCategoryGankBinding>() {
    var currentFragment: LBaseFragment? = null
    val titleStrs = arrayOf("Android", "iOS", "前端","休息视频","拓展资源")

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

