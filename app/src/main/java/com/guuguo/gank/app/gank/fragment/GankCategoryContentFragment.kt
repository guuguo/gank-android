package com.guuguo.gank.app.gank.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.guuguo.android.lib.extension.safe
import com.guuguo.gank.R
import com.guuguo.gank.app.gank.activity.WebViewActivity
import com.guuguo.gank.app.gank.adapter.GankAdapter
import com.guuguo.gank.app.gank.viewmodel.GankCategoryContentViewModel
import com.guuguo.gank.base.BaseListFragment
import com.guuguo.gank.databinding.FragmentGankCategoryContentBinding
import kotlinx.android.synthetic.main.view_refresh_recycler.*


class GankCategoryContentFragment : BaseListFragment<FragmentGankCategoryContentBinding>() {

    var gankAdapter = GankAdapter()

    val viewModel by lazy { GankCategoryContentViewModel() }
    override fun getLayoutResId() = R.layout.fragment_gank_category_content

    companion object {
        const val ARG_GANK_TYPE = "ARG_GANK_TYPE"
        const val GANK_TYPE_STAR = "GANK_TYPE_STAR"
        fun newInstance(title: String): GankCategoryContentFragment {
            val fragment = GankCategoryContentFragment()
            val bundle = Bundle()
            bundle.putString(GankCategoryContentFragment.ARG_GANK_TYPE, title)
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun initVariable(savedInstanceState: Bundle?) {
        super.initVariable(savedInstanceState)
        viewModel.gank_type = arguments!![ARG_GANK_TYPE] as String
    }

    override fun initView() {
        super.initView()
        activity.getToolBar()?.setOnClickListener { recycler.smoothScrollToPosition(0) }
    }

    override fun initViewModelCallBack() {
        super.initViewModelCallBack()
        setupBaseViewModel(viewModel)
        viewModel.ganksListLiveData.observe(this, Observer {
            it?.let {
                if (it.isRefresh()) {
                    gankAdapter.setNewData(it.list)
                } else {
                    gankAdapter.addData(it.list?.toMutableList().safe())
                }
            }
        })
    }

    override fun lazyLoad() {
        super.lazyLoad()
        if (mFirstLazyLoad) {
            onRefresh()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mFirstLazyLoad = true
    }

    override fun initRecycler() {
        super.initRecycler()

        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = gankAdapter
        gankAdapter.setOnItemClickListener { _, _, position ->
            val bean = gankAdapter.getItem(position)!!
            WebViewActivity.intentTo(bean, activity)
        }
    }
}

