package com.guuguo.android.dialog.dialog

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout

import com.guuguo.android.dialog.R
import com.guuguo.android.dialog.dialog.base.IWarningDialog
import com.guuguo.android.dialog.utils.DialogHelper

/**
 * Created by guodeqing on 6/23/16.
 */
class NewEditAlertDialog(var mContext: Context) {

    private var mContentLayout: FrameLayout? = null
    var dialog = DialogHelper.getWarningDialog(mContext)


    fun show() = dialog.show()
    fun title(title: String): IWarningDialog = dialog.title(title)
    fun message(message: String): IWarningDialog = dialog.message(message)
    fun btnNum(btnNum: Int): IWarningDialog = dialog.btnNum(btnNum)
    fun btnText(vararg text: String): IWarningDialog = dialog.btnText(*text)
    fun btnClick(vararg clicks: ((v: IWarningDialog) -> Unit)?): IWarningDialog = dialog.btnClick(*clicks)
    fun positiveBtnPosition(btnPosition: Int): IWarningDialog = dialog.positiveBtnPosition(btnPosition)

    var editTextView: EditText? = null
        private set
    private var mEditText: String? = null
    private var mInputType = -1
    private var mHint = ""

    init {
        val view = createCustomContent()
        dialog.setCustomContent(view)
        initCustomContent()
    }

    var editText: String?
        get() = if (editTextView != null) {
            editTextView!!.text.toString()
        } else null
        set(editText) {
            this.mEditText = editText
        }

    fun paddingVertical(padding: Int) = this.also {
        mContentLayout?.setPadding(dp2px(20f), padding, dp2px(20f), padding + dp2px(8f))
    }

    fun createCustomContent(): View {
        mContentLayout = FrameLayout(mContext)
        mContentLayout!!.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        /** EditText  */
        editTextView = EditText(mContext)
        editTextView!!.background = getDrawable(R.drawable.bg_edittext)
        editTextView!!.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, dp2px(40f)).apply { gravity = Gravity.CENTER }
        editTextView!!.setPadding(dp2px(10f), 0, dp2px(10f), 0)
        editTextView!!.gravity = Gravity.CENTER_VERTICAL
        mContentLayout!!.addView(editTextView)

        return mContentLayout!!
    }

    fun initCustomContent() {
        /** content  */

        mContentLayout!!.setPadding(dp2px(20f), 0, dp2px(20f), dp2px(8f))

        /**edit text  */
        if (!TextUtils.isEmpty(mEditText)) {
            editTextView!!.setText(mEditText)
        }
        if (!mHint.isEmpty()) {
            editTextView!!.hint = mHint
        }
        if (mInputType != -1) {
            editTextView!!.inputType = mInputType
        }
    }

    /** dp to px  */
    protected fun dp2px(dp: Float): Int {
        val scale = mContext.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    fun setInputType(inputType: Int): NewEditAlertDialog {
        this.mInputType = inputType
        return this
    }

    fun setHint(hint: String): NewEditAlertDialog {
        this.mHint = hint
        return this
    }

    fun setError(error: String) {
        if (editTextView != null) {
            editTextView!!.error = error
        }
    }

    fun getDrawable(id: Int): Drawable? {
        return if (Build.VERSION.SDK_INT >= 21) {
            mContext.getDrawable(id)
        } else {
            mContext.resources.getDrawable(id)
        }
    }
}


