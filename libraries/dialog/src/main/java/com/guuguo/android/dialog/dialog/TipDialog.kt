package com.guuguo.android.dialog.dialog

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.guuguo.android.dialog.R
import com.guuguo.android.dialog.base.BaseDialog
import com.guuguo.android.dialog.utils.DialogSettings.*
import com.guuguo.android.dialog.utils.BlurView
import com.guuguo.android.drawable.CircularDrawable

class TipDialog : BaseDialog<TipDialog> {
    private constructor(mContext: Context) : super(mContext) {
        this.mContext = mContext
    }

    private constructor(mContext: Context, themeResId: Int) : super(mContext, themeResId) {
        this.mContext = mContext
    }

    override fun onCreateView(): View {
        val view = layoutInflater.inflate(R.layout.dialog_tip, null)
        widthRatio(0f)
        dimEnabled(false)
        return view
    }

    override fun setUiBeforShow() {
        val bkgResId: Int
        val blur_front_color: Int
        when (tip_theme) {
            THEME_LIGHT -> {
                bkgResId = R.drawable.rect_light
                blur_front_color = Color.argb(100, 255, 255, 255)
            }
            else -> {
                bkgResId = R.drawable.rect_dark
                blur_front_color = Color.argb(200, 0, 0, 0)
            }
        }
        boxInfo = mOnCreateView.findViewById<View>(R.id.box_info) as RelativeLayout
        image = mOnCreateView.findViewById<View>(R.id.image) as ImageView
        txtInfo = mOnCreateView.findViewById<View>(R.id.txt_info) as TextView

        if (useBlur) {
            blur = BlurView(context, null)
            val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            blur!!.setOverlayColor(blur_front_color)
            boxInfo!!.addView(blur, 0, params)
        } else {
            boxInfo!!.setBackgroundResource(bkgResId)
        }

        when (this.mStateStyle) {
            STATE_STYLE.warning -> if (tip_theme == THEME_LIGHT) {
                image!!.setImageResource(R.mipmap.img_warning_dark)
            } else {
                image!!.setImageResource(R.mipmap.img_warning)
            }
            STATE_STYLE.error -> if (tip_theme == THEME_LIGHT) {
                image!!.setImageResource(R.mipmap.img_error_dark)
            } else {
                image!!.setImageResource(R.mipmap.img_error)
            }
            STATE_STYLE.success -> if (tip_theme == THEME_LIGHT) {
                image!!.setImageResource(R.mipmap.img_finish_dark)
            } else {
                image!!.setImageResource(R.mipmap.img_finish)
            }
            in arrayOf(STATE_STYLE.loading, STATE_STYLE.customDrawable) -> {
                image!!.setImageDrawable(customDrawable);
                customDrawable?.let {
                    if (it is CircularDrawable) it.apply {
                        when (tip_theme) {
                            THEME_DARK -> {
                               it.dark()
                            }
                            else -> {
                                it.light()
                            }
                        }
                    }
                    if (it is Animatable)
                        it.start();

                }
            }
            STATE_STYLE.customBitmap -> image!!.setImageBitmap(customBitmap)
        }

        if (!tip!!.isEmpty()) {
            boxInfo!!.visibility = View.VISIBLE
            txtInfo!!.text = tip
        } else {
            boxInfo!!.visibility = View.GONE
        }
        if (tip_text_size > 0) {
            txtInfo!!.setTextSize(TypedValue.COMPLEX_UNIT_DIP, tip_text_size.toFloat())
        }
    }


    private var mStateStyle = 0

    object STATE_STYLE {
        const val customDrawable = -2
        const val customBitmap = -1
        const val loading = 0
        const val success = 1
        const val error = 2
        const val info = 3
        const val warning = 4
        const val noIcon = 5
    }

    private var alertDialog: AlertDialog? = null
    private var isCanCancel = false
    private var tip: String? = null
    private var delayTime = 0

    private var blur: BlurView? = null
    private var boxInfo: RelativeLayout? = null
    private var image: ImageView? = null
    private var txtInfo: TextView? = null

    fun tip(tip: String) = this.also { it.tip = tip }
    fun stateStyle(stateStyle: Int) = this.also { it.mStateStyle = stateStyle }
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    fun setCanCancel(canCancel: Boolean): TipDialog {
        isCanCancel = canCancel
        if (alertDialog != null) alertDialog!!.setCancelable(canCancel)
        return this
    }

    var customDrawable: Drawable? = null
    var customBitmap: Bitmap? = null

    companion object {

        var tipDialog: TipDialog? = null
        fun show(context: Context, tip: String, stateStyle: Int = STATE_STYLE.loading, customDrawable: Drawable? = null, customBitmap: Bitmap? = null): TipDialog {
            synchronized(TipDialog::class.java)
            {
                val style =
                        when (tip_theme) {
                            THEME_LIGHT -> R.style.lightMode
                            else -> R.style.darkMode
                        }
                return TipDialog(context, style)
                        .tip(tip)
                        .stateStyle(stateStyle)
                        .also {
                            it.customBitmap = customBitmap
                            it.customDrawable = if (customDrawable == null) CircularDrawable() else customDrawable
                            tipDialog = it
                            it.show()
                        }
            }
        }
    }

}//Fast Function
