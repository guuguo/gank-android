package com.guuguo.baselib.bindingadapter

import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.guuguo.android.lib.extension.getColorCompat
import com.guuguo.baselib.R

@BindingAdapter("url")
fun ImageView.setImageUrl(url: String?) {
    if (url != null) {
        Glide.with(this).load(url).placeholder(ColorDrawable(context.getColorCompat(R.color.black20))).into(this)
    }
}