package com.guuguo.android.lib.extension

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * 大哥哥 创造于 2016/10/21 0021.
 * 项目 androidLib
 */

/**
 * Extension method to provide simpler access to {@link View#getResources()#getString(int)}.
 */
fun View.getString(stringResId: Int): String = resources.getString(stringResId)

/**
 * Extension method to provide show keyboard for View.
 */
fun EditText.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}


/**
 * Extension method to provide hide keyboard for [View].
 */
fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

/**
 * Extension method to provide quicker access to the [LayoutInflater] from a [View].
 */
fun View.getNameAndHashCode(): String {
    return javaClass.simpleName + "@" + Integer.toHexString(System.identityHashCode(this))
}


fun View.getActivity(): Activity? {
    var context=this.context
    while (context is ContextWrapper) {
        if (context is Activity) {
            return context
        }
        context = context.baseContext
    }
    return null
}
fun Context.getActivity(): Activity? {
    var context=this
    while (context is ContextWrapper) {
        if (context is Activity) {
            return context
        }
        context = context.baseContext
    }
    return null
}