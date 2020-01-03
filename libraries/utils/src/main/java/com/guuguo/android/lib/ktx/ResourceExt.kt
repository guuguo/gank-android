package com.guuguo.android.lib.ktx

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.View

/**
 * Description: 资源操作相关
 * Create by dance, at 2018/12/11
 */

fun Context.color(id: Int) = resources.getColor(id)

fun Context.string(id: Int) = resources.getString(id)

fun Context.stringArray(id: Int) = resources.getStringArray(id)

fun Context.drawable(id: Int) = resources.getDrawable(id)

fun Context.dimenPx(id: Int) = resources.getDimensionPixelSize(id)


fun View.color(id: Int) = context.color(id)

fun View.string(id: Int) = context.string(id)

fun View.stringArray(id: Int) = context.stringArray(id)

fun View.drawable(id: Int) = context.drawable(id)

fun View.dimenPx(id: Int) = context.dimenPx(id)


fun androidx.fragment.app.Fragment.color(id: Int) = context!!.color(id)

fun androidx.fragment.app.Fragment.string(id: Int) = context!!.string(id)

fun androidx.fragment.app.Fragment.stringArray(id: Int) = context!!.stringArray(id)

fun androidx.fragment.app.Fragment.drawable(id: Int) = context!!.drawable(id)

fun androidx.fragment.app.Fragment.dimenPx(id: Int) = context!!.dimenPx(id)


fun androidx.recyclerview.widget.RecyclerView.ViewHolder.color(id: Int) = itemView.color(id)

fun androidx.recyclerview.widget.RecyclerView.ViewHolder.string(id: Int) = itemView.string(id)

fun androidx.recyclerview.widget.RecyclerView.ViewHolder.stringArray(id: Int) = itemView.stringArray(id)

fun androidx.recyclerview.widget.RecyclerView.ViewHolder.drawable(id: Int) = itemView.drawable(id)

fun androidx.recyclerview.widget.RecyclerView.ViewHolder.dimenPx(id: Int) = itemView.dimenPx(id)