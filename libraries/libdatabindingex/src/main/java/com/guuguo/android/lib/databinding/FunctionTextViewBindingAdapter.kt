package com.guuguo.android.lib.databinding

import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import android.view.View
import android.widget.ImageView
import androidx.core.view.doOnLayout
import androidx.core.view.updateLayoutParams
import com.guuguo.android.lib.extension.dpToPx
import com.guuguo.android.lib.extension.getDrawableCompat
import com.guuguo.android.lib.widget.FunctionTextView


@BindingMethods(BindingMethod(type = FunctionTextView::class, attribute = "android:text", method = "setText"),
        BindingMethod(type = FunctionTextView::class, attribute = "ftv_drawableSrc", method = "setDrawable"),
        BindingMethod(type = FunctionTextView::class, attribute = "ftv_drawableAlign", method = "setDrawableAlign")
        )

class FunctionTextViewBindingAdapter

@BindingAdapter("app:ftv_drawableWidth")
fun FunctionTextView.setDrawableWidthBinding(width: Float) {
    drawableWidth = width.dpToPx().toFloat()
}
@BindingAdapter("app:ftv_drawableHeight")
fun FunctionTextView.setDrawableHeightBinding(height: Float) {
    drawableHeight = height.dpToPx().toFloat()
}
@BindingAdapter("app:ftv_drawableRes")
fun FunctionTextView.setDrawableRes(res: Int) {
    drawable = context.getDrawableCompat(res)
}
