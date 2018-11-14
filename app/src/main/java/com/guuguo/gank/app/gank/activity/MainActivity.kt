package com.guuguo.gank.app.gank.activity

import android.graphics.Color
import com.guuguo.android.lib.extension.getColorCompat
import com.guuguo.android.lib.systembar.SystemBarHelper
import com.guuguo.gank.R
import com.guuguo.gank.base.BaseActivity
import android.view.ViewGroup
import android.os.Build.VERSION
import android.view.View
import kotlinx.android.synthetic.main.fragment_home.*


class MainActivity : BaseActivity() {
    override fun getLayoutResId() = R.layout.activity_main
    override fun initStatusBar() {
        val decorView = window.decorView as ViewGroup
        if (VERSION.SDK_INT >= 19) {
            var translucentView = decorView.findViewById<View>(R.id.systembar_foreground_view)
            if (translucentView == null) {
                translucentView = View(decorView.context)
                translucentView.id = R.id.systembar_foreground_view
                val lp = android.view.ViewGroup.LayoutParams(-1, SystemBarHelper.getStatusBarHeight(decorView.context))
                decorView.addView(translucentView, lp)
            }

            translucentView.setBackgroundColor(getColorCompat(R.color.colorPrimary))
        }
        SystemBarHelper.tintStatusBar(activity, getColorCompat(R.color.colorPrimary), 0.2f)
    }
}
