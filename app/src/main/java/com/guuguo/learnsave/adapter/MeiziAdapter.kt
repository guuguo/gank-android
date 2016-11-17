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
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.media.Image
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import com.bumptech.glide.DrawableTypeRequest
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.BaseTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.target.Target
import com.guuguo.learnsave.app.activity.GankActivity
import com.guuguo.learnsave.app.widget.RatioImageView
import com.guuguo.learnsave.extension.date
import com.guuguo.learnsave.extension.getDateSimply
import com.guuguo.learnsave.util.MEIZI
import com.guuguo.learnsave.util.OmeiziDrawable
import com.guuguo.learnsave.util.TRANSLATE_GIRL_VIEW
import java.io.Serializable

class MeiziAdapter : BaseQuickAdapter<GankModel> {
    constructor() : super(R.layout.item_meizi, null) {
    }

    constructor(data: List<GankModel>) : super(R.layout.item_meizi, data) {
    }

    override fun convert(holder: BaseViewHolder, gankBean: GankModel) {
        val image = holder.getView<View>(R.id.image) as RatioImageView
        val describe = holder.getView<View>(R.id.date)
        describe.visibility = View.GONE
        holder.setText(R.id.date, gankBean.publishedAt?.getDateSimply())
                .addOnClickListener(R.id.image)
 
        
        with(gankBean) {
            if (width > 0 && height > 0) {
                knownImageSize(describe, image, width, height)
            }
        }
        Glide.with(mContext).load(gankBean.url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(object : SimpleTarget<GlideDrawable>() {
                    override fun onResourceReady(resource: GlideDrawable, glideAnimation: GlideAnimation<in GlideDrawable>) {
                        val height = resource.intrinsicHeight
                        val width = resource.intrinsicWidth
                        gankBean.width = width
                        gankBean.height = height

                        image.setImageDrawable(resource)
                        knownImageSize(describe, image, width, height)
                    }
                })
        
    }

    private fun knownImageSize(describe: View, image: RatioImageView, width: Int, height: Int) {
        image.setOriginalSize(width, height)
        describe.visibility = View.VISIBLE
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).url!!.hashCode().toLong()
    }

}

