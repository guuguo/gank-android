package com.guuguo.android.lib.extension


import androidx.annotation.ColorInt
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import android.view.View
import com.guuguo.android.R
import com.guuguo.android.R.id.design_bottom_sheet


fun View.wrapWithBottomSheetDialog() = BottomSheetDialog(context).also { it.setContentView(this) }

fun BottomSheetDialog.setPeekHeight(height: Int) {
    BottomSheetBehavior.from(window!!.decorView.findViewById<View>(design_bottom_sheet)).peekHeight = height
}
fun BottomSheetDialog.setBgColor(color: Int) {
   window!!.decorView.findViewById<View>(design_bottom_sheet).setBackgroundColor(color)
}