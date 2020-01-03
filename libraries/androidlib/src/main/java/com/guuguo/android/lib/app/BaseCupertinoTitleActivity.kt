package com.guuguo.android.lib.app

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import android.view.ViewGroup
import android.widget.TextView
import com.guuguo.android.R
import com.guuguo.android.lib.extension.getColorCompat
import com.guuguo.android.lib.systembar.SystemBarHelper
import com.guuguo.android.lib.widget.FunctionTextView

open class BaseCupertinoTitleActivity : LBaseActivity() {
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
        supportActionBar?.title = ""
    }

    override fun initStatusBar() {
        SystemBarHelper.setHeightAndPadding(activity, getToolBar())
        SystemBarHelper.immersiveStatusBar(activity, 0f)
    }

    override fun getHeaderTitle() = null
    override fun setTitle(title: CharSequence?) {
        findViewById<TextView>(R.id.tv_title_bar).text = title
    }

    fun getFunctionView() = findViewById<FunctionTextView>(R.id.tv_function)
    override fun lightBar(@ColorInt textColor: Int) {
        super.lightBar(textColor)

        getFunctionView().delegate.backgroundColor = Color.WHITE
        getFunctionView().delegate.backgroundPressColor = getColorCompat(R.color.black10)
        getFunctionView().textColor = textColor
        findViewById<TextView?>(R.id.tv_title_bar)?.setTextColor(textColor)
    }

    override fun darkBar(@ColorInt color: Int) {
        super.darkBar(color)

        getFunctionView().delegate.backgroundColor =color
        getFunctionView().delegate.backgroundPressColor = getColorCompat(R.color.white10)
        findViewById<TextView?>(R.id.tv_title_bar)?.setTextColor(Color.WHITE)
        getFunctionView().textColor = Color.WHITE
    }
}