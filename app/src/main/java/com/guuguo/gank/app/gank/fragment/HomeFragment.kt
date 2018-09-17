package com.guuguo.gank.app.gank.fragment

import android.support.v7.widget.Toolbar
import android.view.MenuItem
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.guuguo.gank.R
import com.guuguo.gank.app.gank.activity.AboutActivity
import com.guuguo.gank.base.BaseFragment
import com.guuguo.gank.databinding.FragmentHomeBinding
import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.base_toolbar_common.*

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
        id_tool_bar.title = title
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

        NavigationUI.setupWithNavController(binding.navigation, mNavHostFragment.navController)

        getToolBar()?.inflateMenu(getMenuResId())
        getToolBar()?.setOnMenuItemClickListener(this)
    }

}
