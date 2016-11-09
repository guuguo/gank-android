package com.guuguo.learnsave.app.activity

import android.content.Intent
import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.Toast
import butterknife.bindView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.guuguo.learnsave.R
import com.guuguo.learnsave.adapter.MeiziAdapter
import com.guuguo.learnsave.app.base.BaseActivity
import com.guuguo.learnsave.extension.updateData
import com.guuguo.learnsave.model.entity.GankModel
import com.guuguo.learnsave.presenter.MainPresenter
import com.guuguo.learnsave.util.DisplayUtil
import com.guuguo.learnsave.view.IMainView
import java.util.*


class MainActivity : ToolBarActivity(), IMainView {
    var page = 1
    var isRefresh = false
    var meiziAdapter: MeiziAdapter? = null
    var meiziList = ArrayList<GankModel>()
    val presenter: MainPresenter by lazy { MainPresenter(activity, this) }

    override fun initPresenter() {
        presenter.init()
    }

    val recycler: RecyclerView by bindView(R.id.recycler)
    val swiper: SwipeRefreshLayout by  bindView(R.id.swiper)


    override fun getLayoutResId(): Int {
        return R.layout.activity_main;
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
        meiziAdapter = MeiziAdapter(meiziList)
        meiziAdapter?.openLoadMore(12)
        meiziAdapter?.setOnLoadMoreListener {
            presenter.fetchMeiziData(page)
        }

        recycler.layoutManager = StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL)
        recycler.adapter = meiziAdapter
//        recycler.addOnItemTouchListener(object : OnItemClickListener() {
//            override fun SimpleOnItemClick(adapter: BaseQuickAdapter<*>?, view: View?, position: Int) {
//                var model = adapter!!.getItem(position) as GankModel;
//                var intent = Intent(activity, GankActivity::class.java)
//                intent.putExtra(GankActivity.FIELD_DATE, model.publishedAt)
//                startActivity(intent)
//            }
//        })
    }

    private fun initSwiper() {
        swiper.setOnRefreshListener {
            onRefresh()
        };
    }

    private fun onRefresh() {
        page = 1
        isRefresh = true
        presenter.fetchMeiziData(page)
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
        if (isRefresh) {
            swiper.isRefreshing = false
            isRefresh = false
        }
    }

    override fun showNoMoreData() {
        meiziAdapter?.loadComplete()
    }

    override fun showErrorView(e: Throwable) {
        Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()

    }

    override fun showMeiziList(lMeiziList: List<GankModel>) {
        page++
        if (isRefresh) {
            meiziAdapter?.updateData(lMeiziList)
        } else {
            recycler.post {
                meiziAdapter?.addData(lMeiziList)
            }
        }
    }
}

