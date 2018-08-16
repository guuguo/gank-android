package com.guuguo.gank.base

import android.databinding.DataBindingUtil
import android.databinding.Observable
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.CallSuper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guuguo.android.lib.app.LBaseFragmentSupport
import com.guuguo.android.lib.extension.toast
import com.guuguo.gank.R
import com.guuguo.gank.app.App
import netError

/**
 * Created by guodeqing on 7/23/16.
 */

abstract class BaseFragment<VB : ViewDataBinding> : LBaseFragmentSupport() {
    protected open lateinit var binding: VB
    private var viewModel: BaseViewModel? = null
    private lateinit var runCallBack: RunCallBack

    override fun onDestroy() {
        viewModel?.isRunning?.removeOnPropertyChangedCallback(runCallBack)
        super.onDestroy()
    }

    @CallSuper
    override fun initView() {
        super.initView()
        initViewModelCallBack()
    }

    @CallSuper
    protected open fun initViewModelCallBack() {
        setupBaseViewModel(viewModel)
    }

    protected open fun setupBaseViewModel(viewModel: BaseViewModel?) {
        if (viewModel == null) {
            return
        }
        this.viewModel = viewModel
        if (!::runCallBack.isInitialized) {
            runCallBack = RunCallBack()
        }
        this.viewModel?.isRunning?.addOnPropertyChangedCallback(runCallBack)
    }

    protected open fun setUpBinding(binding: VB?) {}

    open fun showProgressDialog(messageRes: Int) {
        dialogLoadingShow(getString(messageRes))
    }

    open fun showProgressDialog(messageRes: String) {
        dialogLoadingShow(messageRes)
    }

    open fun dismissProgressDialog() {
        dialogDismiss()
    }

    override fun setLayoutResId(inflater: LayoutInflater?, resId: Int, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater!!, resId, container, false)
        setUpBinding(binding)
        return binding.root
    }

    protected open fun runStatusChange(isRunning: Boolean) {
        if (isRunning) {
            showProgressDialog(R.string.wait)
        } else {
            dismissProgressDialog()
        }
    }

    protected open fun errorStatusChange(error: Throwable?) {
        error?.netError().toast()
    }

    private inner class ErrorCallBack : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable, propertyId: Int) {
            errorStatusChange(viewModel!!.isError.get())
        }
    }

    private inner class RunCallBack : Observable.OnPropertyChangedCallback() {

        override fun onPropertyChanged(sender: Observable, propertyId: Int) {
            runStatusChange(viewModel!!.isRunning.get())
        }
    }
}
