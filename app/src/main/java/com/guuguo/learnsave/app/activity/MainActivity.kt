package com.guuguo.learnsave.app.activity

import `in`.srain.cube.views.ptr.PtrDefaultHandler
import `in`.srain.cube.views.ptr.PtrFrameLayout
import `in`.srain.cube.views.ptr.header.MaterialHeader
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.widget.Toast
import butterknife.bindView
import com.guuguo.learnsave.R
import com.guuguo.learnsave.adapter.MeiziAdapter
import com.guuguo.learnsave.app.base.BaseActivity
import com.guuguo.learnsave.model.entity.GankModel
import com.guuguo.learnsave.presenter.MainPresenter
import com.guuguo.learnsave.util.DisplayUtil
import com.guuguo.learnsave.view.IMainView
import java.util.*


class MainActivity : BaseActivity(), IMainView {
    var page = 1
    var isRefresh = false
    var meiziAdapter: MeiziAdapter? = null
    var meiziList = ArrayList<GankModel>()
    val presenter: MainPresenter by lazy { MainPresenter(activity, this) }

    override fun initPresenter() {
        presenter.init()
    }

    val recycler: RecyclerView by bindView(R.id.recycler)
    val ptrMain: PtrFrameLayout by  bindView(R.id.pfl_main)


    override fun getLayoutResId(): Int {
        return R.layout.activity_main;
    }


    override fun initIView() {
        initPtr()
        initRecycler()
        ptrMain.postDelayed({ ptrMain.autoRefresh() }, 50)
    }

    private fun initRecycler() {
        meiziAdapter = MeiziAdapter(meiziList)
        meiziAdapter?.openLoadMore(12)
        meiziAdapter?.setOnLoadMoreListener {
            presenter.fetchMeiziData(page)
        }

        recycler.layoutManager = StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL)
        recycler.adapter = meiziAdapter

    }

    private fun initPtr() {
        var header = MaterialHeader(activity)
        header.setPtrFrameLayout(ptrMain)
        header.setPadding(0, DisplayUtil.dip2px(15f), 0, DisplayUtil.dip2px(10f))
        ptrMain.headerView = header
        ptrMain.setPtrHandler(object : PtrDefaultHandler() {
            override fun onRefreshBegin(frame: PtrFrameLayout?) {
                page = 1
                isRefresh = true
                presenter.fetchMeiziData(page)
            }
        })
        ptrMain.addPtrUIHandler(header)
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
        if (isRefresh) {
            ptrMain.refreshComplete()
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
            meiziList.clear()
            meiziList.addAll(lMeiziList)
            meiziAdapter?.notifyDataSetChanged()
        } else {
            recycler.post {
                meiziAdapter?.addData(lMeiziList)
            }
        }
    }
}

