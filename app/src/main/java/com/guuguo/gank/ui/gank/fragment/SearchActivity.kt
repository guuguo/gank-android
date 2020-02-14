package com.guuguo.gank.ui.gank.fragment

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import com.guuguo.android.lib.extension.hideKeyboard
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.lib.extension.showKeyboard
import com.guuguo.android.lib.widget.simpleview.StateLayout
import com.guuguo.gank.R
import com.guuguo.gank.ui.gank.activity.WebViewActivity
import com.guuguo.gank.ui.gank.adapter.GankAdapter
import com.guuguo.gank.ui.gank.viewmodel.SearchViewModel
import com.guuguo.baselib.mvvm.BaseListActivity
import com.guuguo.gank.databinding.FragmentSearchBinding
import kotlinx.android.synthetic.main.toolbar_search.*


class SearchActivity : BaseListActivity<FragmentSearchBinding>() {
    companion object {
        const val TRANSLATE_VIEW = "share_search"

        fun intentTo(activity: Activity, view: View) {
            val intent = Intent(activity, SearchActivity::class.java)
            val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, TRANSLATE_VIEW)
            ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle())
        }

    }

    val viewModel by lazy { SearchViewModel() }

    var page = 1
    val mSearchResultAdapter by lazy {
        GankAdapter()
    }

    override fun isCanLoadMore() = false
    override fun isNavigationBack() = false
    override fun getLayoutResId() = R.layout.fragment_search

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
                if (it.isEnd) {
                    mSearchResultAdapter.loadMoreModule?.loadMoreEnd()
                } else {
                    mSearchResultAdapter.loadMoreModule?.isEnableLoadMore=(true)
                }
            }
        })
        viewModel.isEmpty.observe(this, Observer {
            if (it == true)
                binding.state.showEmpty("搜索结果为空")
            else {
                binding.state.restore()
            }
        })
    }

    override fun loadingStatusChange(it: Boolean) {
        if (it && viewModel.isRefresh) {
            edt_search.hideKeyboard()
            binding.state.showLoading("")
        } else if (!it.safe()) {
            binding.state.restore()
        }
    }

    override fun initView() {
        super.initView()
        mSearchResultAdapter.loadMoreModule?.setOnLoadMoreListener{
            viewModel.fetchData(false)
        }
        mSearchResultAdapter.setOnItemClickListener { _, _, position ->
            val bean = mSearchResultAdapter.getItem(position)!!
            WebViewActivity.intentTo(bean, activity)
        }
        StateLayout.loadingDrawableClass = null
        binding.state.showEmpty("请输入搜索关键字")

        iv_back.setOnClickListener {
            onBackPressed()
        }
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
        edt_search.postDelayed({ edt_search.showKeyboard() }, 200)
    }

    override fun onDestroy() {
        edt_search?.hideKeyboard()
        super.onDestroy()
    }

    override fun initRecycler() {
        super.initRecycler()
        binding.recycler.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        binding.recycler.adapter = mSearchResultAdapter
    }
}
