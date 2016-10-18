package com.guuguo.learnsave.app.activity

import `in`.srain.cube.views.ptr.PtrDefaultHandler
import `in`.srain.cube.views.ptr.PtrFrameLayout
import `in`.srain.cube.views.ptr.header.MaterialHeader
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.widget.Toast
import butterknife.BindView
import com.guuguo.learnsave.R
import com.guuguo.learnsave.adapter.MeiziAdapter
import com.guuguo.learnsave.bean.entity.GankBean
import com.guuguo.learnsave.presenter.MainPresenter
import com.guuguo.learnsave.view.IMainView
import java.util.*


class MainActivity : BaseActivity(), IMainView {

    var page = 1
    var isRefresh = false
    var meiziAdapter: MeiziAdapter? = null
    var meiziList = ArrayList<GankBean>()
    val presenter: MainPresenter by lazy { MainPresenter(activity, this) }

    override fun initPresenter() {
        presenter.init()
    }


    @BindView(R.id.recycler)
    var recycler: RecyclerView? = null;
    @BindView(R.id.pfl_main)
    var pflMain: PtrFrameLayout? = null;


    override fun getLayoutResId(): Int {
        return R.layout.activity_main;
    }


    override fun initIView() {
        initPtr()
        initRecycler()
    }

    private fun initRecycler() {
        recycler?.layoutManager = StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL)
    }

    private fun initPtr() {
        var header = MaterialHeader(activity)
        header.setPtrFrameLayout(pflMain)
        pflMain?.headerView = header
        pflMain?.setPtrHandler(object : PtrDefaultHandler() {
            override fun onRefreshBegin(frame: PtrFrameLayout?) {
                presenter.fetchMeiziData(page)
            }
        })
    }

    override fun showProgress() {
        isRefresh = true
    }

    override fun hideProgress() {
        isRefresh = false
    }

    override fun showNoMoreData() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showErrorView() {
        Toast.makeText(activity, "error", Toast.LENGTH_LONG).show()
    }

    override fun showMeiziList(lMeiziList: MutableList<GankBean>) {
        if (meiziAdapter == null) {
            meiziAdapter = MeiziAdapter()
            recycler!!.adapter = meiziAdapter
        }
        if (isRefresh) {
            meiziList.clear()
            meiziList.addAll(lMeiziList)
        } else
            meiziList.addAll(lMeiziList)
        meiziAdapter!!.setNewData(meiziList)
    }

}
