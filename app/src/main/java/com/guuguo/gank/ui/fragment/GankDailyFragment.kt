package com.guuguo.gank.ui.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.animation.ScaleInAnimation
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.lib.extension.showSnackTip
import com.guuguo.gank.R
import com.guuguo.gank.R.id.*
import com.guuguo.gank.ui.adapter.MeiziAdapter
import com.guuguo.gank.ui.base.BaseActivity
//import com.guuguo.gank.app.fragment.SearchRevealFragment
import com.guuguo.gank.extension.updateData
import com.guuguo.gank.model.entity.GankModel
import com.guuguo.gank.presenter.MainPresenter
import com.guuguo.gank.util.DisplayUtil
import com.guuguo.gank.app.MEIZI
import com.guuguo.gank.app.MEIZI_COUNT
import com.guuguo.gank.app.OmeiziDrawable
import com.guuguo.gank.app.TRANSLATE_GIRL_VIEW
import com.guuguo.gank.ui.activity.GankActivity
import com.guuguo.gank.ui.base.BaseFragment
import com.guuguo.gank.view.IMainView
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.proguard.ac
import com.tencent.bugly.proguard.m
import kotterknife.bindView
import java.io.Serializable
import java.util.*


class GankDailyFragment : BaseFragment(), IMainView {
    var page = 1
    var meiziAdapter = MeiziAdapter()

    val presenter: MainPresenter by lazy { MainPresenter(activity, this) }
    val recycler: RecyclerView by bindView(R.id.recycler)
    val swiper: SwipeRefreshLayout by bindView(R.id.swiper)


    override fun getLayoutResId(): Int {
        return R.layout.view_refresh_recycler;
    }


    override fun initPresenter() {
        presenter.init()
    }

    override fun initIView() {
        initSwiper()
        initRecycler()
        toolbar.setOnClickListener { recycler.smoothScrollToPosition(0) }
        swiper.post {
            swiper.isRefreshing = true
            onRefresh()
        }
    }

    private fun initRecycler() {
//        meiziAdapter.openLoadAnimation(ScaleInAnimation())
        meiziAdapter.setOnLoadMoreListener({
            page++
            presenter.fetchMeiziData(page)
        }, recycler)

        recycler.layoutManager = StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL)
        recycler.adapter = meiziAdapter
        meiziAdapter.setOnItemChildClickListener { _, view, position ->
            when (view!!.id) {
                image -> {
                    val image = view as ImageView
                    OmeiziDrawable = view.getDrawable()
                    val intent = Intent(activity, GankActivity::class.java)
                    intent.putExtra(MEIZI, meiziAdapter.getItem(position) as Serializable)
                    val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, image, TRANSLATE_GIRL_VIEW)
                    ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle())
                    true
                }
                else -> false

            }
        }
    }

    private fun initSwiper() {
        swiper.setOnRefreshListener {
            onRefresh()
        };
    }

    private fun onRefresh() {
        page = 1
        presenter.fetchMeiziData(page)
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
        swiper.isRefreshing = false
    }

    override fun showErrorView(e: Throwable) {
        Toast.makeText(activity, e.message.safe(), Toast.LENGTH_LONG).show()
    }


    override fun showMeiziList(lMeiziList: List<GankModel>) {
        if (lMeiziList.size < MEIZI_COUNT) {
            meiziAdapter.loadMoreEnd(false)
        }
        if (page == 1) {
            meiziAdapter.setNewData(lMeiziList)
        } else {
            meiziAdapter.loadMoreComplete()
            meiziAdapter.addData(lMeiziList)
        }
    }

    override fun showTip(msg: String) {
        showSnackTip(activity.getContainer(), msg)
    }
}

