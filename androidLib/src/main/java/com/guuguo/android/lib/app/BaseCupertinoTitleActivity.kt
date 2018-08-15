package com.guuguo.android.lib.app

import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.Toolbar
import android.view.ViewGroup
import android.widget.TextView
import com.guuguo.android.R
import com.guuguo.android.lib.utils.systembar.SystemBarHelper
import com.guuguo.android.lib.widget.FunctionTextView

open class BaseCupertinoTitleActivity : LBaseActivitySupport() {
    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }
    override fun getLayoutResId() = R.layout.base_activity_cupertino_simple_back
    override fun getToolBar(): Toolbar = findViewById(R.id.id_tool_bar)
    override fun getAppBar(): ViewGroup? = findViewById(R.id.appbar)
    override fun initToolBar() {
        super.initToolBar()
        supportActionBar?.title=""
    }
    override fun initStatusBar() {
        SystemBarHelper.setHeightAndPadding(activity, getToolBar())
        SystemBarHelper.immersiveStatusBar(activity, 0f)
        SystemBarHelper.setStatusBarDarkMode(activity)
    }
    override fun getHeaderTitle()=null
    override fun setTitle(title: CharSequence?) {
        findViewById<TextView>(R.id.tv_title_bar).text = title
    }
    fun getFunctionView()=findViewById<FunctionTextView>(R.id.tv_function)

}