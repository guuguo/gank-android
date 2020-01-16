package com.guuguo.gank.ui

import android.os.Build.VERSION
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.guuguo.android.lib.extension.getColorCompat
import com.guuguo.android.lib.systembar.SystemBarHelper
import com.guuguo.baselib.mvvm.MBaseActivity
import com.guuguo.gank.R
import com.guuguo.gank.databinding.ActivityMainBinding


class MainActivity : MBaseActivity<ActivityMainBinding>() {
    private lateinit var mNavHostFragment: NavHostFragment

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

    override fun initView() {
        super.initView()
        mNavHostFragment =
            supportFragmentManager.findFragmentById(R.id.container_nav) as NavHostFragment
        NavigationUI.setupWithNavController(binding.navigation, mNavHostFragment.navController)
//        binding.navigation.setOnClickListener {
//            when (it.id) {
//                R.id.menu_fuliba -> {
//                }
//                R.id.menu_gank -> {
//
//                }
//            }
//        }

    }

}
