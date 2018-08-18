package com.guuguo.android.lib.extension

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guuguo.android.dialog.dialog.TipDialog
import com.guuguo.android.dialog.dialog.base.IWarningDialog
import com.guuguo.android.dialog.utils.DialogHelper
import com.guuguo.android.lib.app.LBaseFragmentSupport

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
fun Context.inflateLayout(@LayoutRes layoutId: Int, parent: ViewGroup? = null, attachToRoot: Boolean = false): View = LayoutInflater.from(this).inflate(layoutId, parent, attachToRoot)

fun Activity.dialogLoadingShow(msg: String, canTouchCancel: Boolean = false, maxDelay: Long = 0, listener: DialogInterface.OnDismissListener? = null) {
    DialogHelper.dialogLoadingShow(this, msg, canTouchCancel, maxDelay, listener)
}

fun Activity.dialogErrorShow(msg: String, listener: DialogInterface.OnDismissListener? = null, delayTime: Int = 1500) {
    DialogHelper.dialogStateShow(this, msg, listener, TipDialog.STATE_STYLE.error, delayTime.toLong())
}

fun Activity.dialogCompleteShow(msg: String, listener: DialogInterface.OnDismissListener? = null, delayTime: Int = 800) {
    DialogHelper.dialogStateShow(this, msg, listener, TipDialog.STATE_STYLE.success, delayTime.toLong())
}

fun Activity.dialogMsgShow(msg: String, btnText: String, listener: (() -> Unit)?): IWarningDialog? {
    return DialogHelper.dialogMsgShow(this, msg, btnText, listener)
}

fun Activity.dialogWarningShow(msg: String, cancelStr: String, confirmStr: String, listener: (() -> Unit)? = null): IWarningDialog? {
    return DialogHelper.dialogWarningShow(this, msg, cancelStr, confirmStr, listener)
}

fun Activity.showDialogOnMain(dialog: Dialog) {
    DialogHelper.showDialogOnMain(this, dialog)
}

fun Activity.dialogDismiss() {
    DialogHelper.dialogDismiss()
}


fun LBaseFragmentSupport.dialogLoadingShow(msg: String, canTouchCancel: Boolean = false, maxDelay: Long = 0, listener: DialogInterface.OnDismissListener? = null) {
    DialogHelper.dialogLoadingShow(activity, msg, canTouchCancel, maxDelay, listener)
}

fun LBaseFragmentSupport.dialogErrorShow(msg: String, listener: DialogInterface.OnDismissListener? = null, delayTime: Int = 1500) {
    DialogHelper.dialogStateShow(activity, msg, listener, TipDialog.STATE_STYLE.error, delayTime.toLong())
}

fun LBaseFragmentSupport.dialogCompleteShow(msg: String, listener: DialogInterface.OnDismissListener? = null, delayTime: Int = 800) {
    DialogHelper.dialogStateShow(activity, msg, listener, TipDialog.STATE_STYLE.success, delayTime.toLong())
}

fun LBaseFragmentSupport.dialogMsgShow(msg: String, btnText: String, listener: (() -> Unit)?): IWarningDialog? {
    return DialogHelper.dialogMsgShow(activity, msg, btnText, listener)
}

fun LBaseFragmentSupport.dialogWarningShow(msg: String, cancelStr: String, confirmStr: String, listener: (() -> Unit)? = null): IWarningDialog? {
    return DialogHelper.dialogWarningShow(activity, msg, cancelStr, confirmStr, listener)
}

fun LBaseFragmentSupport.showDialogOnMain(dialog: Dialog) {
    DialogHelper.showDialogOnMain(activity, dialog)
}

fun LBaseFragmentSupport.dialogDismiss() {
    DialogHelper.dialogDismiss()
}
