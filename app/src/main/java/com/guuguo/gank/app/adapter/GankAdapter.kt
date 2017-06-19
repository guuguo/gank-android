package com.guuguo.gank.app.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.guuguo.gank.R

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
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
            Glide.with(mContext).load(gankBean.images[0]).asBitmap().centerCrop().into(image)
        } else {
            image.visibility = View.GONE
        }
//        holder.setText(R.id.tv_content, gankBean.desc)
//
//        var res = R.drawable.ic_other
//        when (gankBean.type) {
//            "iOS" -> res = R.drawable.ic_ios
//            "Android" -> res = R.drawable.ic_android
//            "前端" -> res = R.drawable.ic_web
//            "瞎推荐" -> res = R.drawable.ic_other
//            "休息视频" -> res = R.drawable.ic_relax
//            "拓展资源" -> res = R.drawable.ic_extension
//            "APP" -> res = R.drawable.ic_app
//        }
//        val drawable = ContextCompat.getDrawable(holder.getConvertView().context,res)
//        /// 这一步必须要做,否则不会显示.  
//        drawable.setBounds(0, 0, 20.dpToPx(), 20.dpToPx())
//        holder.getView<TextView>(R.id.tv_content).setCompoundDrawables(drawable, null, null, null)
    }


}

