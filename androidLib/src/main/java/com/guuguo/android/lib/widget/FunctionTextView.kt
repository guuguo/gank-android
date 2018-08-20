package com.guuguo.android.lib.widget

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.guuguo.android.R
import com.guuguo.android.lib.extension.dpToPx
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.lib.widget.roundview.RoundLinearLayout

/**
 * mimi 创造于 2017-07-06.
 * 项目 order
 */

class FunctionTextView : RoundLinearLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.FunctionTextView)
        initAttr(context, attributes)
        attributes.recycle()
        initView()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        imageView?.isEnabled = enabled
        textView?.isEnabled = enabled
    }

    private fun initAttr(context: Context, attributes: TypedArray) {
        text = attributes.getString(R.styleable.FunctionTextView_android_text).safe("")
        textSize = attributes.getDimension(R.styleable.FunctionTextView_android_textSize, 12.dpToPx().toFloat())
        textColor = attributes.getColor(R.styleable.FunctionTextView_android_textColor, Color.BLACK)
        textStyle = attributes.getInt(R.styleable.FunctionTextView_android_textStyle, Typeface.NORMAL)
        drawableTint = attributes.getColor(R.styleable.FunctionTextView_ftv_drawableTint, 0)
        drawable = attributes.getDrawable(R.styleable.FunctionTextView_ftv_drawableSrc)

        drawableAlign = attributes.getInt(R.styleable.FunctionTextView_ftv_drawableAlign, 0)
        drawableWidth = attributes.getDimension(R.styleable.FunctionTextView_ftv_drawableWidth, -2f)
        drawableHeight = attributes.getDimension(R.styleable.FunctionTextView_ftv_drawableHeight, -2f)
        drawablePadding = attributes.getDimension(R.styleable.FunctionTextView_android_drawablePadding, 0f)

        gravity = attributes.getInt(R.styleable.FunctionTextView_android_gravity, Gravity.CENTER)
    }

    var text: String = ""
        set(value) {
            field = value
            if (!value.isEmpty()) {
                textView?.apply {
                    text = value
                    textView?.setTextColor(textColor)
                    visibility = View.VISIBLE
                }
            } else {
                textView?.visibility = View.GONE
            }
        }

    var textSize: Float = 0f
        set(value) {
            field = value
            textView?.textSize = value
        }
    var textColor: Int = Color.BLACK
        set(value) {
            field = value
            textView?.setTextColor(value)
        }
    var textStyle: Int = Typeface.NORMAL
        set(value) {
            field = value
            textView?.typeface = Typeface.defaultFromStyle(value);//加粗
        }
    var drawableTint: Int = 0
        set(value) {
            field = value
            drawable?.let {
                val wrapped = DrawableCompat.wrap(it)
                DrawableCompat.setTint(wrapped, value)
                imageView?.setImageDrawable(wrapped)
            }
        }
    /**
     * left "0" />
     * top"1" />
     * right"2" />
     * bottom"3" />
     */
    var drawableAlign: Int? = null
    var drawable: Drawable? = null
        set(value) {
            field = value
            drawable?.let {
                imageView?.visibility = View.VISIBLE
                if (drawableTint != 0) {
                    val wrapped = DrawableCompat.wrap(it)
                    DrawableCompat.setTint(wrapped, drawableTint)
                    imageView?.setImageDrawable(wrapped)
                } else {
                    imageView?.setImageDrawable(it)
                }
            }
            if (drawable == null) {
                imageView?.visibility = View.GONE
            }
        }
    var drawableWidth: Float = -2f
    var drawableHeight: Float = -2f
    var drawablePadding: Float = 0f

    val ALIGN_LEFT = 0
    val ALIGN_TOP = 1
    val ALIGN_RIGHT = 2
    val ALIGN_BOTTOM = 3
    var imageView: AppCompatImageView? = null
    var textView: AppCompatTextView? = null
    private fun initView() {
        textView = AppCompatTextView(context)
        imageView = AppCompatImageView(context)

        requestViews()
    }

    fun requestViews() {
        removeAllViews()
        when (drawableAlign) {
            ALIGN_LEFT -> orientation = HORIZONTAL
            ALIGN_TOP -> orientation = VERTICAL
            ALIGN_RIGHT -> orientation = HORIZONTAL
            ALIGN_BOTTOM -> orientation = VERTICAL
        }

        //param
        val imageViewParams = LinearLayout.LayoutParams(drawableWidth.toInt(), drawableHeight.toInt())

        val textViewParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
            when (drawableAlign) {
                ALIGN_LEFT -> marginStart = drawablePadding.toInt()
                ALIGN_TOP -> topMargin = drawablePadding.toInt()
                ALIGN_RIGHT -> marginEnd = drawablePadding.toInt()
                ALIGN_BOTTOM -> bottomMargin = drawablePadding.toInt()
            }
        }
        when (drawableAlign) {
            ALIGN_LEFT, ALIGN_TOP -> {
                addView(imageView, imageViewParams)
                addView(textView, textViewParams)
            }
            ALIGN_RIGHT, ALIGN_BOTTOM -> {
                addView(textView, textViewParams)
                addView(imageView, imageViewParams)
            }
        }

        //image text
        drawable?.also {
            imageView?.visibility = View.VISIBLE
            if (drawableTint != 0) {
                val wrapped = DrawableCompat.wrap(it)
                DrawableCompat.setTint(wrapped, drawableTint)
                imageView?.setImageDrawable(wrapped)
            } else {
                imageView?.setImageDrawable(drawable)
            }
        } ?: { imageView?.visibility = View.GONE }.invoke()

        if (text.isEmpty()) {
            textView?.visibility = View.GONE
        } else {
            textView?.visibility = View.VISIBLE
            textView?.text = text
        }
        textView?.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        textView?.setTextColor(textColor)
        textView?.typeface = Typeface.defaultFromStyle(textStyle);//加粗
    }

    private fun createColorStateList(normal: Int, pressed: Int, focused: Int, unable: Int): ColorStateList {
        val colors = intArrayOf(pressed, focused, normal, focused, unable, normal)
        val states = arrayOfNulls<IntArray>(6)
        states[0] = intArrayOf(android.R.attr.state_pressed, android.R.attr.state_enabled)
        states[1] = intArrayOf(android.R.attr.state_enabled, android.R.attr.state_focused)
        states[2] = intArrayOf(android.R.attr.state_enabled)
        states[3] = intArrayOf(android.R.attr.state_focused)
        states[4] = intArrayOf(android.R.attr.state_window_focused)
        states[5] = intArrayOf()
        return ColorStateList(states, colors)
    }
}