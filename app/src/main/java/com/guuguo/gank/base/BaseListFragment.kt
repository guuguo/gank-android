package com.guuguo.gank.base

import android.databinding.Observable
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import com.guuguo.gank.R
import com.guuguo.gank.helper.LoadMoreHelper
import kotlinx.android.synthetic.main.activity_webview.*

/**
 * Copyright ©2017 by ruzhan
 */

abstract class BaseListFragment<VB : ViewDataBinding> : BaseFragment<VB>(), SwipeRefreshLayout.OnRefreshListener {

    protected open var swipeRefresh: SwipeRefreshLayout? = null
    protected open var recyclerView: RecyclerView? = null
    private var listViewModel: BaseListViewModel? = null

    //状态是加载更多还是刷新
    private var isRefresh = true
    protected val layout: Int
        get() = R.layout.frag_base_list

    override fun setupBaseViewModel(viewModel: BaseViewModel?) {
        this.listViewModel = viewModel as BaseListViewModel?
        viewModel?.refreshing?.let {
            it.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    if (it.get() && isRefresh) {
                        swipeRefresh?.isRefreshing = true
                    } else if (!it.get()) {
                        swipeRefresh?.isRefreshing = false
                    }
                }
            })
        }
        super.setupBaseViewModel(viewModel)
    }

    @CallSuper
    override fun initView() {
        super.initView()
        initRefresh()
        initRecycler()
    }

    protected open fun initRecycler() {
        recyclerView = binding.root.findViewById(R.id.recycler)
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
        isRefresh = true
        listViewModel?.fetchData(isRefresh)
    }

    fun onLoadMore() {
        isRefresh = false
        listViewModel?.fetchData(isRefresh)
    }

//    override fun errorStatusChange(error: Throwable?) {
//        if (isRefresh)
//            error.
//    }

    override fun runStatusChange(isRunning: Boolean) {
        listViewModel?.refreshing?.set(isRunning)
    }
}