package com.guuguo.gank.app.fragment

import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.guuguo.gank.R
import com.guuguo.gank.app.adapter.MeiziAdapter
import com.guuguo.gank.model.entity.GankModel
import com.guuguo.gank.constant.MEIZI_COUNT
import com.guuguo.gank.constant.OmeiziDrawable
import com.guuguo.gank.app.activity.GankActivity
import com.guuguo.gank.base.BaseFragment
import com.guuguo.gank.databinding.FragmentGankDailyBinding
import com.guuguo.gank.app.viewmodel.GankDailyViewModel
import kotlinx.android.synthetic.main.view_refresh_recycler.*


class GankDailyFragment : BaseFragment() {
    var page = 1
    var meiziAdapter = MeiziAdapter()
    lateinit var binding: FragmentGankDailyBinding
    val viewModel by lazy { GankDailyViewModel(this) }
    override fun isNavigationBack() = false
    override fun getLayoutResId(): Int {
        return R.layout.fragment_gank_daily;
    }

    override fun setLayoutResId(inflater: LayoutInflater?, resId: Int, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, resId, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun initView() {
        super.initView()
        initSwiper()
        initRecycler()
        activity.getToolBar()?.setOnClickListener { recycler.smoothScrollToPosition(0) }
        swiper.isRefreshing = true
    }

    private fun initRecycler() {
        meiziAdapter.setOnLoadMoreListener({
            page++
            viewModel.getMeiziDataFromNet(page)
        }, recycler)

        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = meiziAdapter
        meiziAdapter.setOnItemChildClickListener { _, view, position ->
            when (view!!.id) {
                R.id.iv_image -> {
                    val image = view as ImageView
                    OmeiziDrawable = view.getDrawable()
                    GankActivity.intentTo(activity, image, meiziAdapter.getItem(position))

                    true
                }
                else -> false

            }
        }
    }

    private fun initSwiper() {
        swiper.setOnRefreshListener {
            swiper.isRefreshing = true
            page = 1
            loadData()
        };
    }

    override fun loadData() {
        viewModel.getMeiziData()
        viewModel.getMeiziDataFromNet(page)
        super.loadData()
    }


    fun setUpMeiziList(lMeiziList: List<GankModel>) {
        swiper.isRefreshing = false
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
}

