package com.bianla.commonlibrary.extension

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.DrawableCompat

fun Drawable.tint(@ColorInt color: Int): Drawable {
    return DrawableCompat.wrap(this).mutate().apply {
        DrawableCompat.setTint(this, color)
    }
}

fun ImageView.tint(@ColorInt color: Int) {
    return this.setImageDrawable(drawable.tint(color))
}