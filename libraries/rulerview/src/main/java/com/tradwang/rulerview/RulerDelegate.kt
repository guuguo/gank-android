package com.tradwang.rulerview

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewConfiguration

/**
 * 自定义属性的代理类
 * @version 0.0.1
 * @author TradWang
 * @date 2019-4-25
 */
class RulerDelegate {

    private var mDefaultLineColor = Color.parseColor("#000000")

    var mModel = RuleModel.HORIZONTAL_BOTTOM

    var mMinVelocity = 0

    var mUnit = 1f

    var mLineSpaceWidth = 5

    var mCircularRadius: Int = 0

    var mMaxValue = 500f
    var mMinValue = 0f
    var mDefaultValue = (mMaxValue - mMinValue) / 2f

    var mMaxLineHeight = 10
    var mMiddleLineHeight = 8
    var mMinLineHeight = 5
    var mSelectedLineHeight = 12

    private var mMaxLineWidth = 3
    private var mMiddleLineWidth = 2
    private var mMinLineWidth = 1
    private var mSelectedLineWidth = 3
    private var mBaseLineWidth = 0

    var mTextMargin = 4
    var mBaseLineMargin = 0

    var mNeedDrawBaseLine = false

    private var mMaxLineColor = mDefaultLineColor
    private var mMiddleLineColor = mDefaultLineColor
    private var mMinLineColor = mDefaultLineColor
    private var mSelectedLineColor = mDefaultLineColor
    private var mBaseLineColor = mDefaultLineColor

    val mMaxLinePaint by lazy { getPaint(mMaxLineColor, mMaxLineWidth.toFloat()) }
    val mMiddleLinePaint by lazy { getPaint(mMiddleLineColor, mMiddleLineWidth.toFloat()) }
    val mMinLinePaint by lazy { getPaint(mMinLineColor, mMinLineWidth.toFloat()) }
    val mSelectedLinePaint by lazy { getPaint(mSelectedLineColor, mSelectedLineWidth.toFloat()) }
    val mBaseLinePaint by lazy { getPaint(mBaseLineColor, mBaseLineWidth.toFloat()).also { it.style = Paint.Style.STROKE } }

    var mMaxTextSize = 18

    var mMaxTextColor = mDefaultLineColor
    var mSelectedTextColor = mDefaultLineColor

    val mMaxTextPaint by lazy { getTextPaint(mMaxTextColor, mMaxTextSize.toFloat()) }

    fun setup(context: Context, attributeSet: AttributeSet?) {

        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.RulerView)

        mMaxLineHeight = typedArray.getDimensionPixelSize(R.styleable.RulerView_maxLineHeight, dipToPx(context, mMaxLineHeight))
        mMiddleLineHeight = typedArray.getDimensionPixelSize(R.styleable.RulerView_middleLineHeight, dipToPx(context, mMiddleLineHeight))
        mMinLineHeight = typedArray.getDimensionPixelSize(R.styleable.RulerView_minLineHeight, dipToPx(context, mMinLineHeight))
        mSelectedLineHeight = typedArray.getDimensionPixelSize(R.styleable.RulerView_selectedLineHeight, dipToPx(context, mSelectedLineHeight))

        mMaxLineWidth = typedArray.getDimensionPixelSize(R.styleable.RulerView_maxLineWidth, dipToPx(context, mMaxLineWidth))
        mMiddleLineWidth = typedArray.getDimensionPixelSize(R.styleable.RulerView_middleLineWidth, dipToPx(context, mMiddleLineWidth))
        mMinLineWidth = typedArray.getDimensionPixelSize(R.styleable.RulerView_minLineWidth, dipToPx(context, mMinLineWidth))
        mSelectedLineWidth = typedArray.getDimensionPixelSize(R.styleable.RulerView_selectedLineWidth, dipToPx(context, mSelectedLineWidth))

