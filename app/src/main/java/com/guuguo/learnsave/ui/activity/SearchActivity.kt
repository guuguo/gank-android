package com.guuguo.learnsave.ui.activity

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import com.guuguo.learnsave.R
import com.guuguo.learnsave.ui.adapter.CategoryGankAdapter
import com.guuguo.learnsave.ui.base.BaseActivity
import com.guuguo.learnsave.model.GankDays
import com.guuguo.learnsave.model.entity.GankModel
import com.guuguo.learnsave.presenter.BasePresenter
import com.guuguo.learnsave.presenter.DateGankPresenter
import com.guuguo.learnsave.presenter.WebViewPresenter
import com.guuguo.learnsave.view.IBaseView
import com.guuguo.learnsave.view.IDateGankView
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar
import java.util.*
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebSettings
import com.chad.library.adapter.base.animation.ScaleInAnimation
import com.guuguo.android.lib.view.simpleview.SimpleViewHelper
import com.guuguo.learnsave.R.id.activity
import com.guuguo.learnsave.ui.adapter.SearchResultAdapter
import com.guuguo.learnsave.extension.*
import com.guuguo.learnsave.model.retrofit.ApiServer
import com.guuguo.learnsave.util.*
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.toolbar_gank_search.*
import kotlinx.android.synthetic.main.toolbar_search.*
import kotterknife.bindView


class SearchActivity : BaseActivity() {
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
        mSearchResultAdapter.openLoadAnimation(ScaleInAnimation())
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
