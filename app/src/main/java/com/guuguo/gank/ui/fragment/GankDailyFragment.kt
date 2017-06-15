package com.guuguo.gank.ui.fragment

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.lib.extension.showSnackTip
import com.guuguo.gank.R
import com.guuguo.gank.ui.adapter.MeiziAdapter
import com.guuguo.gank.model.entity.GankModel
import com.guuguo.gank.constant.MEIZI
import com.guuguo.gank.constant.MEIZI_COUNT
import com.guuguo.gank.constant.OmeiziDrawable
import com.guuguo.gank.constant.TRANSLATE_GIRL_VIEW
import com.guuguo.gank.ui.activity.GankActivity
import com.guuguo.gank.base.BaseFragment
import com.guuguo.gank.databinding.FragmentGankDailyBinding
import com.guuguo.gank.ui.viewmodel.GankDailyViewModel
import com.guuguo.gank.view.IMainView
import kotlinx.android.synthetic.main.view_refresh_recycler.*
import java.io.Serializable


class GankDailyFragment : BaseFragment() {
    var page = 1
    var meiziAdapter = MeiziAdapter()
    lateinit var binding: FragmentGankDailyBinding
    val viewModel by lazy { GankDailyViewModel(this) }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_gank_daily;
    }

    override fun setLayoutResId(inflater: LayoutInflater?, resId: Int, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, resId, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun initPresenter() {
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
            viewModel.fetchMeiziData(page)
        }, recycler)

        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = meiziAdapter
        meiziAdapter.setOnItemChildClickListener { _, view, position ->
            when (view!!.id) {
                R.id.image -> {
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
            swiper.isRefreshing = true
            page = 1
            loadData()
        };
    }

    override fun loadData() {
        viewModel.fetchMeiziData(page)
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

