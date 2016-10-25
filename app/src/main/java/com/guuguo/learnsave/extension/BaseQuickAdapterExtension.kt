package com.guuguo.learnsave.extension

import com.chad.library.adapter.base.BaseQuickAdapter
import java.util.*

/**
 * Created by 大哥哥 on 2016/10/25 0025.
 */
fun <T> BaseQuickAdapter<T>.updateData(list: List<T>) {
    var index = 0
    if (data?.size == 0)
        addAll(0, list)
    for (nModel in list) {
        if (data.get(0)!!.equals(nModel))
            break
        index++
    }
    if (index == 0)
        return
    addAll(index, list)

}

private fun <T> BaseQuickAdapter<T>.addAll(index: Int, list: List<T>) {
    data.addAll(0, list.subList(0, index))
    notifyItemRangeInserted(0, index + 1)
}