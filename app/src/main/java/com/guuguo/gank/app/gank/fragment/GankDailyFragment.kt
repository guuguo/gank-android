package com.guuguo.gank.app.gank.fragment

import android.arch.lifecycle.Observer
import android.support.v7.widget.LinearLayoutManager
import android.widget.ImageView
import com.guuguo.android.lib.extension.safe
import com.guuguo.gank.R
import com.guuguo.gank.app.gank.activity.GankActivity
import com.guuguo.gank.app.gank.adapter.MeiziAdapter
import com.guuguo.gank.app.gank.viewmodel.GankDailyViewModel
import com.guuguo.gank.base.BaseListFragment
import com.guuguo.gank.constant.OmeiziDrawable
import com.guuguo.gank.databinding.FragmentGankDailyBinding
import kotlinx.android.synthetic.main.view_refresh_recycler.*


class GankDailyFragment : BaseListFragment<FragmentGankDailyBinding>() {
    var meiziAdapter = MeiziAdapter()

    val viewModel by lazy { GankDailyViewModel() }
    override fun isNavigationBack() = false
    override fun isCanLoadMore() = false
    override fun getLayoutResId(): Int {
        return R.layout.fragment_gank_daily
    }

    override fun setUpBinding(binding: FragmentGankDailyBinding?) {
        super.setUpBinding(binding)
        binding?.viewModel = viewModel
    }

    override fun initView() {
        super.initView()
        mActivity.getToolBar()?.setOnClickListener { recycler.smoothScrollToPosition(0) }
    }

    override fun initViewModelCallBack() {
        super.initViewModelCallBack()
        setupBaseViewModel(viewModel)
        viewModel.ganksListLiveData.observe(this, Observer {
            it?.let {
                if (it.isRefresh()) {
                    meiziAdapter.setNewData(it.list)
                } else {
                    meiziAdapter.addData(it.list?.toMutableList().safe())
                }
            }
        })
    }

    override fun loadingStatusChange(it: Boolean) {
        super.loadingStatusChange(it)
        if (it) {
        } else {
            meiziAdapter.loadMoreComplete()
        }
    }

    override fun initRecycler() {
        super.initRecycler()
        meiziAdapter.setOnLoadMoreListener({
            viewModel.fetchData(false)
        }, recycler)

        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = meiziAdapter
        meiziAdapter.setOnItemChildClickListener { _, view, position ->
            when (view!!.id) {
                R.id.iv_image -> {
                    val image = view as ImageView
                    OmeiziDrawable = view.drawable
                    GankActivity.intentTo(activity, image, ArrayList(meiziAdapter.data),position)
                    true
                }
                else -> false
            }
        }
    }

    override fun loadData() {
        viewModel.getMeiziData()
        viewModel.fetchData(true)
        super.loadData()
    }


}

