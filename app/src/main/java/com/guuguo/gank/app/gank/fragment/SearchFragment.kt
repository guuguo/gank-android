package com.guuguo.gank.app.gank.fragment

import android.arch.lifecycle.Observer
import android.databinding.Observable
import android.support.v7.widget.LinearLayoutManager
import android.view.inputmethod.EditorInfo
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.lib.widget.simpleview.SimpleViewHelper
import com.guuguo.gank.R
import com.guuguo.gank.R.id.iv_search
import com.guuguo.gank.app.gank.activity.WebViewActivity
import com.guuguo.gank.app.gank.adapter.GankAdapter
import com.guuguo.gank.app.gank.viewmodel.SearchViewModel
import com.guuguo.gank.base.BaseListFragment
import com.guuguo.gank.databinding.FragmentSearchBinding
import com.guuguo.gank.model.entity.GankModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.toolbar_search.*


class SearchFragment : BaseListFragment<FragmentSearchBinding>() {
    override fun getToolBar() = id_tool_bar

    val viewModel by lazy { SearchViewModel() }

    companion object {
        fun getInstance(): SearchFragment {
            return SearchFragment()
        }
    }

    var page = 1
    val mSearchResultAdapter by lazy {
        GankAdapter()
    }

    override fun isNavigationBack() = false
    override fun getLayoutResId() = R.layout.fragment_search

    var simplerViewHelper: SimpleViewHelper? = null
    override fun initViewModelCallBack() {
        super.initViewModelCallBack()
        setupBaseViewModel(viewModel)
        viewModel.ganksListLiveData.observe(this, Observer {
            it?.let {
                if (it.isRefresh()) {
                    mSearchResultAdapter.setNewData(it.list)
                } else {
                    mSearchResultAdapter.addData(it.list?.toMutableList().safe())
                }
            }
        })
        viewModel.isEmpty.observe(this, Observer {
            if (it == true)
                simplerViewHelper?.showEmpty("搜索结果为空", imgRes = R.drawable.empty_cute_girl_box)
            else {
                simplerViewHelper?.restore()
            }
        })
    }

    override fun loadingStatusChange(it: Boolean) {
        if (it.safe() && isRefresh) {
            simplerViewHelper?.showLoading("正在加载搜索结果")
        } else if (!it.safe()) {
            simplerViewHelper?.restore()
        }
    }

    override fun initView() {
        super.initView()
        mSearchResultAdapter.setOnItemClickListener { _, _, position ->
            val bean = mSearchResultAdapter.getItem(position)!!
            WebViewActivity.intentTo(bean, activity)
        }
        simplerViewHelper = SimpleViewHelper(recycler)
        simplerViewHelper?.showEmpty("请输入搜索关键字", imgRes = R.drawable.empty_cute_girl_box)
        iv_back.setOnClickListener { pop() }
        iv_search.setOnClickListener {
            viewModel.searchText = edt_search.text.toString()
            viewModel.fetchData(true)
        }
        edt_search.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchText = edt_search.text.toString()
                viewModel.fetchData(true)
                true
            } else false
        }

    }

    override fun initRecycler() {
        super.initRecycler()
        binding.recycler.layoutManager = LinearLayoutManager(activity)
        binding.recycler.adapter = mSearchResultAdapter
    }

    override fun onBackPressedSupport(): Boolean {
        pop()
        return true
    }
}
