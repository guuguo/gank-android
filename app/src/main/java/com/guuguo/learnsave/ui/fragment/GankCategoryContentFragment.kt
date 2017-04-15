package com.guuguo.learnsave.ui.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.*
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView
import com.guuguo.learnsave.R
import com.guuguo.learnsave.R.id.*
import com.guuguo.learnsave.ui.adapter.MeiziAdapter
import com.guuguo.learnsave.ui.base.BaseActivity
//import com.guuguo.learnsave.app.fragment.SearchRevealFragment
import com.guuguo.learnsave.extension.safe
import com.guuguo.learnsave.extension.showSnackTip
import com.guuguo.learnsave.extension.updateData
import com.guuguo.learnsave.model.entity.GankModel
import com.guuguo.learnsave.presenter.MainPresenter
import com.guuguo.learnsave.util.DisplayUtil
import com.guuguo.learnsave.app.MEIZI
import com.guuguo.learnsave.app.MEIZI_COUNT
import com.guuguo.learnsave.app.OmeiziDrawable
import com.guuguo.learnsave.app.TRANSLATE_GIRL_VIEW
import com.guuguo.learnsave.model.Ganks
import com.guuguo.learnsave.model.retrofit.ApiServer
import com.guuguo.learnsave.ui.activity.GankActivity
import com.guuguo.learnsave.ui.activity.WebViewActivity
import com.guuguo.learnsave.ui.adapter.GankAdapter
import com.guuguo.learnsave.ui.adapter.SearchResultAdapter
import com.guuguo.learnsave.ui.base.BaseFragment
import com.guuguo.learnsave.view.IMainView
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.proguard.ac
import com.tencent.bugly.proguard.m
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.view_refresh_recycler.*
import kotterknife.bindView
import java.io.Serializable
import java.util.*


class GankCategoryContentFragment : BaseFragment() {
    var page = 1
    var gankAdapter = GankAdapter()

    var gank_type = "Android"

    companion object {
        val ARG_GANK_TYPE = "ARG_GANK_TYPE"
    }

    override fun getLayoutResId(): Int {
        return R.layout.view_refresh_recycler;
    }

    override fun initVariable() {
        super.initVariable()
        gank_type = arguments[ARG_GANK_TYPE] as String
    }

    override fun initPresenter() {
    }

    override fun initView() {
        super.initView()
        swiper.setOnRefreshListener {
            onRefresh()
        };
        initRecycler()
        toolbar.setOnClickListener { recycler.smoothScrollToPosition(0) }
        swiper.post {
            swiper.isRefreshing = true
            onRefresh()
        }
    }

    override fun lazyLoad() {
        super.lazyLoad()
        if (mFirstLazyLoad)
            fetchGankData(page);
    }

    override fun onDestroy() {
        super.onDestroy()
        mFirstLazyLoad = true
    }

    private fun initRecycler() {
        gankAdapter.setOnLoadMoreListener({
            page++
            fetchGankData(page)
        }, recycler)

        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = gankAdapter
        gankAdapter.setOnItemClickListener { _, view, position ->
            val bean = gankAdapter.getItem(position)
            WebViewActivity.loadWebViewActivity(bean.url, bean.desc, activity)
        }
    }

    private fun initSwiper() {
        swiper.setOnRefreshListener {
            onRefresh()
        };
    }

    private fun onRefresh() {
        page = 1
        fetchGankData(page)
    }

    private fun fetchGankData(page: Int) {
        ApiServer.getGankData(gank_type, MEIZI_COUNT, page)
                .subscribe(object : Consumer<Ganks> {
                    override fun accept(meiziData: Ganks?) {
                        meiziData?.let {
                            showMeiziList(meiziData.results)
                            hideProgress()
                        }
                    }
                }, object : Consumer<Throwable> {
                    override fun accept(error: Throwable) {
                        showErrorView(error)
                        hideProgress()
                    }
                })
    }


    fun hideProgress() {
        swiper.isRefreshing = false
    }

    fun showErrorView(e: Throwable) {
        Toast.makeText(activity, e.message.safe(), Toast.LENGTH_LONG).show()
    }


    fun showMeiziList(lMeiziList: List<GankModel>) {
        if (lMeiziList.size < MEIZI_COUNT) {
            gankAdapter.loadMoreEnd(false)
        }
        if (page == 1) {
            gankAdapter.setNewData(lMeiziList)
        } else {
            gankAdapter.loadMoreComplete()
            gankAdapter.addData(lMeiziList)
        }
    }

    fun showTip(msg: String) {
        showSnackTip(activity.getContainer(), msg)
    }
}

