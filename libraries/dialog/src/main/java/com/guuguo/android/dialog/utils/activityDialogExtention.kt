package com.guuguo.android.dialog.utils

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import com.guuguo.android.dialog.dialog.TipDialog
import com.guuguo.android.dialog.dialog.base.IWarningDialog

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

