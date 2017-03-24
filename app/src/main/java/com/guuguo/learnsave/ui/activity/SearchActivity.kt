package com.guuguo.learnsave.ui.activity

import android.content.Context
import android.content.Intent
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import com.guuguo.learnsave.R
import com.guuguo.learnsave.ui.adapter.GankAdapter
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
import com.guuguo.learnsave.R.id.activity
import com.guuguo.learnsave.ui.adapter.SearchResultAdapter
import com.guuguo.learnsave.extension.*
import com.guuguo.learnsave.model.retrofit.ApiServer
import com.guuguo.learnsave.util.*
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.toolbar_gank_search.*
import kotterknife.bindView


class SearchActivity : BaseActivity() {
    val SEARCH_COUNT = 20

    var page = 1
    var mGankBean: GankModel? = null
    val mSearchResultAdapter by lazy {
        SearchResultAdapter()
    }

    override fun getToolBarResId(): Int {
        return R.layout.toolbar_gank_search
    }

    override fun getMenuResId(): Int {
        return R.menu.search_menu
    }


    override fun getLayoutResId(): Int {
        return R.layout.activity_search
    }

    override fun initView() {
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = mSearchResultAdapter
        mSearchResultAdapter.setEnableLoadMore(true)
        mSearchResultAdapter.setOnLoadMoreListener({
            page++
            search()
        }, recycler)
        mSearchResultAdapter.setOnItemClickListener { _, view, position ->
            val bean = mSearchResultAdapter.getItem(position)
            if (view!!.id == R.id.tv_content) {
                WebViewActivity.loadWebViewActivity(bean.url, bean.desc, activity)
                true
            }
            false
        }
        state_layout.isUseAnimation = true
        state_layout.showEmptyView("请输入搜索关键字", R.drawable.empty_cute_girl_box)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menu_search -> {
                page = 1
                search()
                return true
            }
            else -> return super.onOptionsItemSelected(item)

        }
    }

    private fun search() {
        if (page == 1)
            state_layout.showLoadingView()
        if (et_search.text.isNullOrEmpty())
            activity.dialogErrorShow("请输入搜索内容", null)
        else ApiServer.getGankSearchResult(et_search.text.toString(), ApiServer.TYPE_ALL, SEARCH_COUNT, page).subscribe(Consumer {
            searchResult ->
            mSearchResultAdapter.loadMoreComplete()
            state_layout.showContentView()
            if (page == 1) {
                if (searchResult.count == 0)
                    state_layout.showEmptyView()
                else {
                    mSearchResultAdapter.setNewData(searchResult.results)
                }
            } else
                if (searchResult.count < SEARCH_COUNT)
                    mSearchResultAdapter.loadMoreEnd()
            mSearchResultAdapter.addData(searchResult.results!!)
        }, Consumer<Throwable> { error ->
            dialogErrorShow(error.message, null)
        })
    }
}
