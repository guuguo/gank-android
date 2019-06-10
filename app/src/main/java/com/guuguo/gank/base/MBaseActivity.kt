package com.guuguo.gank.base

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.annotation.CallSuper
import com.guuguo.android.dialog.utils.dialogDismiss
import com.guuguo.android.dialog.utils.dialogLoadingShow
import com.guuguo.android.lib.app.LBaseActivity
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.lib.extension.toast
import com.guuguo.gank.R
import com.guuguo.gank.util.ThemeUtils
import netError

/**
 * Created by guodeqing on 7/23/16.
 */

abstract class MBaseActivity<VB : ViewDataBinding> : LBaseActivity() {
    protected open lateinit var binding: VB
    private var viewModel: BaseViewModel? = null
    private lateinit var runCallBack: RunCallBack

    open fun getViewModel(): BaseViewModel? = null
    @CallSuper
    override fun initView() {
        super.initView()
        initViewModelCallBack()
    }
    override fun initVariable(savedInstanceState: Bundle?) {
        super.initVariable(savedInstanceState)
        ThemeUtils.onActivityCreateSetTheme(activity)
    }
    protected open fun initViewModelCallBack() {
        setupBaseViewModel(getViewModel())
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



