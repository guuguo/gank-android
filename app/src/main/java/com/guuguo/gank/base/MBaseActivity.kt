package com.guuguo.gank.base

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.CallSuper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guuguo.android.lib.app.LBaseActivitySupport
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.lib.extension.toast
import com.guuguo.gank.R
import netError

/**
 * Created by guodeqing on 7/23/16.
 */

abstract class MBaseActivity<VB : ViewDataBinding> : LBaseActivitySupport() {
    protected open lateinit var binding: VB
    private var viewModel: BaseViewModel? = null
    private lateinit var runCallBack: RunCallBack

    @CallSuper
    override fun initView() {
        super.initView()
        initViewModelCallBack()
    }

    @CallSuper
    protected open fun initViewModelCallBack() {
        setupBaseViewModel(viewModel)
    }

    //父类只处理了网络请求的 观察
    protected open fun setupBaseViewModel(viewModel: BaseViewModel?) {
        if (viewModel == null) {
            return
        }
        this.viewModel = viewModel
        if (!::runCallBack.isInitialized) {
            runCallBack = RunCallBack()
        }
        this.viewModel?.isLoading?.observe(this, runCallBack)
    }

    protected open fun setUpBinding(binding: VB?) {}

    override fun setLayoutResId(layoutResId: Int) {
        binding = DataBindingUtil.setContentView(activity, layoutResId)
        setUpBinding(binding)
    }

    protected open fun loadingStatusChange(isRunning: Boolean) {
        if (isRunning) {
            dialogLoadingShow(getString(R.string.wait))
        } else {
            dialogDismiss()
        }
    }

    protected open fun errorStatusChange(error: Throwable?) {
        error?.netError().toast()
    }

    private inner class RunCallBack : Observer<Boolean> {
        override fun onChanged(t: Boolean?) {
            loadingStatusChange(t.safe())
        }

    }
}



