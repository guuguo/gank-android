package com.guuguo.android.lib.extension

import android.graphics.drawable.*
import android.os.Build
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.WrappedDrawable


/**
 * Created by 大哥哥 on 2019/11/27 0024.
 */
fun Drawable.changeProgressColor(@ColorInt color: Int): Drawable {
    val drawable = this.mutate()
    when {
        drawable is LayerDrawable -> {
            drawable.findDrawableByLayerId(android.R.id.progress).changeColor(color, mutate = false)
        }
    }
    return drawable
}

fun Drawable.changeColor(@ColorInt color: Int, mutate: Boolean = true): Drawable {
    var drawable = if (mutate) this.mutate() else this
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && drawable is DrawableWrapper)
        drawable = drawable.drawable!!
    when {
        drawable is ShapeDrawable -> {
            drawable.paint.color = color
        }
        drawable is GradientDrawable -> {
            drawable.setColor(color)
        }
        drawable is ColorDrawable -> {
            drawable.setColor(color)
        }
    }
    return drawable
}