package com.guuguo.gank.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.TextView

import com.guuguo.gank.R
import com.guuguo.gank.base.BaseFragment
import com.guuguo.gank.ui.activity.SearchActivity
import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.base_toolbar_common.*
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.*

class HomeFragment : BaseFragment(), Toolbar.OnMenuItemClickListener {

    val mFragments: ArrayList<BaseFragment> = arrayListOf()
    var mFragment: BaseFragment? = null

    override fun initPresenter() {
    }

    private val STATE_FRAGMENT_SHOW = "STATE_FRAGMENT_SHOW"
    private var mTextMessage: TextView? = null
    override fun getHeaderTitle() = "gank"
    override fun getMenuResId() = R.menu.main_menu
    override fun getToolBar() = id_tool_bar
    override fun getLayoutResId() = R.layout.fragment_main

    override fun setTitle(title: String) {
        id_tool_bar.title = title
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when (item.itemId) {
            R.id.navigation_daily -> {
                ViewCompat.setElevation(appbar, 10f)
                showHideFragment(mFragments[0], mFragment)
                mFragment = mFragments[0]
                true
            }
            R.id.navigation_category -> {
                ViewCompat.setElevation(appbar, 0f)
                showHideFragment(mFragments[1], mFragment)
                mFragment = mFragments[1]
                true
            }
        }
        false
    }

    override fun initVariable(savedInstanceState: Bundle?) {
        super.initVariable(savedInstanceState)
        if (savedInstanceState == null) {
            mFragments.add(GankDailyFragment())
            mFragments.add(GankCategoryFragment())
            loadMultipleRootFragment(R.id.container_view, 0, *mFragments.toTypedArray())
            mFragment = mFragments[0]
        } else {
            mFragments[0] = findFragment(GankDailyFragment::class.java)
            mFragments[1] = findFragment(GankCategoryFragment::class.java)
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menu_check_up -> {
                Beta.checkUpgrade()
                return true
            }
            android.R.id.home -> {
                return true
            }
            R.id.menu_search -> {
                val intent = Intent(activity, SearchActivity::class.java)
                startActivity(intent);
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun initView() {
        super.initView()
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        getToolBar()?.navigationIcon = ContextCompat.getDrawable(activity, R.drawable.ic_launcher_xml)
        getToolBar()?.inflateMenu(getMenuResId())
        getToolBar()?.setOnMenuItemClickListener(this)
    }

    private var currentFragment: Fragment? = null
}
