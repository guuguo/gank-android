package com.guuguo.gank.ui.activity

import android.support.v7.widget.LinearLayoutManager
import com.guuguo.gank.R
import com.guuguo.gank.model.entity.GankModel
import com.chad.library.adapter.base.animation.SlideInLeftAnimation
import com.guuguo.android.lib.view.simpleview.SimpleViewHelper
import com.guuguo.gank.ui.adapter.SearchResultAdapter
import com.guuguo.gank.net.ApiServer
import com.guuguo.gank.base.BaseSwipeBackActivity
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.toolbar_search.*


class SearchActivity : BaseSwipeBackActivity() {
    val SEARCH_COUNT = 20

    var page = 1
    var mGankBean: GankModel? = null
    val mSearchResultAdapter by lazy {
        SearchResultAdapter()
    }

    override fun getToolBarResId(): Int {
        return R.layout.toolbar_search
    }

    override fun isNavigationButtonVisible(): Boolean {
        return false
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_search
    }

    var simplerViewHelper: SimpleViewHelper? = null
    override fun initView() {

        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = mSearchResultAdapter
        mSearchResultAdapter.setEnableLoadMore(true)
        mSearchResultAdapter.setOnLoadMoreListener({
            page++
            search(fsv_search.query)
        }, recycler)
        mSearchResultAdapter.openLoadAnimation(SlideInLeftAnimation())
        mSearchResultAdapter.setOnItemClickListener { _, view, position ->
            val bean = mSearchResultAdapter.getItem(position)
            if (view!!.id == R.id.tv_content) {
                WebViewActivity.loadWebViewActivity(bean.url, bean.desc, activity)
            }
        }
        simplerViewHelper = SimpleViewHelper(recycler, false)
        simplerViewHelper?.showEmpty("请输入搜索关键字")

        fsv_search.setOnHomeActionClickListener { activity.finish() }
        fsv_search.setOnQueryChangeListener { oldQuery, newQuery ->
            clearApiCall()
            page=1
            search(newQuery)
        }
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
                dialogErrorShow(error.message, null)
            }))
        }
    }
}
