package com.guuguo.android.lib.app

import android.app.Dialog
import android.content.DialogInterface
import com.guuguo.android.dialog.dialog.base.IWarningDialog
import com.trello.rxlifecycle2.LifecycleProvider

/**
 * Package Name : com.hesheng.orderpad.app.base
 *
 * @author : guuguo
 * @since 2018/5/18
 */
interface IView<E> : LifecycleProvider<E> {
    fun loadData()

//    fun dialogLoadingShow(msg: String, canTouchCancel: Boolean = false, maxDelay: Long = 0, listener: DialogInterface.OnDismissListener? = null)
//
//    fun dialogErrorShow(msg: String, listener: DialogInterface.OnDismissListener? = null, delayTime: Int = 1500)
//
//    fun dialogCompleteShow(msg: String, listener: DialogInterface.OnDismissListener? = null, delayTime: Int = 800)
//
//    fun dialogMsgShow(msg: String, btnText: String, listener: (() -> Unit)?): IWarningDialog?
//
//    fun dialogWarningShow(msg: String, cancelStr: String, confirmStr: String, listener: (() -> Unit)? = null): IWarningDialog?
//
//    fun showDialogOnMain(dialog: Dialog)
//
//    fun dialogDismiss()

    var activity: LBaseActivitySupport
}