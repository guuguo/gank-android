package com.guuguo.gank.app.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.guuguo.gank.R
import com.guuguo.gank.model.entity.GankModel

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import com.bumptech.glide.request.target.*
import com.guuguo.android.lib.extension.getDateSimply
import com.guuguo.android.lib.ui.imageview.RatioImageView
import com.guuguo.gank.util.DisplayUtil
import java.util.*

class MeiziAdapter : BaseQuickAdapter<GankModel, BaseViewHolder> {
    constructor() : super(R.layout.item_meizi, null)

    constructor(data: List<GankModel>) : super(R.layout.item_meizi, data)

    val colors = arrayListOf(Color.parseColor("#bbdefb"),Color.parseColor("#90caf9")
    ,Color.parseColor("#64b5f6"),Color.parseColor("#42a5f5"),Color.parseColor("#2196f3")
    ,Color.parseColor("#1e88e5"),Color.parseColor("#1976d2"),Color.parseColor("#1565c0"))
    val random = Random(1)
    override fun convert(holder: BaseViewHolder, gankBean: GankModel) {
        val image = holder.getView<View>(R.id.image) as RatioImageView
        
        holder.setText(R.id.date, gankBean.who+" ◉ "+gankBean.createdAt?.getDateSimply())
                .addOnClickListener(R.id.image)

        Glide.with(mContext).load(gankBean.getWidthUrl(DisplayUtil.getScreenWidth()))
                .asBitmap()
                .placeholder(ColorDrawable(colors[(Math.random() * colors.size).toInt()]))
                .centerCrop()
                .into(object : BitmapImageViewTarget(image){
                    override fun getSize(cb: SizeReadyCallback?) {
                        cb?.onSizeReady(ImageViewTarget.SIZE_ORIGINAL, ImageViewTarget.SIZE_ORIGINAL)
                    }
                })

    }

    override fun getItemId(position: Int): Long {
        return getItem(position).url.hashCode().toLong()
    }

}

