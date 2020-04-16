package com.guuguo.gank.ui.main

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.guuguo.android.lib.extension.getColorCompat
import com.guuguo.android.lib.systembar.SystemBarHelper
import com.guuguo.baselib.mvvm.MBaseActivity
import com.guuguo.gank.R
import com.guuguo.gank.databinding.ActivityMainBinding
import com.guuguo.gank.ui.setupWithNavController
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : MBaseActivity<ActivityMainBinding>() {

    private var currentNavController: LiveData<NavController>? = null
    override fun getLayoutResId() = R.layout.activity_main
    override fun isNavigationBack(): Boolean = false
    val vm by viewModels<MainVM> ()
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("BOTTOM_ID", binding.bottomNavigation.selectedItemId)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.getInt("BOTTOM_ID")?.runCatching {
            vm.selectedId.value = this
        }
    }

    override fun initViewModelCallBack() {
        super.initViewModelCallBack()
            vm.selectedId.observe(this, Observer {
                binding.bottomNavigation.selectedItemId=it
            })
    }
    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {

        val navGraphIds = listOf(R.navigation.fuliba, R.navigation.gank)

        val controller = binding.bottomNavigation.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )
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
