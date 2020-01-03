package com.guuguo.android.lib.widget.simpleview

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.guuguo.android.lib.widget.R

//import com.wang.avi.AVLoadingIndicatorView

class SimpleView {

    val view: View
    val viewHolder: SimpleViewHolder

    constructor(context: Context) : this(context, null)
    constructor(context: Context, viewGroup: ViewGroup?) {
        view = View.inflate(context, R.layout.widget_include_simple_empty_view, viewGroup)
        viewHolder = SimpleViewHolder(view)
    }
}