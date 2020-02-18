package com.guuguo.gank.ui

import android.os.Build.VERSION
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.guuguo.android.lib.extension.getColorCompat
import com.guuguo.android.lib.systembar.SystemBarHelper
import com.guuguo.baselib.mvvm.MBaseActivity
import com.guuguo.gank.R
import com.guuguo.gank.databinding.ActivityMainBinding


class MainActivity : MBaseActivity<ActivityMainBinding>() {

    private var currentNavController: LiveData<NavController>? = null
    override fun getLayoutResId() = R.layout.activity_main
    override fun initStatusBar() {
        val decorView = window.decorView as ViewGroup
        var translucentView = decorView.findViewById<View>(R.id.systembar_foreground_view)
        if (translucentView == null) {
            translucentView = View(decorView.context)
            translucentView.id = R.id.systembar_foreground_view
            val lp = ViewGroup.LayoutParams(
                -1,
                SystemBarHelper.getStatusBarHeight(decorView.context)
            )
            decorView.addView(translucentView, lp)
        }

        translucentView.setBackgroundColor(getColorCompat(R.color.colorPrimary))
        SystemBarHelper.tintStatusBar(activity, getColorCompat(R.color.colorPrimary), 0.2f)
    }
    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {

        val navGraphIds = listOf(R.navigation.fuliba, R.navigation.gank)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = binding.bottomNavigation.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, Observer { navController ->
            setupActionBarWithNavController(navController)
        })
        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }
    override fun initView() {
        super.initView()
        setupBottomNavigationBar()
    }

}
