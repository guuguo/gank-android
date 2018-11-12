package com.guuguo.gank.app.gank.fragment

import androidx.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.lib.widget.simpleview.SimpleViewHolder
import com.guuguo.android.lib.widget.simpleview.StateLayout
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
    override fun isCanLoadMore() = viewModel.gank_type != GANK_TYPE_STAR

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
        mActivity.getToolBar()?.setOnClickListener { recycler.smoothScrollToPosition(0) }
        StateLayout.loadingDrawableClass = null
        binding.stateLayout.showLoading("")
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
        viewModel.isEmpty.observe(this, Observer {
            if (it == true) {
                binding.stateLayout.showEmpty("没有数据呢")
            } else {
                binding.stateLayout.restore()
            }
        })
    }

    override fun lazyLoad() {
        super.lazyLoad()
        if (mFirstLazyLoad || viewModel.gank_type == GANK_TYPE_STAR) {
            onRefresh()
        }
    }

    override fun initRecycler() {
        super.initRecycler()

        recycler.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        recycler.adapter = gankAdapter
        gankAdapter.setOnItemClickListener { _, _, position ->
            val bean = gankAdapter.getItem(position)!!
            WebViewActivity.intentTo(bean, this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == WebViewActivity.ACTIVITY_WEBVIEW && viewModel.gank_type == GANK_TYPE_STAR)
            onRefresh()
    }
}

