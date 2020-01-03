package com.guuguo.android.lib.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.DrawableCompat
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import com.guuguo.android.R
import com.guuguo.android.lib.extension.dpToPx
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.lib.ktx.margin
import com.guuguo.android.lib.widget.roundview.RoundLinearLayout

/**
 * mimi 创造于 2017-07-06.
 * 项目 order
 */

class FunctionTextView : RoundLinearLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, android.R.attr.textViewStyle)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, R.style.Widget_AppCompat_Button)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.FunctionTextView, defStyleAttr, defStyleRes)
        initAttr(context, attributes)
        attributes.recycle()
        initView(attrs, defStyleAttr)
    }

    val ALIGN_LEFT = 0
    val ALIGN_TOP = 1
    val ALIGN_RIGHT = 2
    val ALIGN_BOTTOM = 3
    var imageView: AppCompatImageView? = null
    var textView: AppCompatTextView? = null
    private fun initView(attrs: AttributeSet?, defStyleAttr: Int) {
        textView = AppCompatTextView(context, null, defStyleAttr).apply {
            id = R.id.function_tv;gravity = Gravity.CENTER
        }
        imageView = AppCompatImageView(context).apply { id = R.id.function_img }

        requestViews()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        imageView?.isEnabled = enabled
        textView?.isEnabled = enabled
    }

    private fun initAttr(context: Context, attributes: TypedArray) {
        text = attributes.getString(R.styleable.FunctionTextView_android_text).safe("")
        textStyle = attributes.getInt(R.styleable.FunctionTextView_android_textStyle, Typeface.NORMAL)
        textSize = attributes.getDimension(R.styleable.FunctionTextView_android_textSize, 12.dpToPx().toFloat())
        textColor = attributes.getColor(R.styleable.FunctionTextView_android_textColor, 0)
        textMinWidth = attributes.getDimension(R.styleable.FunctionTextView_ftv_textMinWidth, 0f).toInt()
        textMaxWidth = attributes.getDimension(R.styleable.FunctionTextView_ftv_textMaxWidth, Float.MAX_VALUE).toInt()
        textGravity = attributes.getInt(R.styleable.FunctionTextView_ftv_textGravity, Gravity.CENTER)

        drawableTintDefaultTextColor = attributes.getBoolean(R.styleable.FunctionTextView_ftv_drawableTintDefaultTextColor, false)
        drawableTint = attributes.getColor(R.styleable.FunctionTextView_ftv_drawableTint, 0)
        drawable = attributes.getDrawable(R.styleable.FunctionTextView_ftv_drawableSrc)

        drawableAlign = attributes.getInt(R.styleable.FunctionTextView_ftv_drawableAlign, 0)
        drawableWidth = attributes.getDimension(R.styleable.FunctionTextView_ftv_drawableWidth, -2f)
        drawableHeight = attributes.getDimension(R.styleable.FunctionTextView_ftv_drawableHeight, -2f)
        drawablePadding = attributes.getDimension(R.styleable.FunctionTextView_android_drawablePadding, 0f)

        gravity = attributes.getInt(R.styleable.FunctionTextView_android_gravity, Gravity.CENTER)
    }

    var text: String? = ""
        set(value) {
            field = value
            textView?.apply {
                text = value
                isVisible = !value.isNullOrEmpty()
            }
            requestLayout()
        }

    var textSize: Float = 0f
        set(value) {
            field = value
            textView?.textSize = value
        }
    var textColor: Int = 0
        set(value) {
            field = value
            textView?.setTextColor(value)
            setDrawableWithTint()
        }
    var textMinWidth: Int = 0
        set(value) {
            field = value
            textView?.minWidth = value
        }
    var textMaxWidth: Int = 0
        set(value) {
            field = value
            textView?.maxWidth = value
        }
    var textGravity: Int = 0
        set(value) {
            field = value
            textView?.gravity = value
        }

    var textStyle: Int = Typeface.NORMAL
        set(value) {
            field = value
            textView?.typeface = Typeface.defaultFromStyle(value);//加粗
        }
    var drawableTint: Int = 0
        set(value) {
            field = value
            setDrawableWithTint()
        }

    var drawableTintDefaultTextColor = false
    /**
     * left "0" />
     * top"1" />
     * right"2" />
     * bottom"3" />
     */
    var drawableAlign: Int? = null
        set(value) {
            field = value
            if (textView == null || imageView == null)
                return
            when (field) {
                ALIGN_LEFT -> orientation = HORIZONTAL
                ALIGN_TOP -> orientation = VERTICAL
                ALIGN_RIGHT -> orientation = HORIZONTAL
                ALIGN_BOTTOM -> orientation = VERTICAL
            }
            when (field) {
                ALIGN_LEFT, ALIGN_TOP -> {
                    removeView(textView)
                    addView(textView)
                }
                ALIGN_RIGHT, ALIGN_BOTTOM -> {
                    removeView(imageView)
                    addView(imageView)
                }
            }
            textView?.updateLayoutParams<LayoutParams> {
                updateLayoutParams(drawableAlign)
            }
        }

    var drawable: Drawable? = null
        set(value) {
            field = value
            setDrawableWithTint()
        }

    private fun setDrawableWithTint() {
        drawable?.let {
            imageView?.visibility = View.VISIBLE
            if (drawableTint != 0) {
                val wrapped = DrawableCompat.wrap(drawable!!)
                DrawableCompat.setTint(wrapped, drawableTint)
                wrapped.mutate()
                imageView?.setImageDrawable(wrapped)
            } else if (drawableTint == 0 && drawableTintDefaultTextColor) {
                val color = textView?.currentTextColor.safe()
                val wrapped = DrawableCompat.wrap(it)
                wrapped.mutate()
                DrawableCompat.setTint(wrapped, color)
                imageView?.setImageDrawable(wrapped)
            } else {
                imageView?.setImageDrawable(it)
            }
            textView?.updateLayoutParams<LayoutParams> {
                updateLayoutParams(drawableAlign)
            }
        } ?: {
            imageView?.visibility = View.GONE;textView?.updateLayoutParams<LayoutParams> {
            setMargins(0, 0, 0, 0)
        }
        }.invoke()
    }

    private fun LayoutParams.updateLayoutParams( drawableAlign:Int?) {
        when (drawableAlign) {
            ALIGN_LEFT -> this.setMargins(drawablePadding.toInt(), 0, 0, 0)
            ALIGN_TOP -> this.setMargins(0, drawablePadding.toInt(), 0, 0)
            ALIGN_RIGHT -> this.setMargins(0, 0, drawablePadding.toInt(), 0)
            ALIGN_BOTTOM -> this.setMargins(0, 0, 0, drawablePadding.toInt())
        }
    }

    var drawableWidth: Float = -2f
    var drawableHeight: Float = -2f
    var drawablePadding: Float = 0f


    fun requestViews() {
        removeAllViews()
        when (drawableAlign) {
            ALIGN_LEFT -> orientation = HORIZONTAL
            ALIGN_TOP -> orientation = VERTICAL
            ALIGN_RIGHT -> orientation = HORIZONTAL
            ALIGN_BOTTOM -> orientation = VERTICAL
        }

        //param
        val imageViewParams = LayoutParams(drawableWidth.toInt(), drawableHeight.toInt())

        val textViewParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
            drawable?.let {
                updateLayoutParams(drawableAlign)
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
        setDrawableWithTint()

        if (text.isNullOrEmpty()) {
            textView?.visibility = View.GONE
        } else {
            textView?.visibility = View.VISIBLE
            textView?.text = text
        }
        textView?.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        textView?.minWidth = textMinWidth
        textView?.maxWidth = textMaxWidth
        textView?.gravity = textGravity
        if (textColor != 0)
            textView?.setTextColor(textColor)
        textView?.typeface = Typeface.defaultFromStyle(textStyle)//加粗
    }
}