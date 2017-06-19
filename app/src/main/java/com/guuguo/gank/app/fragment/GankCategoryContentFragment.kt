package com.guuguo.gank.app.fragment

import android.os.Bundle
import android.support.v7.widget.*
import android.widget.Toast
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.lib.extension.showSnackTip
import com.guuguo.gank.R
//import com.guuguo.gank.app.fragment.SearchRevealFragment
import com.guuguo.gank.model.entity.GankModel
import com.guuguo.gank.constant.MEIZI_COUNT
import com.guuguo.gank.model.Ganks
import com.guuguo.gank.net.ApiServer
import com.guuguo.gank.app.activity.WebViewActivity
import com.guuguo.gank.app.adapter.GankAdapter
import com.guuguo.gank.app.adapter.GankWithCategoryAdapter
import com.guuguo.gank.base.BaseFragment
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.view_refresh_recycler.*
import java.util.*


class GankCategoryContentFragment : BaseFragment() {

    var page = 1
    var gankAdapter = GankAdapter()
    var gank_type = "Android"

    companion object {
        val ARG_GANK_TYPE = "ARG_GANK_TYPE"
        fun newInstance(title: String): GankCategoryContentFragment {
            val fragment = GankCategoryContentFragment()
            val bundle = Bundle()
            bundle.putString(GankCategoryContentFragment.ARG_GANK_TYPE, title)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_gank_category_content;
    }

    override fun initVariable(savedInstanceState: Bundle?) {
        super.initVariable(savedInstanceState)
        gank_type = arguments[ARG_GANK_TYPE] as String
    }

    override fun initView() {
        super.initView()
        swiper.setOnRefreshListener {
            onRefresh()
        };
        initRecycler()
        activity.getToolBar()?.setOnClickListener { recycler.smoothScrollToPosition(0) }
        swiper.isRefreshing = true
    }

    override fun lazyLoad() {
        super.lazyLoad()
        if (mFirstLazyLoad) {
            swiper.isRefreshing = true
            swiper.postDelayed({
                onRefresh()
            }, 200)

        }
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
            WebViewActivity.intentTo(bean.url, bean.desc, activity)
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
                .subscribe(object : Consumer<Ganks<ArrayList<GankModel>>> {
                    override fun accept(meiziData: Ganks<ArrayList<GankModel>>?) {
                        meiziData?.let {
                            showMeiziList(meiziData.results!!)
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
        showSnackTip(contentView!!, msg)
    }
}

