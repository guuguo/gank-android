package com.guuguo.android.lib.app

import android.support.v7.widget.Toolbar
import com.guuguo.android.R

open class BaseTitleActivity : LBaseActivitySupport() {
    override fun getLayoutResId() = R.layout.base_activity_simple_back
    override fun getToolBar(): Toolbar = findViewById(R.id.id_tool_bar)

}