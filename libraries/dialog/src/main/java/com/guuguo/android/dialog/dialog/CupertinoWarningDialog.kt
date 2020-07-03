package com.guuguo.android.dialog.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.guuguo.android.dialog.R
import com.guuguo.android.dialog.R.id.tv_title
import com.guuguo.android.dialog.dialog.base.IWarningDialog
import com.guuguo.android.dialog.utils.CornerUtils
import com.guuguo.android.dialog.utils.DialogSettings


class CupertinoWarningDialog : IWarningDialog {


    constructor(mContext: Context) : super(mContext) {
        this.mContext = mContext
    }

    override fun onCreateView(): View {
        val view = layoutInflater.inflate(R.layout.dialog_cupertino_warning, null)
        widthRatio(0f)
        heightRatio(0f)
        dimEnabled(true)

        val lp = window!!.attributes
        lp.dimAmount = 0.5f
        window!!.attributes = lp

        view.findViewById<View>(R.id.ll_content).background = CornerUtils.cornerDrawable(Color.WHITE, radius)
        return view
    }

    val radius = dp2px(15f).toFloat()
    var mCustomContentView: View? = null
    override fun setCustomContent(v: View) = this.also {
        mCustomContentView = v
    }

    override fun setUiBeforShow() {
        val bkgResId: Int
        val blur_front_color: Int
        val btn_text_color: Int
        val btn_text_color_Press: Int
        val bgColor: Int
        val dividerColor: Int
        when (DialogSettings.tip_theme) {
            DialogSettings.THEME_LIGHT -> {
                bkgResId = R.drawable.rect_dark
                blur_front_color = Color.argb(255, 255, 255, 255)
                bgColor = getColor(R.color.progress_dlg_bkg)
                btn_text_color = Color.WHITE
                btn_text_color_Press = getColor(R.color.ios_dialog_split_dark)
                dividerColor=getColor(R.color.ios_dialog_split_dark)

            }
            else -> {
                bkgResId = R.drawable.rect_light
                blur_front_color = Color.argb(255, 0, 0, 0)
                bgColor = getColor(R.color.ios_dlg_bkg)
                btn_text_color = getColor(R.color.ios_dlg_text)
                btn_text_color_Press = getColor(R.color.ios_dialog_button_press)
                dividerColor=getColor(R.color.ios_dialog_split_light)
            }
        }

        val content = mOnCreateView.findViewById<LinearLayout>(R.id.ll_content)
        val btn1 = mOnCreateView.findViewById<TextView>(R.id.btn_1)
        val btn2 = mOnCreateView.findViewById<TextView>(R.id.btn_2)
        val tv_message = mOnCreateView.findViewById<TextView>(R.id.tv_message)
        val tv_title = mOnCreateView.findViewById<TextView>(R.id.tv_title)
        val customContainer = mOnCreateView.findViewById<FrameLayout>(R.id.content_container)
        val ll_functions = mOnCreateView.findViewById<LinearLayout>(R.id.ll_functions)
        val v_divider = mOnCreateView.findViewById<View>(R.id.v_divider)

        mCustomContentView?.let {
            customContainer.addView(mCustomContentView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        }

        content.setBackgroundResource(bkgResId)
        tv_message.setTextColor(blur_front_color)
        tv_title.setTextColor(blur_front_color)
        btn1.setTextColor(btn_text_color)
        btn2.setTextColor(btn_text_color)

        ll_functions.dividerDrawable=ColorDrawable().apply { color= dividerColor}
        v_divider.setBackgroundColor(dividerColor)

        btn1.setOnClickListener {
            btnClick1?.invoke(this)
        }
        btn2.setOnClickListener {
            btnClick2?.invoke(this)
        }
        btn1.text = btnText1
        btn2.text = btnText2
        tv_title.text = title
        tv_message.text = message

        if (title.isNullOrEmpty())
            tv_title.visibility = View.GONE
        else tv_title.visibility = View.VISIBLE
        if (message.isNullOrEmpty())
            tv_message.visibility = View.GONE
        else tv_message.visibility = View.VISIBLE

        if (btnNum == 0) {
            if (!btnText2.isEmpty())
                btnNum = 2
            else if (!btnText1.isEmpty())
                btnNum = 1
        }
        btn1.visibility = View.GONE
        btn2.visibility = View.GONE
        if (btnNum == 1) {
            btn1.visibility = View.VISIBLE
            btn1.background = CornerUtils.btnSelector(radius, Color.TRANSPARENT, btn_text_color_Press, -1)
        } else if (btnNum == 2) {
            btn1.visibility = View.VISIBLE
            btn2.visibility = View.VISIBLE
            btn1.background = CornerUtils.btnSelector(radius, Color.TRANSPARENT, btn_text_color_Press, 0)
            btn2.background = CornerUtils.btnSelector(radius, Color.TRANSPARENT, btn_text_color_Press, 1)
        }

        btn1.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        btn2.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        if (btnPosition == 1)
            btn1.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
        else if (btnPosition == 2)
            btn2.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
    }

    fun getColor(id: Int): Int {
        return if (Build.VERSION.SDK_INT >= 23) {
            mContext.getColor(id)
        } else {
            mContext.resources.getColor(id)
        }
    }

    fun getDrawable(id: Int): Drawable? {
        return if (Build.VERSION.SDK_INT >= 23) {
            mContext.getDrawable(id)
        } else {
            mContext.resources.getDrawable(id)
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
    var btnClick1: ((v: CupertinoWarningDialog) -> Unit)? = null
    var btnClick2: ((v: CupertinoWarningDialog) -> Unit)? = null

    override fun title(title: String) = this.also { it.title = title }
    override fun message(message: String) = this.also { it.message = message }
    override fun btnNum(btnNum: Int) = this.also { it.btnNum = (if (btnNum < 0) 0 else if (btnNum > 2) 2 else btnNum) }
    override fun btnText(vararg text: String) = this.also { text.getOrNull(0)?.apply { it.btnText1 = (this) }; text.getOrNull(1)?.apply { it.btnText2 = (this) } }
    override fun btnClick(vararg clicks: ((v: IWarningDialog) -> Unit)?) = this.also { clicks.getOrNull(0)?.apply { it.btnClick1 = this }; clicks.getOrNull(1)?.apply { it.btnClick2 = this } }
    override fun positiveBtnPosition(btnPosition: Int) = this.also { it.btnPosition = (btnPosition) }

}//Fast Function
