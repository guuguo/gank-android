package com.guuguo.android.lib.databinding

import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.doOnLayout
import androidx.core.view.updateLayoutParams
import com.guuguo.android.lib.extension.dpToPx
import com.guuguo.android.lib.extension.getColorCompat
import com.guuguo.android.lib.extension.getDrawableCompat
import com.guuguo.android.lib.ktx.sizeDrawable
import com.guuguo.android.lib.ktx.width
import com.guuguo.android.lib.widget.FunctionTextView
import com.guuguo.android.lib.widget.roundview.RoundFrameLayout
import org.w3c.dom.Text


//@BindingMethods(
//        BindingMethod(type = ImageView::class, attribute = "android:src", method = "setImageResource")
//
//)
//class ViewBindingAdapter

/**dp*/
@BindingAdapter("app:textColorRes")
fun TextView.setTextColorRes(colorRes: Int) {
    this.setTextColor(context.getColorCompat(colorRes))
}

@BindingAdapter("app:drawableWidth", "app:drawableHeight")
fun TextView.setDrawableSize(drawableWidth: Int, drawableHeight: Int) {
    sizeDrawable(drawableWidth, drawableHeight)
}