package com.guuguo.android.lib.extension

import android.content.Context
import android.content.Intent
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by 大哥哥 on 2016/10/21 0021.
 */

fun Context.getScreeWidth() = this.resources.displayMetrics.widthPixels
fun Context.getScreeHeight() = this.resources.displayMetrics.heightPixels
val Context.inflater: LayoutInflater
    get() = LayoutInflater.from(this)

@ColorInt
fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)

inline fun <reified T : Any> Context.newIntent() = Intent(this, T::class.java)
fun Context.inflateLayout(@LayoutRes layoutId: Int, parent: ViewGroup? = null, attachToRoot: Boolean = false): View
        = LayoutInflater.from(this).inflate(layoutId, parent, attachToRoot)