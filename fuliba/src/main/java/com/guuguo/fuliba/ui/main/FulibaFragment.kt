package com.guuguo.fuliba.ui.main

import androidx.core.view.get
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.guuguo.baselib.extension.*
import com.guuguo.baselib.mvvm.BaseFragment
import com.guuguo.baselib.mvvm.FullscreenWebViewActivity
import com.guuguo.fuliba.R
import com.guuguo.fuliba.databinding.FragmentFulibaBinding
import com.guuguo.fuliba.databinding.FulibaItemBinding
import com.guuguo.fuliba.data.bean.FulibaItemBean
import com.guuguo.fuliba.data.source.FulibaRepository
import kotlinx.coroutines.launch

class FulibaFragment : BaseFragment<FragmentFulibaBinding>() {
    override fun getLayoutResId() = R.layout.fragment_fuliba
    lateinit var adapter: BaseQuickAdapter<FulibaItemBean, BaseViewHolder>
    override fun initView() {
        super.initView()
        adapter = binding.recycler.generate(R.layout.fuliba_item) { h, t ->
            h.getBinding<FulibaItemBinding>()?.bean = t
            h.itemView.setOnClickListener {
                val bean = adapter.getItem(h.adapterPosition)
                FullscreenWebViewActivity.intentTo(mActivity, bean?.url.safe())
            }
        }
        binding.refresh.setOnRefreshListener {
            loadData()
            binding.refresh.isRefreshing = false
        }
    }

    private fun request() {
        launch {
            runCatching {
            val list = FulibaRepository.getFuliItemList(page)
            list?.let {
                adapter.addData(list)
                adapter.setEmptyView(R.layout.widget_include_simple_empty_view)
            }
            }.onFailure {
                adapter.setEmptyView(R.layout.agentweb_error_page)
                adapter.emptyLayout?.get(0)?.setOnClickListener {
                    loadData()
                }
            }
        }

    }

    var page = 1
    override fun loadData() {
        super.loadData()
        request()
    }
}
