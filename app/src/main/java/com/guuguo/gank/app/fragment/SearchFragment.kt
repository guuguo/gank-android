package com.guuguo.gank.app.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.inputmethod.EditorInfo
import com.guuguo.gank.R
import com.guuguo.gank.model.entity.GankModel
import com.chad.library.adapter.base.animation.SlideInLeftAnimation
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.lib.view.simpleview.SimpleViewHelper
import com.guuguo.gank.app.activity.WebViewActivity
import com.guuguo.gank.app.adapter.GankAdapter
import com.guuguo.gank.base.BaseFragment
import com.guuguo.gank.net.ApiServer
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.toolbar_search.*


class SearchFragment : BaseFragment() {
    override fun getToolBar() = id_tool_bar

    companion object {
        fun getInstance(): SearchFragment {
            return SearchFragment()
        }
    }

    val SEARCH_COUNT = 20

    var page = 1
    var mGankBean: GankModel? = null
    val mSearchResultAdapter by lazy {
        GankAdapter()
    }

    override fun isNavigationBack() = false
    override fun getLayoutResId() = R.layout.fragment_search

    var simplerViewHelper: SimpleViewHelper? = null
    override fun initView() {

        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = mSearchResultAdapter
        mSearchResultAdapter.setEnableLoadMore(true)
        mSearchResultAdapter.setOnLoadMoreListener({
            page++
            search(edt_search.text.toString())
        }, recycler)
        mSearchResultAdapter.openLoadAnimation(SlideInLeftAnimation())
        mSearchResultAdapter.setOnItemClickListener { _, view, position ->
            val bean = mSearchResultAdapter.getItem(position)
            if (view!!.id == R.id.tv_content) {
                WebViewActivity.intentTo(bean.url, bean.desc, activity)
            }
        }
        simplerViewHelper = SimpleViewHelper(recycler, false)
        simplerViewHelper?.showEmpty("请输入搜索关键字")
        iv_back.setOnClickListener { pop() }
        iv_search.setOnClickListener {
            clearApiCall()
            page = 1
            search(edt_search.text.toString())
        }
        edt_search.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                page = 1
                search(edt_search.text.toString())
                true
            } else false
        }
    }

    override fun onBackPressedSupport(): Boolean {
        pop()
        return true
    }

    private fun search(searchText: String) {
        if (searchText.isNullOrEmpty()) {
            simplerViewHelper?.showEmpty("请输入搜索关键字")
        } else {
            if (page == 1) {
                simplerViewHelper?.showLoading("正在加载搜索结果")
            }
            addApiCall(ApiServer.getGankSearchResult(searchText, ApiServer.TYPE_ALL, SEARCH_COUNT, page).subscribe(Consumer {
                searchResult ->
                simplerViewHelper?.restore()

                if (page == 1) {
                    if (searchResult.count == 0)
                        simplerViewHelper?.showEmpty("搜索结果为空")
                    else {
                        mSearchResultAdapter.setNewData(searchResult.results)
                    }
                } else {
                    mSearchResultAdapter.loadMoreComplete()
                    if (searchResult.count < SEARCH_COUNT)
                        mSearchResultAdapter.loadMoreEnd()
                    mSearchResultAdapter.addData(searchResult.results!!)
                }
            }, Consumer<Throwable> { error ->
                activity.dialogErrorShow(error.message.safe(), null)
            }))
        }
    }
}
