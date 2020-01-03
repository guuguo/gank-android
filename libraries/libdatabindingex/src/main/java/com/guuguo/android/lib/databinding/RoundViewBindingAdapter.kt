package com.guuguo.android.lib.databinding

import androidx.databinding.BindingAdapter
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import com.guuguo.android.lib.extension.getColorCompat
import com.guuguo.android.lib.widget.roundview.*


@BindingAdapter("app:rv_backgroundColor")
fun RoundTextView.setRvBackGroundColor(color: Int) {
    this.delegate.backgroundColor = color
}
@BindingAdapter("app:rv_strokeColor")
fun RoundTextView.setRvStrokeColor(color: Int) {
    this.delegate.strokeColor = color
}
@BindingAdapter("app:rv_backgroundColorRes")
fun RoundTextView.setRvBackGroundColorRes(@ColorRes color: Int) {
    this.delegate.backgroundColor = context.getColorCompat(color)
}

@BindingAdapter("app:textColorRes")
fun RoundTextView.setCustomTextColor(@ColorRes color: Int) {
    this.setTextColor(context.getColorCompat(color))
}

@BindingAdapter("app:rv_backgroundColor")
fun RoundBgImageView.setRvBackGroundColor(color: Int) {
    this.delegate.backgroundColor = color
}
@BindingAdapter("app:rv_backgroundColor")
fun RoundRelativeLayout.setRvBackGroundColor(color: Int) {
    this.delegate.backgroundColor = color
}

@BindingAdapter("app:rv_backgroundColor")
fun RoundConstraintLayout.setRvBackGroundColor(color: Int) {
    this.delegate.backgroundColor = color
}

@BindingAdapter("app:rv_backgroundColor")
fun RoundLinearLayout.setRvBackGroundColor(color: Int) {
    this.delegate.backgroundColor = color
}

@BindingAdapter("app:rv_backgroundColor")
fun RoundFrameLayout.setRvBackGroundColor(color: Int) {
    this.delegate.backgroundColor = color
}
@BindingAdapter("app:rv_cornerRadius_BL")
fun RoundLinearLayout.setCornerRadius_BL(radius: Int) {
    this.delegate.cornerRadius_BL = radius
}
@BindingAdapter("app:rv_cornerRadius_BR")
fun RoundLinearLayout.setCornerRadius_BR(radius: Int) {
    this.delegate.cornerRadius_BR = radius
}
@BindingAdapter("app:rv_cornerRadius_TL")
fun RoundLinearLayout.setCornerRadius_TL(radius: Int) {
    this.delegate.cornerRadius_TL = radius
}
@BindingAdapter("app:rv_cornerRadius_TR")
fun RoundLinearLayout.setCornerRadius_TR(radius: Int) {
    this.delegate.cornerRadius_TR = radius
}
