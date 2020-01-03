package com.guuguo.android.lib.databinding

import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.doOnLayout
import androidx.core.view.updateLayoutParams
import com.guuguo.android.lib.extension.dpToPx
import com.guuguo.android.lib.extension.getDrawableCompat
import com.guuguo.android.lib.ktx.width
import com.guuguo.android.lib.widget.FunctionTextView
import com.guuguo.android.lib.widget.roundview.RoundFrameLayout


//@BindingMethods(
//        BindingMethod(type = ImageView::class, attribute = "android:src", method = "setImageResource")
//
//)
//class ViewBindingAdapter

/**dp*/
@BindingAdapter("app:layout_width")
fun View.setLayoutWidth(width: Int) {
    this.updateLayoutParams<ViewGroup.LayoutParams> {
        this.width = width.dpToPx()
    }
}
@BindingAdapter("app:layout_height")
fun View.setLayoutHeight(height: Int) {
    this.updateLayoutParams<ViewGroup.LayoutParams> {
        this.height = height.dpToPx()
    }
}

// 根据View的高度和宽高比，设置高度
@BindingAdapter("app:heightWidthRatio")
fun View.setWidthHeightRatio(ratio: Double) {
    doOnLayout {
        val width = this@setWidthHeightRatio.width
        if (width > 0) {
            this@setWidthHeightRatio.layoutParams.height = (width * ratio).toInt()
        }
    }
}

// 设置高度
@BindingAdapter("app:height")
fun View.setHeight(height: Int) {
    this.updateLayoutParams {
        this.height = height
    }
}

// 设置宽度
@BindingAdapter("app:width")
fun View.setWidth(width: Int) {
    this.updateLayoutParams {
        this.width = width
    }
}
