package com.guuguo.gank.util

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

fun <T : ViewDataBinding> View.dataBind() = DataBindingUtil.bind<T>(this)!!
fun <T : ViewDataBinding> Activity.dataBind(resId: Int) = DataBindingUtil.setContentView<T>(this, resId)
fun <T : ViewDataBinding> Int.dataBind(inflater: LayoutInflater, viewGroup: ViewGroup? = null, attachToParent: Boolean = false) = DataBindingUtil.inflate<T>(inflater, this, viewGroup, attachToParent)