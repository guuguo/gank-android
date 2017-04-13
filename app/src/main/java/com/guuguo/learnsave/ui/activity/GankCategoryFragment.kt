package com.guuguo.learnsave.ui.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
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
    val fragments:Array<Class<*>> = arrayOf(GankDailyFragment::class.java, GankDailyFragment::class.java, GankDailyFragment::class.java, GankDailyFragment::class.java, GankDailyFragment::class.java, GankDailyFragment::class.java)
    override fun initPresenter() {
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_category_gank;
    }

    override fun initView() {
        super.initView()
        viewpager.adapter = MyFragmentPagerAdapter(activity, activity.supportFragmentManager, fragments)
        tabLayout.setupWithViewPager(viewpager)
    }

}

