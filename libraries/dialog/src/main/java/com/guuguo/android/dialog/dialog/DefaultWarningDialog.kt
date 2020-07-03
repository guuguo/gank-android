package com.guuguo.android.dialog.dialog

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.guuguo.android.dialog.R
import com.guuguo.android.dialog.dialog.base.IWarningDialog
import com.guuguo.android.dialog.utils.CornerUtils


class DefaultWarningDialog : IWarningDialog {
    constructor(mContext: Context) : super(mContext) {
        this.mContext = mContext
    }

    override fun onCreateView(): View {
        val view = layoutInflater.inflate(R.layout.dialog_default_warning, null)
        widthRatio(0f)
        heightRatio(0f)
        dimEnabled(true)

        val lp = window!!.attributes
        lp.dimAmount = 0.5f
        window!!.attributes = lp

        view.findViewById<View>(R.id.ll_content).background = CornerUtils.cornerDrawable(Color.WHITE, radius)
        return view
    }

    val radius = dp2px(4f).toFloat()
    var mCustomContentView: View? = null
    override fun setCustomContent(v: View) = this.also {
        mCustomContentView = v
    }

    override fun setUiBeforShow() {

        val btn1 = mOnCreateView.findViewById<TextView>(R.id.btn_1)
        val btn2 = mOnCreateView.findViewById<TextView>(R.id.btn_2)
        val tv_message = mOnCreateView.findViewById<TextView>(R.id.tv_message)
        val tv_title = mOnCreateView.findViewById<TextView>(R.id.tv_title)
        val divider_title = mOnCreateView.findViewById<View>(R.id.divider_title)
        val customContainer = mOnCreateView.findViewById<FrameLayout>(R.id.content_container)
        mCustomContentView?.let {
            customContainer.addView(mCustomContentView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        }

        btn1.setOnClickListener {
            btnClick1?.invoke(this)
        }
        btn2.setOnClickListener {
            btnClick2?.invoke(this)
        }
        btn1.setTextColor(getColor(R.color.colorPrimary))
        btn2.setTextColor(getColor(R.color.colorPrimary))
        if (btnPosition == 1) {
            btn1.setTextColor(Color.WHITE)
        } else if (btnPosition == 2) {
            btn2.setTextColor(Color.WHITE)
        }
        btn1.text = btnText1
        btn2.text = btnText2
        tv_message.text = message
        tv_title.text = title

        (if (title.isEmpty()) View.GONE else View.VISIBLE).also {
            tv_title.visibility = if (it == View.GONE) View.INVISIBLE else it
            divider_title.visibility = it
        }
        if (message.isNullOrEmpty())
            tv_message.visibility = View.GONE
        else tv_message.visibility = View.VISIBLE

        val colorWhite = Color.WHITE
        val colorWhitePress = getColor(R.color.black20)

        val colorPrimary = getColor(R.color.dialogColorPrimary)
        val colorPrimaryPress = getColor(R.color.dialogColorPrimaryDark)

        if (btnNum == 0) {
            if (!btnText2.isEmpty())
                btnNum = 2
            else if (!btnText1.isEmpty())
                btnNum = 1
        }

        if (btnNum == 1) {
            btn1.visibility = View.VISIBLE
            btn2.visibility = View.GONE
            btn1.background = CornerUtils.btnSelector(radius, colorWhite, colorWhitePress, -1)
        } else if (btnNum == 2) {
            btn1.visibility = View.VISIBLE
            btn2.visibility = View.VISIBLE
            btn1.background = CornerUtils.btnSelector(radius,
                    if (btnPosition == 1) colorPrimary else colorWhite, if (btnPosition == 1) colorPrimaryPress else colorWhitePress, 0)
            btn2.background = CornerUtils.btnSelector(radius,
                    if (btnPosition == 2) colorPrimary else colorWhite, if (btnPosition == 2) colorPrimaryPress else colorWhitePress, 1)
        }
    }


    fun getColor(id: Int): Int {
        return if (Build.VERSION.SDK_INT >= 23) {
            mContext.getColor(id)
        } else {
            mContext.resources.getColor(id)
        }
    }

    init {

    }

    var title = ""
        private set
    var message = ""
        private set
    var btnPosition = 2
        private set
    var btnNum = 0
        private set
    var btnText1 = ""
        private set
    var btnText2 = ""
        private set
    var btnClick1: ((v: DefaultWarningDialog) -> Unit)? = null
    var btnClick2: ((v: DefaultWarningDialog) -> Unit)? = null

    override fun title(title: String) = this.also { it.title = title }
    override fun message(message: String) = this.also { it.message = message }
    override fun btnNum(btnNum: Int) = this.also { it.btnNum = (if (btnNum < 0) 0 else if (btnNum > 2) 2 else btnNum) }
    override fun btnText(vararg text: String) = this.also { text.getOrNull(0)?.apply { it.btnText1 = (this) }; text.getOrNull(1)?.apply { it.btnText2 = (this) } }
    override fun btnClick(vararg clicks: ((v: IWarningDialog) -> Unit)?) = this.also { clicks.getOrNull(0)?.apply { it.btnClick1 = this }; clicks.getOrNull(1)?.apply { it.btnClick2 = this } }
    override fun positiveBtnPosition(btnPosition: Int) = this.also { it.btnPosition = (btnPosition) }

}//Fast Function
