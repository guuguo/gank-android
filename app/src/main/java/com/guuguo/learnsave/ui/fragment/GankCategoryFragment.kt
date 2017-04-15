package com.guuguo.learnsave.ui.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.guuguo.learnsave.R
import com.guuguo.learnsave.R.id.*
import com.guuguo.learnsave.ui.adapter.MeiziAdapter
import com.guuguo.learnsave.ui.base.BaseActivity
//import com.guuguo.learnsave.app.fragment.SearchRevealFragment
import com.guuguo.learnsave.extension.safe
import com.guuguo.learnsave.extension.showSnackTip
import com.guuguo.learnsave.extension.updateData
import com.guuguo.learnsave.model.entity.GankModel
import com.guuguo.learnsave.presenter.MainPresenter
import com.guuguo.learnsave.util.DisplayUtil
import com.guuguo.learnsave.app.MEIZI
import com.guuguo.learnsave.app.OmeiziDrawable
import com.guuguo.learnsave.app.TRANSLATE_GIRL_VIEW
import com.guuguo.learnsave.ui.adapter.MyFragmentPagerAdapter
import com.guuguo.learnsave.ui.base.BaseFragment
import com.guuguo.learnsave.view.IMainView
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.proguard.ac
import kotlinx.android.synthetic.main.fragment_category_gank.*
import kotlinx.android.synthetic.main.layout_viewpager.*
import kotterknife.bindView
import java.io.Serializable
import java.util.*


class GankCategoryFragment : BaseFragment() {
    val fragments: Array<Class<*>> = arrayOf(GankCategoryContentFragment::class.java, GankCategoryContentFragment::class.java, GankCategoryContentFragment::class.java)
    val titleStrs = arrayOf("Android", "IOS", "前端")

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
