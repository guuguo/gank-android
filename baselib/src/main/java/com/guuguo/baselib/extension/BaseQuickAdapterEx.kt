package com.guuguo.baselib.extension

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.util.getItemView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.guuguo.android.lib.extension.safe

fun <T> RecyclerView.generate(
    res: Int,
    diffCallback: DiffUtil.ItemCallback<T>? = null,
    convert: ((helper: BaseViewHolder, item: T?) -> Unit)? = null
): BaseQuickAdapter<T, BaseViewHolder> {
    val callback = diffCallback ?: object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }
    }
    val adapter = object : BaseQuickAdapter<T, BaseViewHolder>(res) {
        override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
            DataBindingUtil.bind<ViewDataBinding>(viewHolder.itemView)
        }

        override fun convert(helper: BaseViewHolder, item: T?) {
            convert?.invoke(helper, item)
        }
    }
    adapter.setDiffCallback(callback)
    this.adapter = adapter
    return adapter
}

class QuickBindingViewHolder(view: View) : BaseViewHolder(view) {
    fun <T : ViewDataBinding> getBind(): T? {
        return DataBindingUtil.bind<ViewDataBinding>(itemView) as? T
    }
}

class BindingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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
fun <T : MultiItemEntity> RecyclerView.generateMultiType(typeResMap: Map<Int, MultiTypeEntity<T>>): BaseQuickAdapter<T, BaseViewHolder> {
    val adapter = object : BaseMultiItemQuickAdapter<T, BaseViewHolder>(null) {
        override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
            super.onItemViewHolderCreated(viewHolder, viewType)
            DataBindingUtil.bind<ViewDataBinding>(viewHolder.itemView)
        }

        override fun onCreateDefViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): BaseViewHolder {
            return BaseViewHolder(parent.getItemView(typeResMap[viewType]?.res ?: 0))

        }

        override fun convert(helper: BaseViewHolder, item: T?) {
            typeResMap[item?.itemType]?.convert?.invoke(helper, item)
        }
    }
    this.adapter = adapter
    return adapter
}

data class MultiTypeEntity<T : MultiItemEntity>(
    val res: Int,
    val convert: ((BaseViewHolder, T?) -> Unit)? = null
)

/**[itemSam] 1old 2new compare item id*/
/**[contentSam] 1old 2new  compare Content*/
fun <T, K : BaseViewHolder> BaseQuickAdapter<T, K>.setDataDiff(
    newList: MutableList<T>?
    , itemSam: (T, T?) -> Boolean = { i1, i2 -> i1 == i2 }
    , contentSam: (T, T?) -> Boolean = { i1, i2 -> i1 == i2 }
) {
    if (this.data.isEmpty()) {
        setNewData(newList)
    } else {
        val diffCallback = AdapterDiffCallback(this, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
    }
}

class AdapterDiffCallback<T, K : BaseViewHolder>(
    val adapter: BaseQuickAdapter<T, K>,
    val newList: List<T>?,
    val itemSam: (T, T?) -> Boolean = { i1, i2 -> i1 == i2 }
    ,
    val contentSam: (T, T?) -> Boolean = { i1, i2 -> i1 == i2 }
) : DiffUtil.Callback() {
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
    override fun getChangePayload(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Any? { // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}