        mMaxLineColor = typedArray.getColor(R.styleable.RulerView_maxLineColor, mMaxLineColor)
        mMiddleLineColor = typedArray.getColor(R.styleable.RulerView_middleLineColor, mMiddleLineColor)
        mMinLineColor = typedArray.getColor(R.styleable.RulerView_minLineColor, mMinLineColor)
        mSelectedLineColor = typedArray.getColor(R.styleable.RulerView_selectedLineColor, mSelectedLineColor)

        mMaxTextSize = typedArray.getDimensionPixelSize(R.styleable.RulerView_maxTextSize, sp2px(context, mMaxTextSize))

        mMaxTextColor = typedArray.getDimensionPixelSize(R.styleable.RulerView_maxTextColor, mMaxTextColor)
        mSelectedTextColor = typedArray.getDimensionPixelSize(R.styleable.RulerView_selectedTextColor, mSelectedTextColor)

        mMaxValue = typedArray.getFloat(R.styleable.RulerView_maxValue, mMaxValue)
        mMinValue = typedArray.getFloat(R.styleable.RulerView_minValue, mMinValue)

        mDefaultValue = typedArray.getFloat(R.styleable.RulerView_defaultValue, mDefaultValue)

        mUnit = typedArray.getFloat(R.styleable.RulerView_unit, mUnit)

        mLineSpaceWidth = typedArray.getDimensionPixelSize(R.styleable.RulerView_lineSpaceWidth, dipToPx(context, mLineSpaceWidth))

        mTextMargin = typedArray.getDimensionPixelSize(R.styleable.RulerView_textMargin, dipToPx(context, mTextMargin))

        mCircularRadius = typedArray.getDimensionPixelSize(R.styleable.RulerView_circularRadius, dipToPx(context, mCircularRadius))

        mBaseLineWidth = typedArray.getDimensionPixelSize(R.styleable.RulerView_baseLineWidth, dipToPx(context, mBaseLineWidth))
        mNeedDrawBaseLine = mBaseLineWidth != 0
        mBaseLineMargin = typedArray.getDimensionPixelSize(R.styleable.RulerView_baseLineMargin, dipToPx(context, mBaseLineMargin))
        mBaseLineColor = typedArray.getColor(R.styleable.RulerView_baseLineColor, mBaseLineColor)

        mModel = getModel(typedArray.getInt(R.styleable.RulerView_model, mModel.value))

        mMinVelocity = ViewConfiguration.get(context).scaledMinimumFlingVelocity

        typedArray.recycle()
    }

    private fun getModel(value: Int): RuleModel {
        return when (value) {
            0 -> RuleModel.HORIZONTAL_TOP
            1 -> RuleModel.HORIZONTAL_BOTTOM
            2 -> RuleModel.HORIZONTAL_CIRCULAR_TOP
            3 -> RuleModel.HORIZONTAL_CIRCULAR_BOTTOM
            4 -> RuleModel.VERTICAL_LEFT
            5 -> RuleModel.VERTICAL_RIGHT
            6 -> RuleModel.VERTICAL_CIRCULAR_LEFT
            7 -> RuleModel.VERTICAL_CIRCULAR_RIGHT
            else -> RuleModel.HORIZONTAL_BOTTOM
        }
    }

    private fun dipToPx(context: Context, dpValue: Int): Int {
        if (dpValue == 0) return 0
        return (dpValue * (context.resources.displayMetrics.density) + 0.5f).toInt()
    }

    private fun sp2px(context: Context, spVal: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal.toFloat(), context.resources.displayMetrics).toInt()
    }

    private fun getTextPaint(color: Int, size: Float): Paint {
        return Paint().also { it.isAntiAlias = true;it.textAlign = Paint.Align.CENTER;it.color = color; it.textSize = size }
    }

    private fun getPaint(color: Int, width: Float): Paint {
        return Paint().also { it.isAntiAlias = true;it.color = color; it.strokeWidth = width }
    }

}