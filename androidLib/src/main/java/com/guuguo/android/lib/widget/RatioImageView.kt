package com.guuguo.android.lib.widget

import android.content.Context
import android.content.res.TypedArray
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import com.guuguo.android.R

/**
 * 一个能保持比例的 ImageView
 * @author guuguo
 */
class RatioImageView : AppCompatImageView {

    private var originalWidth: Int = 0
    private var originalHeight: Int = 0

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val attributes = context.theme.obtainStyledAttributes(attrs, R.styleable.RatioImageView, defStyleAttr, 0)
        initAttr(context, attributes)
        attributes.recycle()
    }

    private fun initAttr(context: Context, attributes: TypedArray) {
        setOriginalSize(attributes.getDimensionPixelSize(R.styleable.RatioImageView_riv_origin_width, 0),
                attributes.getDimensionPixelSize(R.styleable.RatioImageView_riv_origin_height, 0))
    }

    fun setOriginalSize(originalWidth: Int, originalHeight: Int) {
        this.originalWidth = originalWidth
        this.originalHeight = originalHeight
        if (width > 0) {
            layoutParams.height = this.originalHeight / this.originalWidth * width
            requestLayout()
 
         }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (originalWidth > 0 && originalHeight > 0) {
            val ratio = originalWidth.toFloat() / originalHeight.toFloat()

            val width = MeasureSpec.getSize(widthMeasureSpec)
            var height = MeasureSpec.getSize(heightMeasureSpec)

            if (width > 0) {
                height = (width.toFloat() / ratio).toInt()
            }

            layoutParams.height = height
            setMeasuredDimension(width, height)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

}
