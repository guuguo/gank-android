package com.guuguo.learnsave.adapter

import android.view.ViewGroup
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.guuguo.learnsave.R
import com.guuguo.learnsave.bean.entity.GankBean

import java.text.SimpleDateFormat

import android.R.attr.width
import android.view.View

class MeiziAdapter : BaseQuickAdapter<GankBean> {
    constructor() : super(R.layout.item_category_image, null) {
    }

    constructor(data: List<GankBean>) : super(R.layout.item_category_image, data) {
    }

    override fun convert(holder: BaseViewHolder, gankBean: GankBean) {
        val image=holder.getView<View>(R.id.image) as ImageView
        holder.setText(R.id.who, gankBean.who).setText(R.id.date, SimpleDateFormat("yyyy年MM月dd日").format(gankBean.publishedAt))
        //        Glide.with(mContext).using( new CustomImageSizeUrlLoader(holder.convertView.getContext())).load(new CustomImageSizeModelFutureStudio(gankBean.getUrl())).into((ImageView) holder.getView(R.id.image));
        Glide.with(mContext).load(gankBean.url).into(image).getSize { width, height ->
            val lp = image.getLayoutParams()
            lp.height = height
            image.setLayoutParams(lp)
        }
    }
}
