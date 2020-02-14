package com.guuguo.gank.ui.gank.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.guuguo.gank.R

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.guuguo.android.lib.extension.getTimeSpan
import com.guuguo.android.lib.extension.safe
import com.guuguo.gank.model.entity.GankModel


class GankAdapter : BaseQuickAdapter<GankModel, BaseViewHolder>,LoadMoreModule {
    constructor() : super(R.layout.item_gank, null)

    constructor(data: MutableList<GankModel>) : super(R.layout.item_gank, data)

    override fun convert(holder: BaseViewHolder, gankBean: GankModel?) {
        holder.setText(R.id.tv_content, gankBean?.desc)
                .setText(R.id.tv_hint, gankBean?.who + " · " + gankBean?.publishedAt?.getTimeSpan())
        val image = holder.getView<ImageView>(R.id.iv_image)
        if (gankBean?.images.safe().isNotEmpty()) {
            image.visibility = View.VISIBLE
            Glide.with(context).asBitmap().load(gankBean?.images.safe()[0]).apply(RequestOptions().centerCrop()).into(image)
        } else {
            image.visibility = View.GONE
        }
    }


}

