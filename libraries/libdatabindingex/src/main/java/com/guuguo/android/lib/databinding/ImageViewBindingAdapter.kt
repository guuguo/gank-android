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


@BindingMethods(
        BindingMethod(type = ImageView::class, attribute = "android:src", method = "setImageResource")

)
class ImageViewBindingAdapter

