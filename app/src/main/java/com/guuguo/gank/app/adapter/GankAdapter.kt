package com.guuguo.gank.app.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.guuguo.gank.R

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.guuguo.android.lib.extension.getTimeSpan
import com.guuguo.android.lib.extension.safe
import com.guuguo.gank.model.entity.GankModel


class GankAdapter : BaseQuickAdapter<GankModel, BaseViewHolder> {
    constructor() : super(R.layout.item_gank, null)

    constructor(data: List<GankModel>) : super(R.layout.item_gank, data)

    override fun convert(holder: BaseViewHolder, gankBean: GankModel) {
        holder.setText(R.id.tv_content, gankBean.desc)
                .setText(R.id.tv_hint, gankBean.who + " · " + gankBean.publishedAt?.getTimeSpan())
        val image = holder.getView<ImageView>(R.id.iv_image)
        if (gankBean.images.safe().isNotEmpty()) {
            image.visibility = View.VISIBLE
            Glide.with(mContext).asBitmap().load(gankBean.images[0]).apply(RequestOptions().centerCrop()).into(image)
        } else {
            image.visibility = View.GONE
        }
    }


}

