package com.guuguo.learnsave.adapter

import android.view.ViewGroup
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.guuguo.learnsave.R
import com.guuguo.learnsave.model.entity.GankModel

import java.text.SimpleDateFormat

import android.R.attr.width
import android.graphics.Bitmap
import android.media.Image
import android.util.Log
import android.view.View
import com.bumptech.glide.DrawableTypeRequest
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.BaseTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.target.Target
import com.guuguo.learnsave.app.widget.RatioImageView
import com.guuguo.learnsave.extension.date

class MeiziAdapter : BaseQuickAdapter<GankModel> {
    constructor() : super(R.layout.item_meizi, null) {
    }

    constructor(data: List<GankModel>) : super(R.layout.item_meizi, data) {
    }

    override fun convert(holder: BaseViewHolder, gankBean: GankModel) {
        val image = holder.getView<View>(R.id.image) as RatioImageView
        holder.setText(R.id.date, gankBean.publishedAt?.date())
        //        Glide.with(mContext).using( new CustomImageSizeUrlLoader(holder.convertView.getContext())).load(new CustomImageSizeModelFutureStudio(gankBean.getUrl())).into((ImageView) holder.getView(R.id.image));
        with(gankBean) {
            if (width > 0 && height > 0) {
                image.setOriginalSize(width, height)
            }
        }
        Glide.with(mContext).load(gankBean.url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(object : SimpleTarget<GlideDrawable>() {
                    override fun onResourceReady(resource: GlideDrawable, glideAnimation: GlideAnimation<in GlideDrawable>) {
                        val height = resource.intrinsicHeight
                        val width = resource.intrinsicWidth
                        gankBean.width = width
                        gankBean.height = height

                        image.setTag(gankBean.url)
                        image.setImageDrawable(resource)
                        image.setOriginalSize(width, height)
                    }
                })
    }


}

