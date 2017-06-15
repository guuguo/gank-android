package com.guuguo.gank.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.guuguo.gank.R

import android.widget.TextView
import android.support.v4.content.ContextCompat
import com.guuguo.android.lib.extension.dpToPx
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

