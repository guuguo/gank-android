package com.guuguo.gank.app.gank.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.databinding.BaseObservable
import android.net.Uri
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.lib.extension.toast
import com.guuguo.gank.app.gank.activity.WebViewActivity
import com.guuguo.gank.base.BaseViewModel
import com.guuguo.gank.model.entity.GankModel
import com.guuguo.gank.source.GankRepository


/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class WebViewModel : BaseViewModel() {
    lateinit var gank: GankModel
    var isFavorite = MutableLiveData<Boolean>()

    fun likeChanged() {
        if (isFavorite.value == true) {
            GankRepository.deleteGank(gank).doOnComplete {
                "已成功移除收藏".toast()
                isFavorite.value = !isFavorite.value.safe()
            }.subscribe()
        } else {
            GankRepository.insertGank(gank).doOnComplete {
                "已成功加入收藏".toast()
                isFavorite.value = !isFavorite.value.safe()
            }.subscribe()
        }
    }

    fun checkFavorite() {
        GankRepository.getGankById(gank._id)
                .doOnSuccess { isFavorite.value = it != null }
                .doOnComplete { isFavorite.value = false }
                .subscribe()
    }
}

