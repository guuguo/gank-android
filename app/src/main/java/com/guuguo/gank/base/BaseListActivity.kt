package com.guuguo.gank.base

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.databinding.Observable
import androidx.databinding.ViewDataBinding
import androidx.annotation.CallSuper
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.RecyclerView
import com.guuguo.android.lib.extension.safe
import com.guuguo.gank.R
import com.guuguo.gank.helper.LoadMoreHelper
import com.guuguo.gank.util.ThemeUtils

/**
 * Copyright ©2017 by ruzhan
 */

abstract class BaseListActivity<VB : ViewDataBinding> : MBaseActivity<VB>(), SwipeRefreshLayout.OnRefreshListener {

    protected open var swipeRefresh: SwipeRefreshLayout? = null
    protected open var recyclerView: RecyclerView? = null
    private var listViewModel: BaseListViewModel? = null

    open fun isCanLoadMore() = true
    override fun getLayoutResId() = R.layout.frag_base_list

    override fun setupBaseViewModel(viewModel: BaseViewModel?) {
        this.listViewModel = viewModel as BaseListViewModel?
        super.setupBaseViewModel(viewModel)
    }


    override fun loadingStatusChange(it: Boolean) {
        listViewModel?.apply {
            if (it && isRefresh) {
                swipeRefresh?.isRefreshing = true
            } else if (!it) {
                swipeRefresh?.isRefreshing = false
            }
        }
    }

    @CallSuper
    override fun initView() {
        super.initView()
        initRefresh()
        initRecycler()
    }

    protected open fun initRecycler() {
        recyclerView = binding.root.findViewById(R.id.recycler)
        if (isCanLoadMore())
            recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (LoadMoreHelper.isLoadMore(recyclerView, newState)) {
                        onLoadMore()
                    }
                }
            })
    }

    protected open fun initRefresh() {
        swipeRefresh = binding.root.findViewById(R.id.refresh)
        swipeRefresh?.setOnRefreshListener(this)
        swipeRefresh?.setColorSchemeColors(ContextCompat.getColor(activity, R.color.colorPrimary))
    }

    override fun onRefresh() {
        listViewModel?.fetchData(true)
    }

    fun onLoadMore() {
        listViewModel?.fetchData(false)
    }

}