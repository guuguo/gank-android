package com.bianla.commonlibrary.extension

import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.guuguo.android.lib.extension.safe

fun <T> RecyclerView.generate(res: Int, convert: ((helper: BindingViewHolder, item: T) -> Unit)? = null): BaseQuickAdapter<T, BindingViewHolder> {
    val adapter = object : BaseQuickAdapter<T, BindingViewHolder>(res) {
        override fun convert(helper: BindingViewHolder, item: T) {
            convert?.invoke(helper, item)
        }

        override fun onCreateDefViewHolder(parent: ViewGroup?, viewType: Int): BindingViewHolder {
            return BindingViewHolder(getItemView(mLayoutResId, parent))
        }
    }
    adapter.bindToRecyclerView(this)
    return adapter
}

class BindingViewHolder(view: View) : BaseViewHolder(view) {
    fun <T : ViewDataBinding> getBind(): T? {
        return DataBindingUtil.bind<ViewDataBinding>(itemView) as? T
    }
}


/**[typeResMap] 映射实体 列表
 *
 * example:
binding.recycler.generateMultiType<TangbaRecordMultiBean>(
hashMapOf(
TangbaRecordMultiBean.TYPE_FIRST to MultiTypeEntity(R.layout.home_tangba_module_blood_sugar_record_item_first, { h, it ->
}),
TangbaRecordMultiBean.TYPE_LAST to MultiTypeEntity(R.layout.home_tangba_module_blood_sugar_record_item_last, { h, it ->
}),
TangbaRecordMultiBean.TYPE_RECORD to MultiTypeEntity(R.layout.home_tangba_module_blood_sugar_record_item, { h, it ->
})
)
)
 *
 * */
fun <T : MultiItemEntity> androidx.recyclerview.widget.RecyclerView.generateMultiType(typeResMap: Map<Int, MultiTypeEntity<T>>): BaseQuickAdapter<T, BindingViewHolder> {
    val adapter = object : BaseMultiItemQuickAdapter<T, BindingViewHolder>(null) {
        override fun convert(helper: BindingViewHolder, item: T) {
            typeResMap.get(item.itemType)?.convert?.invoke(helper, item)
        }

        override fun onCreateDefViewHolder(parent: ViewGroup?, viewType: Int): BindingViewHolder {
            return BindingViewHolder(getItemView(typeResMap.get(viewType)?.res ?: 0, parent))
        }
    }
    adapter.bindToRecyclerView(this)
    return adapter
}

data class MultiTypeEntity<T : MultiItemEntity>(val res: Int, val convert: ((BindingViewHolder, T) -> Unit)? = null)

/**[itemSam] 1old 2new compare item id*/
/**[contentSam] 1old 2new  compare Content*/
fun <T, K : BaseViewHolder> BaseQuickAdapter<T, K>.setDataDiff(newList: List<T>?
                                                               , itemSam: (T, T?) -> Boolean = { i1, i2 -> i1 == i2 }
                                                               , contentSam: (T, T?) -> Boolean = { i1, i2 -> i1 == i2 }) {
    if (this.data.isEmpty()) {
        setNewData(newList)
    } else {
        val diffCallback = AdapterDiffCallback(this, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
    }
}

class AdapterDiffCallback<T, K : BaseViewHolder>(val adapter: BaseQuickAdapter<T, K>, val newList: List<T>?, val itemSam: (T, T?) -> Boolean = { i1, i2 -> i1 == i2 }
                                                 , val contentSam: (T, T?) -> Boolean = { i1, i2 -> i1 == i2 }) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return adapter.itemCount
    }

    override fun getNewListSize(): Int {
        return newList?.size.safe() + 1
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return itemSam(adapter.data[oldItemPosition], newList?.getOrNull(newItemPosition))
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return contentSam(adapter.data[oldItemPosition], newList?.getOrNull(newItemPosition))
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? { // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}
