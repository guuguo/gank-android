package com.guuguo.learnsave.app.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.AppBarLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.ActionBar
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Toast
import butterknife.bindView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.flyco.systembar.SystemBarHelper
import com.guuguo.learnsave.R
import com.guuguo.learnsave.adapter.MeiziAdapter
import com.guuguo.learnsave.app.base.BaseActivity
import com.guuguo.learnsave.model.entity.GankModel
import com.guuguo.learnsave.presenter.MainPresenter
import com.guuguo.learnsave.util.DisplayUtil
import com.guuguo.learnsave.view.IMainView
import java.util.*


abstract class ToolBarActivity : BaseActivity() {

    val appbar by bindView<AppBarLayout>(R.id.app_bar)
    val toolbar by bindView<Toolbar>(R.id.toolbar)
    var actionBar: ActionBar? = null

    open protected fun isBackVisible(): Boolean {
        return false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolBar()
    }

    open protected fun initToolBar() {
        SystemBarHelper.immersiveStatusBar(activity, 0f)
        SystemBarHelper.setHeightAndPadding(activity, toolbar)
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        
        actionBar?.setDisplayHomeAsUpEnabled(isBackVisible());
        if (isBackVisible()) toolbar.setNavigationOnClickListener { onBackPressed() }
    }
}

