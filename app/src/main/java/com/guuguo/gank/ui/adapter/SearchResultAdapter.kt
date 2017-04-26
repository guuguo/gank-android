package com.guuguo.gank.ui.adapter

import android.view.ViewGroup
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.guuguo.gank.R
import com.guuguo.gank.model.entity.GankModel

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
import android.widget.Switch
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
import com.guuguo.gank.ui.activity.GankActivity
import com.guuguo.gank.app.MEIZI
import com.guuguo.gank.app.OmeiziDrawable
import com.guuguo.gank.app.TRANSLATE_GIRL_VIEW
import java.io.Serializable
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import com.guuguo.android.lib.extension.dpToPx
import com.guuguo.gank.ui.activity.WebViewActivity
import com.guuguo.gank.model.entity.SearchResultModel


class SearchResultAdapter : BaseQuickAdapter<SearchResultModel,BaseViewHolder> {
    constructor() : super(R.layout.item_gank_search, null)

    constructor(data: List<SearchResultModel>) : super(R.layout.item_gank_search, data)

    override fun convert(holder: BaseViewHolder, gankBean: SearchResultModel) {

        holder.setText(R.id.tv_content, gankBean.desc)

        var res = R.drawable.ic_other
        when (gankBean.type) {
            "iOS" -> res = R.drawable.ic_ios
            "Android" -> res = R.drawable.ic_android
            "前端" -> res = R.drawable.ic_web
            "瞎推荐" -> res = R.drawable.ic_other
            "休息视频" -> res = R.drawable.ic_relax
            "拓展资源" -> res = R.drawable.ic_extension
            "APP" -> res = R.drawable.ic_app
        }
        val drawable = ContextCompat.getDrawable(holder.getConvertView().context,res)
        /// 这一步必须要做,否则不会显示.  
        drawable.setBounds(0, 0, 20.dpToPx(), 20.dpToPx())
        holder.getView<TextView>(R.id.tv_content).setCompoundDrawables(drawable, null, null, null)
    }


}

