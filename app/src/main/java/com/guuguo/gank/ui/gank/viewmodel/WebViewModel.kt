package com.guuguo.gank.ui.gank.viewmodel

import androidx.lifecycle.MutableLiveData
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.lib.extension.toast
import com.guuguo.gank.base.BaseViewModel
import com.guuguo.gank.model.entity.GankModel
import com.guuguo.gank.net.EmptyConsumer
import com.guuguo.gank.net.ErrorConsumer
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
        GankRepository.getGankById(gank._id.safe())
                .doOnSuccess { isFavorite.value = it != null }
                .doOnComplete { isFavorite.value = false }
                .subscribe(EmptyConsumer(), ErrorConsumer()).isDisposed
    }
}

