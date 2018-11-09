package com.guuguo.gank.base

import androidx.annotation.CallSuper
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.guuguo.gank.R
import com.guuguo.gank.helper.LoadMoreHelper

/**
 * Copyright ©2017 by ruzhan
 */

abstract class BaseListFragment<VB : ViewDataBinding> : BaseFragment<VB>(), androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener {

    protected open var swipeRefresh: SwipeRefreshLayout? = null
    protected open var recyclerView: RecyclerView? = null
    private var listViewModel: BaseListViewModel? = null
    override fun getViewModel(): BaseViewModel? =listViewModel

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
        swipeRefresh?.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorPrimary))
    }

    override fun onRefresh() {
        listViewModel?.fetchData(true)
    }

    fun onLoadMore() {
        listViewModel?.fetchData(false)
    }

}