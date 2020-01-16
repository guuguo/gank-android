package com.guuguo.fuliba.ui.main

import com.bianla.commonlibrary.extension.BindingViewHolder
import com.bianla.commonlibrary.extension.generate
import com.chad.library.adapter.base.BaseQuickAdapter
import com.guuguo.baselib.mvvm.BaseFragment
import com.guuguo.fuliba.R
import com.guuguo.fuliba.databinding.FragmentFulibaBinding
import com.guuguo.fuliba.databinding.FulibaItemBinding
import com.guuguo.fuliba.model.FulibaItemBean
import com.guuguo.fuliba.ui.fuliba.repository.FulibaRepository
import kotlinx.coroutines.launch

class FulibaFragment : BaseFragment<FragmentFulibaBinding>() {
    override fun getLayoutResId() = R.layout.fragment_fuliba
    lateinit var adapter: BaseQuickAdapter<FulibaItemBean, BindingViewHolder>
    override fun initView() {
        super.initView()
        adapter = binding.recycler.generate(R.layout.fuliba_item) { h, t ->
            h.getBind<FulibaItemBinding>()?.bean = t
        }
        binding.refresh.setOnRefreshListener {
            loadData(true)
            binding.refresh.isRefreshing = false
        }
    }

    override fun loadData(isFromNet: Boolean) {
        super.loadData(isFromNet)
        launch {
            val list = FulibaRepository.getHomeList()
            adapter.setNewData(list)
        }
    }
}
