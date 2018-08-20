package com.guuguo.android.lib.widget.roundview

import android.databinding.BindingAdapter


@BindingAdapter("rv_backgroundColor")
fun com.guuguo.android.lib.widget.roundview.RoundTextView.setRvBackGroundColor(color: Int) {
    this.delegate.backgroundColor = color
}
@BindingAdapter("rv_strokeColor")
fun com.guuguo.android.lib.widget.roundview.RoundTextView.setRvStrokeColor(color: Int) {
    this.delegate.strokeColor = color
}

@BindingAdapter("rv_backgroundColor")
fun RoundBgImageView.setRvBackGroundColor(color: Int) {
    this.delegate.backgroundColor = color
}

@BindingAdapter("rv_backgroundColor")
fun RoundConstraintlayout.setRvBackGroundColor(color: Int) {
    this.delegate.backgroundColor = color
}

@BindingAdapter("rv_backgroundColor")
fun RoundLinearLayout.setRvBackGroundColor(color: Int) {
    this.delegate.backgroundColor = color
}

@BindingAdapter("rv_backgroundColor")
fun RoundFrameLayout.setRvBackGroundColor(color: Int) {
    this.delegate.backgroundColor = color
}
