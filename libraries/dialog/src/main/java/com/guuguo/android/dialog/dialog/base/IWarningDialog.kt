package com.guuguo.android.dialog.dialog.base

import android.content.Context
import android.view.View
import com.guuguo.android.dialog.base.BaseDialog

//子类必须实现 construct(context)
abstract class  IWarningDialog (context: Context) : BaseDialog<IWarningDialog>(context) {

    abstract fun title(title: String): IWarningDialog
    abstract fun message(message: String): IWarningDialog
    abstract fun btnNum(btnNum: Int): IWarningDialog
    abstract fun btnText(vararg text: String): IWarningDialog
    abstract fun btnClick(vararg clicks: ((v: IWarningDialog) -> Unit)?): IWarningDialog
    abstract fun positiveBtnPosition(btnPosition: Int): IWarningDialog
    abstract fun setCustomContent(v:View): IWarningDialog

}