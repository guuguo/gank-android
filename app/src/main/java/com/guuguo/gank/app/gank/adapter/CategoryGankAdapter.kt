package com.guuguo.gank.app.gank.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.guuguo.android.lib.extension.dpToPx
import com.guuguo.gank.R
import com.guuguo.gank.model.entity.GankModel


class CategoryGankAdapter : BaseQuickAdapter<GankModel, BaseViewHolder> {
    constructor() : super(R.layout.item_gank_category, null)

    constructor(data: List<GankModel>) : super(R.layout.item_gank_category, data)

    override fun convert(holder: BaseViewHolder, gankBean: GankModel) {

        holder.setVisible(R.id.tv_category, holder.layoutPosition == 0 || !gankBean.type.equals(getItem(holder.layoutPosition - 1)!!.type))
                .setText(R.id.tv_category, gankBean.type)
                .setText(R.id.tv_content, gankBean.desc)
                .addOnClickListener(R.id.tv_content)

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
        val drawable = holder.getConvertView().context
                .resources.getDrawable(res)
        /// 这一步必须要做,否则不会显示.  
        drawable.setBounds(0, 0, 20.dpToPx(), 20.dpToPx())
        holder.getView<TextView>(R.id.tv_content).setCompoundDrawables(drawable, null, null, null)
    }


}

