package com.guuguo.gank.extension

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import java.util.*
import java.util.Collections.addAll

/**
 * Created by 大哥哥 on 2016/10/25 0025.
 */
fun <T, K : BaseViewHolder> BaseQuickAdapter<T, K>.updateData(list: List<T>) {
    var index = 0
    if (data?.size == 0) {
        addAll(0, list)
        return
    }
    for (nModel in list) {
        if (data.get(0)!!.equals(nModel))
            break
        index++
    }
    if (index == 0)
        return
    addAll(index, list)

}

private fun <T, K : BaseViewHolder> BaseQuickAdapter<T,K>.addAll(index: Int, list: List<T>) {
    data.addAll(0, list.subList(0, index + list.size))
    notifyItemRangeInserted(0, index + 1)
}