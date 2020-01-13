package com.guuguo.gank.ui.fuliba.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.bianla.commonlibrary.extension.BindingViewHolder
import com.bianla.commonlibrary.extension.generate
import com.chad.library.adapter.base.BaseQuickAdapter
import com.guuguo.gank.R
import com.guuguo.gank.base.BaseFragment
import com.guuguo.gank.databinding.FragmentFulibaBinding
import com.guuguo.gank.ui.fuliba.repository.FulibaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FulibaFragment : BaseFragment<FragmentFulibaBinding>() {
    override fun getLayoutResId() = R.layout.fragment_fuliba
    lateinit var adapter: BaseQuickAdapter<String, BindingViewHolder>
    override fun initView() {
        super.initView()
        adapter = binding.recycler.generate(R.layout.fuliba_item_test) { h, t ->
            h.setText(R.id.tv_text, t)
        }
        adapter.setNewData((0..100).map { "${it}真开心${it}" })
    }

    override fun loadData(isFromNet: Boolean) {
        super.loadData(isFromNet)
        launch {
            FulibaRepository.getHomeList()
        }
        binding.refresh.setOnRefreshListener {
            loadData(true)
            binding.refresh.isRefreshing=false
        }
    }
}
