package com.guuguo.gank.app.gank.fragment

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import com.google.android.material.tabs.TabLayout
import com.guuguo.android.lib.extension.dpToPx
import com.guuguo.gank.R
import com.guuguo.gank.app.gank.activity.AboutActivity
import com.guuguo.gank.app.gank.activity.GankActivity
import com.guuguo.gank.base.BaseFragment
import com.guuguo.gank.databinding.FragmentHomeBinding
import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.base_toolbar_common.*
import kotlinx.android.synthetic.main.base_toolbar_common.view.*

class HomeFragment : BaseFragment<FragmentHomeBinding>(), Toolbar.OnMenuItemClickListener {
    override fun isNavigationBack() = false
    companion object {
        fun getInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun getHeaderTitle() = "gank"
    override fun getMenuResId() = R.menu.main_menu
    override fun getToolBar() = contentView?.findViewById<Toolbar>(R.id.id_tool_bar)
    override fun getLayoutResId() = R.layout.fragment_home

    override fun setTitle(title: String) {
        tv_title.text=title
    }


    lateinit var mNavHostFragment: NavHostFragment


    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menu_check_up -> Beta.checkUpgrade()
            R.id.menu_search -> {
                findNavController().navigate(R.id.action_to_search)
            }
            R.id.menu_about -> AboutActivity.intentTo(activity)
            else -> return false
        }
        return true
    }

    override fun initView() {
        super.initView()
        mNavHostFragment = childFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
//        NavigationUI.setupWithNavController(binding.navigation, mNavHostFragment.navController)
        ViewCompat.setElevation(binding.toolbar,8.dpToPx().toFloat())
        binding.toolbar.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(t: TabLayout.Tab?) {
                val id = when (t?.position) {
                    0 -> R.id.dailyFragment
                    1 -> R.id.gankCategoryFragment
                    2 -> R.id.gankCategoryContentFragment
                    else -> R.id.dailyFragment
                }
                when (t?.position) {
                    1 -> ViewCompat.setElevation(binding.toolbar,0f)
                    else -> ViewCompat.setElevation(binding.toolbar,8.dpToPx().toFloat())
                }
                onNavDestinationSelected(id, mNavHostFragment.navController, false)
            }
        })
        getToolBar()?.inflateMenu(getMenuResId())
        getToolBar()?.setOnMenuItemClickListener(this)
        binding.toolbar.search_card.setOnClickListener {
            val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, binding.toolbar.search_card,"share_search")
            findNavController().navigate(R.id.action_to_search)
//            FragmentNavigator(activity,childFragmentManager,)
        }
    }

    internal fun onNavDestinationSelected(id: Int, navController: NavController, popUp: Boolean): Boolean {
        val builder = NavOptions.Builder().setLaunchSingleTop(true).setEnterAnim(R.anim.nav_default_enter_anim).setExitAnim(R.anim.nav_default_exit_anim).setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim).setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)
        if (popUp) {
            builder.setPopUpTo(R.id.nav_host, false)
        }

        val options = builder.build()

        try {
            navController.navigate(id, null as Bundle?, options)
            return true
        } catch (var6: IllegalArgumentException) {
            return false
        }

    }
}
