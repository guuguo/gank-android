package com.tradwang.rulerview

import android.graphics.*
import android.view.VelocityTracker
import android.widget.Scroller

/**
 * 处理滑动和绘制逻辑
 * @version 0.0.1
 * @author TradWang
 * @date 2019-4-25
 */
internal class RulerDecorator(private val view: RulerView, private val delegate: RulerDelegate, private val scroller: Scroller, private val velocityTracker: VelocityTracker) {

    /*需要的成员变量*/
    //总共的刻度数
    private var totalLine = 0
    //总偏移量
    private var totalOffset = 0f
    //当前偏移量
    private var offset = 0f //最终绘制动画都通过此值的变化而变化
    //最终选中的值
    private var selectorValue = 0f
    //回调
    private var listener: ((value: Float) -> Unit)? = null

    /*绘制相关的成员变量*/
    //进度条中间的位置
    private var middle = 0
    //圆的半径
    private var radius = 0.0
    //圆心到X轴的距离(用于计算圆心位置)
    private var sqrtX = 0.0
    //圆心到Y轴的距离(用于计算圆心位置)
    private var sqrtY = 0.0
    //圆心坐标X
    private var circleCenterX = 0.0
    //圆心坐标Y
    private var circleCenterY = 0.0
    //圆形的RectF
    private var circleRectF: RectF = RectF()
    //每个间隔的弧度 这里弧长约等于线的间隔
    private var spaceArc = 0.0
    //需要画的总弧度
    private var arc = 0.0
    //需要画的总弧长度
    private var arcLine = 0.0
    //圆弧相对与刻度尺长度需要偏移的长度
    private var d = 0.0
    //文字的宽度
    private var textWidth = 0
    //文字的高度
    private var textHeight = 0
    /*END*/

    /**设置值*/
    fun setValue(maxValue: Float, minValue: Float, defaultValue: Float, unit: Float) {
        delegate.mMaxValue = maxValue
        delegate.mMinValue = minValue
        delegate.mDefaultValue = defaultValue
        delegate.mUnit = unit

        totalLine = ((delegate.mMaxValue - delegate.mMinValue) / delegate.mUnit).toInt() + 1
        initOffset()
        selectorValue = getSelectorValue(offset)
        listener?.invoke(selectorValue)
        view.postInvalidate()
    }

    /**初始化值*/
    fun initValue(maxValue: Float, minValue: Float, defaultValue: Float, unit: Float) {
        delegate.mMaxValue = maxValue
        delegate.mMinValue = minValue
        delegate.mDefaultValue = defaultValue
        delegate.mUnit = unit

        totalLine = ((delegate.mMaxValue - delegate.mMinValue) / delegate.mUnit).toInt() + 1
        initOffset()
        selectorValue = getSelectorValue(offset)
    }

    /**设置监听*/
    fun setSelectListener(listener: ((value: Float) -> Unit)?) {
        this.listener = listener
    }

    fun onSizeChanged() {
        preTreatDraw()
    }

    /**处理惯性滑动*/
    fun handleFling() {
        when (delegate.mModel) {
            RuleModel.HORIZONTAL_TOP, RuleModel.HORIZONTAL_BOTTOM, RuleModel.HORIZONTAL_CIRCULAR_TOP, RuleModel.HORIZONTAL_CIRCULAR_BOTTOM -> flingHorizontal()
            RuleModel.VERTICAL_LEFT, RuleModel.VERTICAL_RIGHT, RuleModel.VERTICAL_CIRCULAR_LEFT, RuleModel.VERTICAL_CIRCULAR_RIGHT -> flingVertical()
        }
    }

    /**处理滑动结束后*/
    fun handleFlingStop(x: Float, y: Float) {
        when (delegate.mModel) {
            RuleModel.HORIZONTAL_TOP, RuleModel.HORIZONTAL_BOTTOM, RuleModel.HORIZONTAL_CIRCULAR_TOP, RuleModel.HORIZONTAL_CIRCULAR_BOTTOM -> flingStopHorizontal(x)
            RuleModel.VERTICAL_LEFT, RuleModel.VERTICAL_RIGHT, RuleModel.VERTICAL_CIRCULAR_LEFT, RuleModel.VERTICAL_CIRCULAR_RIGHT -> flingStopVertical(y)
        }
    }

    /**处理滚动逻辑*/
    fun handleScroll(x: Float, y: Float) {
        when (delegate.mModel) {
            RuleModel.HORIZONTAL_TOP, RuleModel.HORIZONTAL_BOTTOM, RuleModel.HORIZONTAL_CIRCULAR_TOP, RuleModel.HORIZONTAL_CIRCULAR_BOTTOM -> scrollHorizontal(x)
            RuleModel.VERTICAL_LEFT, RuleModel.VERTICAL_RIGHT, RuleModel.VERTICAL_CIRCULAR_LEFT, RuleModel.VERTICAL_CIRCULAR_RIGHT -> scrollVertical(y)
        }
    }

    /**处理绘制逻辑*/
    fun handleDraw(canvas: Canvas) {
        when (delegate.mModel) {
            RuleModel.HORIZONTAL_TOP -> {
                drawHorizontal(canvas, true)
            }
            RuleModel.HORIZONTAL_BOTTOM -> {
                drawHorizontal(canvas, false)
            }
            RuleModel.HORIZONTAL_CIRCULAR_TOP -> {
                drawCircleHorizontal(canvas, true)
            }
            RuleModel.HORIZONTAL_CIRCULAR_BOTTOM -> {
                drawCircleHorizontal(canvas, false)
            }
            RuleModel.VERTICAL_LEFT -> {
                drawVertical(canvas, true)
            }
            RuleModel.VERTICAL_RIGHT -> {
                drawVertical(canvas, false)
            }
            RuleModel.VERTICAL_CIRCULAR_LEFT -> {
                drawCircleVertical(canvas, true)
            }
            RuleModel.VERTICAL_CIRCULAR_RIGHT -> {
                drawCircleVertical(canvas, false)
            }
        }
    }

    /**处理滑动结束后*/
    private fun flingStopVertical(y: Float) {
        offset -= y
        when {
            offset >= 0f -> {
                view.resetTouch()
                offset = 0f
                scroller.forceFinished(true)
            }
            offset <= totalOffset -> {
                view.resetTouch()
                offset = totalOffset
                scroller.forceFinished(true)
            }
        }
        view.resetTouch()
        selectorValue = getSelectorValue(offset)
        offset = (selectorValue - delegate.mMaxValue) / delegate.mUnit * delegate.mLineSpaceWidth
        listener?.invoke(selectorValue)
        view.postInvalidate()
    }

    /**处理水平方向的惯性滑动*/
    private fun flingHorizontal() {
        velocityTracker.computeCurrentVelocity(500)  //初始化速率的单位
        val xVelocity = velocityTracker.xVelocity //当前的速度

        if (Math.abs(xVelocity) > delegate.mMinVelocity) {
            scroller.fling(0, 0, xVelocity.toInt(), 0, Int.MIN_VALUE, Int.MAX_VALUE, 0, 0)
        }
    }

    /**处理垂直方向的惯性滑动*/
    private fun flingVertical() {
        velocityTracker.computeCurrentVelocity(500)  //初始化速率的单位
        val yVelocity = velocityTracker.yVelocity //当前的速度

        if (Math.abs(yVelocity) > delegate.mMinVelocity) {
            scroller.fling(0, 0, 0, yVelocity.toInt(), 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE)
        }
    }

    /**处理滑动结束后*/
    private fun flingStopHorizontal(x: Float) {
        offset -= x
        when {
            offset >= 0f -> {
                view.resetTouch()
                offset = 0f
                scroller.forceFinished(true)
            }
            offset <= totalOffset -> {
                view.resetTouch()
                offset = totalOffset
                scroller.forceFinished(true)
            }
        }
        view.resetTouch()
        selectorValue = getSelectorValue(offset)
        offset = (delegate.mMinValue - selectorValue) / delegate.mUnit * delegate.mLineSpaceWidth
        listener?.invoke(selectorValue)
        view.postInvalidate()
    }

    /**处理水平滚动逻辑*/
    private fun scrollHorizontal(x: Float) {
        offset -= x
        when {
            offset >= 0f -> {
                offset = 0f
                view.resetTouch()
                scroller.forceFinished(true)
            }
            offset <= totalOffset -> {
                offset = totalOffset
                view.resetTouch()
                scroller.forceFinished(true)
            }
        }
        selectorValue = getSelectorValue(offset)
        listener?.invoke(selectorValue)

        view.postInvalidate()
    }

    /**处理垂直滚动逻辑*/
    private fun scrollVertical(y: Float) {
        offset -= y
        when {
            offset >= 0f -> {
                view.resetTouch()
                offset = 0f
                scroller.forceFinished(true)
            }
            offset <= totalOffset -> {
                view.resetTouch()
                offset = totalOffset
                scroller.forceFinished(true)
            }
        }
        selectorValue = getSelectorValue(offset)
        listener?.invoke(selectorValue)
        view.postInvalidate()
    }

    /**绘制水平刻度尺*/
    private fun drawHorizontal(canvas: Canvas, top: Boolean) {

        for (i in 0 until totalLine) {
            val pointX = middle + offset + i * delegate.mLineSpaceWidth
            if (pointX < 0 || pointX > view.viewWidth) {//超过范围不绘制
                continue
            }
            var drawLineStart: Int
            var drawLineEnd: Int
            var paint: Paint
            when {
                i % 10 == 0 -> {//最大
                    drawLineStart = if (top) 0 else view.viewHeight - delegate.mMaxLineHeight
                    drawLineEnd = if (top) delegate.mMaxLineHeight else view.viewHeight
                    paint = delegate.mMaxLinePaint

                    //绘制文字
                    val text = getDrawText(i)

                    if (top) {
                        canvas.drawText(text, pointX, drawLineEnd.toFloat() + textHeight + delegate.mTextMargin, delegate.mMaxTextPaint)
                    } else {
                        canvas.drawText(text, pointX, drawLineStart.toFloat() - delegate.mTextMargin, delegate.mMaxTextPaint)
                    }

                }
                i % 5 == 0 -> {//中间
                    drawLineStart = if (top) 0 else view.viewHeight - delegate.mMiddleLineHeight
                    drawLineEnd = if (top) delegate.mMiddleLineHeight else view.viewHeight
                    paint = delegate.mMiddleLinePaint
                }
                else -> {//最小
                    drawLineStart = if (top) 0 else view.viewHeight - delegate.mMinLineHeight
                    drawLineEnd = if (top) delegate.mMinLineHeight else view.viewHeight
                    paint = delegate.mMinLinePaint
                }
            }
            //绘制刻度线
            canvas.drawLine(pointX, drawLineStart.toFloat(), pointX, drawLineEnd.toFloat(), paint)

            //绘制中间的刻度线
            drawLineStart = if (top) 0 else view.viewHeight - delegate.mSelectedLineHeight
            drawLineEnd = if (top) delegate.mSelectedLineHeight else view.viewHeight

            canvas.drawLine(view.viewWidth / 2f, drawLineStart.toFloat(), view.viewWidth / 2f, drawLineEnd.toFloat(), delegate.mSelectedLinePaint)
        }
    }

    /**绘制水平弧形刻度尺*/
    private fun drawCircleHorizontal(canvas: Canvas, top: Boolean) {

        var inRadius: Double
        var paint: Paint
        var textInRadius: Double
        //当前占总量的百分比
        val needOffset = -spaceArc * (totalLine - 1) * (offset / totalOffset)
        //绘制最外层的圆弧
        val lineRadius: Double = radius - delegate.mBaseLineMargin

        if (delegate.mNeedDrawBaseLine) {
            if (top) {
                canvas.drawArc(circleRectF, 0f, 180f, false, delegate.mBaseLinePaint)
            } else {
                canvas.drawArc(circleRectF, -180f, 180f, false, delegate.mBaseLinePaint)
            }
        }
        for (i in 0 until totalLine) {

            val pointX = middle + offset + i * delegate.mLineSpaceWidth

            if (pointX < -d || pointX > view.viewWidth + d) {//超过范围不绘制
                continue
            }
            var swipeArc = spaceArc * i
            swipeArc += needOffset
            when {
                i % 10 == 0 -> {//最大
                    paint = delegate.mMaxLinePaint
                    inRadius = lineRadius - delegate.mMaxLineHeight
                }
                i % 5 == 0 -> {//中间
                    paint = delegate.mMiddleLinePaint
                    inRadius = lineRadius - delegate.mMiddleLineHeight
                }
                else -> {//最小
                    paint = delegate.mMinLinePaint
                    inRadius = lineRadius - delegate.mMinLineHeight
                }
            }

            val rectF = getCircleHorizontalRectF(circleCenterX, circleCenterY, swipeArc, lineRadius, inRadius, top)
            canvas.drawLine(rectF.left, rectF.top, rectF.right, rectF.bottom, paint)

            if (i % 10 == 0) {
                val text = getDrawText(i)

                textInRadius = inRadius - delegate.mTextMargin

                val textArc = textWidth / textInRadius
                val rectFText = getCircleHorizontalTextRectF(circleCenterX, circleCenterY, swipeArc, textArc, textInRadius, top)
                val path = Path()
                path.moveTo(rectFText.left, rectFText.top)
                path.lineTo(rectFText.right, rectFText.bottom)
                canvas.drawTextOnPath(text, path, 0f, textHeight / 2f, delegate.mMaxTextPaint)
            }
        }
    }

    /**获取水平文字RectF 水平文字根据文字长度计算*/
    private fun getCircleHorizontalTextRectF(circleCenterX: Double, circleCenterY: Double, swipeArc: Double, textArc: Double, inRadius: Double, top: Boolean): RectF {
        val d = textArc / 2
        return if (top) {
            val startX = circleCenterX + Math.sin(Math.PI - swipeArc + d) * (inRadius)
            val startY = circleCenterY - Math.cos(Math.PI - swipeArc + d) * (inRadius)

            val endX = circleCenterX + Math.sin(Math.PI - swipeArc - d) * (inRadius)
            val endY = circleCenterY - Math.cos(Math.PI - swipeArc - d) * (inRadius)
            RectF(startX.toFloat(), startY.toFloat(), endX.toFloat(), endY.toFloat())
        } else {
            val startX = circleCenterX + Math.sin(swipeArc - d) * (inRadius)
            val startY = circleCenterY - Math.cos(swipeArc - d) * (inRadius)

            val endX = circleCenterX + Math.sin(swipeArc + d) * (inRadius)
            val endY = circleCenterY - Math.cos(swipeArc + d) * (inRadius)
            RectF(startX.toFloat(), startY.toFloat(), endX.toFloat(), endY.toFloat())
        }
    }

    /**获取水平绘制刻度的RectF*/
    private fun getCircleHorizontalRectF(circleCenterX: Double, circleCenterY: Double, swipeArc: Double, outRadius: Double, inRadius: Double, top: Boolean): RectF {
        return if (top) {
            val startX = circleCenterX + Math.sin(Math.PI - swipeArc) * (inRadius)
            val startY = circleCenterY - Math.cos(Math.PI - swipeArc) * (inRadius)

            val endX = circleCenterX + Math.sin(Math.PI - swipeArc) * (outRadius)
            val endY = circleCenterY - Math.cos(Math.PI - swipeArc) * (outRadius)
            RectF(startX.toFloat(), startY.toFloat(), endX.toFloat(), endY.toFloat())
        } else {
            val startX = circleCenterX + Math.sin(swipeArc) * (inRadius)
            val startY = circleCenterY - Math.cos(swipeArc) * (inRadius)

            val endX = circleCenterX + Math.sin(swipeArc) * (outRadius)
            val endY = circleCenterY - Math.cos(swipeArc) * (outRadius)
            RectF(startX.toFloat(), startY.toFloat(), endX.toFloat(), endY.toFloat())
        }
    }

    /**绘制垂直刻度尺*/
    private fun drawVertical(canvas: Canvas, left: Boolean) {

        for (i in 0 until totalLine) {

            val pointY = middle + offset + i * delegate.mLineSpaceWidth

            if (pointY < 0 || pointY > view.viewHeight) { //超出View的布局不绘制
                continue
            }
            var drawLineStart: Int
            var drawLineEnd: Int
            var paint: Paint

            when {
                i % 10 == 0 -> {//最大
                    drawLineStart = if (left) 0 else view.viewWidth - delegate.mMaxLineHeight
                    drawLineEnd = if (left) delegate.mMaxLineHeight else view.viewWidth
                    paint = delegate.mMaxLinePaint

                    //绘制文字
                    val text = getDrawText(i)

                    if (left) {
                        canvas.drawText(text, drawLineEnd + textWidth / 2f + delegate.mTextMargin, pointY + textHeight / 2f, delegate.mMaxTextPaint)
                    } else {
                        canvas.drawText(text, drawLineStart - textWidth / 2f - delegate.mTextMargin, pointY + textHeight / 2f, delegate.mMaxTextPaint)
                    }
                }
                i % 5 == 0 -> {//中间
                    drawLineStart = if (left) 0 else view.viewWidth - delegate.mMiddleLineHeight
                    drawLineEnd = if (left) delegate.mMiddleLineHeight else view.viewWidth
                    paint = delegate.mMiddleLinePaint
                }
                else -> {//最小
                    drawLineStart = if (left) 0 else view.viewWidth - delegate.mMinLineHeight
                    drawLineEnd = if (left) delegate.mMinLineHeight else view.viewWidth
                    paint = delegate.mMinLinePaint
                }
            }
            //绘制刻度线
            canvas.drawLine(drawLineStart.toFloat(), pointY, drawLineEnd.toFloat(), pointY, paint)

            //绘制中间的刻度线
            drawLineStart = if (left) 0 else view.viewWidth - delegate.mSelectedLineHeight
            drawLineEnd = if (left) delegate.mSelectedLineHeight else view.viewWidth

            canvas.drawLine(drawLineStart.toFloat(), view.viewHeight / 2f, drawLineEnd.toFloat(), view.viewHeight / 2f, delegate.mSelectedLinePaint)
        }
    }

    /**绘制垂直弧形刻度尺*/
    private fun drawCircleVertical(canvas: Canvas, left: Boolean) {
        var inRadius: Double
        var paint: Paint

        var textInRadius: Double
        var textOutRadius: Double

        //当前占总量的百分比
        val needOffset = -spaceArc * (totalLine - 1) * (offset / totalOffset)

        //绘制最外层的圆弧
        val lineRadius: Double = radius - delegate.mBaseLineMargin

        if (delegate.mNeedDrawBaseLine) {
            if (left) {
                canvas.drawArc(circleRectF, 270f, 180f, false, delegate.mBaseLinePaint)
            } else {
                canvas.drawArc(circleRectF, 90f, 270f, false, delegate.mBaseLinePaint)
            }
        }
        for (i in 0 until totalLine) {
            val pointY = middle + offset + i * delegate.mLineSpaceWidth
            if (pointY < -d || pointY > view.viewHeight + d) {//超过范围不绘制
                continue
            }
            var swipeArc = spaceArc * i
            swipeArc += needOffset
            when {
                i % 10 == 0 -> {//最大
                    paint = delegate.mMaxLinePaint
                    inRadius = lineRadius - delegate.mMaxLineHeight
                }
                i % 5 == 0 -> {//中间
                    paint = delegate.mMiddleLinePaint
                    inRadius = lineRadius - delegate.mMiddleLineHeight
                }
                else -> {//最小
                    paint = delegate.mMinLinePaint
                    inRadius = lineRadius - delegate.mMinLineHeight
                }
            }

            val rectF = getCircleVerticalRectF(circleCenterX, circleCenterY, swipeArc, lineRadius, inRadius, left)
            canvas.drawLine(rectF.left, rectF.top, rectF.right, rectF.bottom, paint)

            if (i % 10 == 0) {
                val text = getDrawText(i)

                textInRadius = inRadius - textWidth - delegate.mTextMargin
                textOutRadius = lineRadius - delegate.mMaxLineHeight
                //文字方向等于刻度方向
                val rectFText = getCircleVerticalRectF(circleCenterX, circleCenterY, swipeArc, textOutRadius, textInRadius, left)
                val path = Path()
                //左边文字方向正常
                if (left) {
                    path.moveTo(rectFText.left, rectFText.top)
                    path.lineTo(rectFText.right, rectFText.bottom)
                } else {
                    //右边文字反转
                    path.moveTo(rectFText.right, rectFText.bottom)
                    path.lineTo(rectFText.left, rectFText.top)
                }
                canvas.drawTextOnPath(text, path, 0f, textHeight / 2f, delegate.mMaxTextPaint)
            }
        }
    }

    /**获取垂直绘制刻度的RectF*/
    private fun getCircleVerticalRectF(circleCenterX: Double, circleCenterY: Double, swipeArc: Double, outRadius: Double, inRadius: Double, left: Boolean): RectF {
        return if (left) {
            val startX = circleCenterX + Math.sin(swipeArc + Math.PI / 2) * (inRadius)
            val startY = circleCenterY - Math.cos(swipeArc + Math.PI / 2) * (inRadius)

            val endX = circleCenterX + Math.sin(swipeArc + Math.PI / 2) * (outRadius)
            val endY = circleCenterY - Math.cos(swipeArc + Math.PI / 2) * (outRadius)
            RectF(startX.toFloat(), startY.toFloat(), endX.toFloat(), endY.toFloat())
        } else {
            val startX = circleCenterX + Math.sin(3 * Math.PI / 2 - swipeArc) * (inRadius)
            val startY = circleCenterY - Math.cos(3 * Math.PI / 2 - swipeArc) * (inRadius)

            val endX = circleCenterX + Math.sin(3 * Math.PI / 2 - swipeArc) * (outRadius)
            val endY = circleCenterY - Math.cos(3 * Math.PI / 2 - swipeArc) * (outRadius)
            RectF(startX.toFloat(), startY.toFloat(), endX.toFloat(), endY.toFloat())
        }
    }

    /**获取垂直方向圆弧的半径*/
    private fun getVerticalRadius(defaultRadius: Int): Double {
        return if (defaultRadius == 0) {
            (view.viewHeight * view.viewHeight + 4f * view.viewWidth * view.viewWidth) / (view.viewWidth * 8f).toDouble()
        } else {
            defaultRadius.toDouble()
        }
    }

    /**获取水平方向圆弧的半径*/
    private fun getHorizontalRadius(defaultRadius: Int): Double {
        return if (defaultRadius == 0) {
            (view.viewWidth * view.viewWidth + 4f * view.viewHeight * view.viewHeight) / (view.viewHeight * 8f).toDouble()
        } else {
            defaultRadius.toDouble()
        }
    }

    /**获取需要根据格数计算对应的文字*/
    private fun getDrawText(i: Int): String {
        return when (delegate.mModel) {
            RuleModel.HORIZONTAL_TOP, RuleModel.HORIZONTAL_BOTTOM, RuleModel.HORIZONTAL_CIRCULAR_TOP, RuleModel.HORIZONTAL_CIRCULAR_BOTTOM -> {
                (delegate.mMinValue + i * delegate.mUnit).toInt().toString()
            }
            RuleModel.VERTICAL_LEFT, RuleModel.VERTICAL_RIGHT, RuleModel.VERTICAL_CIRCULAR_LEFT, RuleModel.VERTICAL_CIRCULAR_RIGHT -> {
                (delegate.mMaxValue - i * delegate.mUnit).toInt().toString()
            }
        }
    }

    /**获取文字的宽度*/
    private fun getTextWidth(text: String, paint: Paint): Int {
        val rect = Rect()
        paint.getTextBounds(text, 0, text.length, rect)
        return rect.width()
    }

    /**获取文字的高度*/
    private fun getTextHeight(text: String, paint: Paint): Int {
        val rect = Rect()
        paint.getTextBounds(text, 0, text.length, rect)
        return rect.height()
    }

    /**根据偏移量获取选中的值*/
    private fun getSelectorValue(offset: Float): Float {
        return when (delegate.mModel) {
            RuleModel.HORIZONTAL_TOP, RuleModel.HORIZONTAL_BOTTOM, RuleModel.HORIZONTAL_CIRCULAR_TOP, RuleModel.HORIZONTAL_CIRCULAR_BOTTOM -> {
                delegate.mMinValue + Math.round(Math.abs(offset) * 1.0f / delegate.mLineSpaceWidth) * delegate.mUnit
            }
            RuleModel.VERTICAL_LEFT, RuleModel.VERTICAL_RIGHT, RuleModel.VERTICAL_CIRCULAR_LEFT, RuleModel.VERTICAL_CIRCULAR_RIGHT -> {
                delegate.mMaxValue - Math.round(Math.abs(offset) * 1.0f / delegate.mLineSpaceWidth) * delegate.mUnit
            }
        }
    }

    /**根据方向计算偏移量 垂直方向需要最大值在上*/
    private fun initOffset() {
        when (delegate.mModel) {
            RuleModel.HORIZONTAL_TOP, RuleModel.HORIZONTAL_BOTTOM, RuleModel.HORIZONTAL_CIRCULAR_TOP, RuleModel.HORIZONTAL_CIRCULAR_BOTTOM -> {
                totalOffset = (delegate.mMinValue - delegate.mMaxValue) / delegate.mUnit * delegate.mLineSpaceWidth
                offset = (delegate.mMinValue - delegate.mDefaultValue) / delegate.mUnit * delegate.mLineSpaceWidth
            }
            RuleModel.VERTICAL_LEFT, RuleModel.VERTICAL_RIGHT, RuleModel.VERTICAL_CIRCULAR_LEFT, RuleModel.VERTICAL_CIRCULAR_RIGHT -> {
                totalOffset = (delegate.mMinValue - delegate.mMaxValue) / delegate.mUnit * delegate.mLineSpaceWidth
                offset = (delegate.mDefaultValue - delegate.mMaxValue) / delegate.mUnit * delegate.mLineSpaceWidth
            }
        }
    }

    /**初始化绘制中的一些常量*/
    private fun preTreatDraw() {
        when (delegate.mModel) {
            RuleModel.HORIZONTAL_TOP -> {
                middle = view.viewWidth / 2
                textWidth = getTextWidth(delegate.mMaxValue.toInt().toString(), delegate.mMaxTextPaint)
                textHeight = getTextHeight(delegate.mMaxValue.toInt().toString(), delegate.mMaxTextPaint)
            }
            RuleModel.HORIZONTAL_BOTTOM -> {
                middle = view.viewWidth / 2
                textWidth = getTextWidth(delegate.mMaxValue.toInt().toString(), delegate.mMaxTextPaint)
                textHeight = getTextHeight(delegate.mMaxValue.toInt().toString(), delegate.mMaxTextPaint)
            }
            RuleModel.HORIZONTAL_CIRCULAR_TOP -> {
                middle = view.viewWidth / 2
                textWidth = getTextWidth(delegate.mMaxValue.toInt().toString(), delegate.mMaxTextPaint)
                textHeight = getTextHeight(delegate.mMaxValue.toInt().toString(), delegate.mMaxTextPaint)

                //根据勾股定理计算半径
                radius = getHorizontalRadius(delegate.mCircularRadius)
                //每个间隔的弧度 这里弧长约等于线的间隔
                spaceArc = delegate.mLineSpaceWidth / radius
                //需要画的总弧度
                arc = Math.asin(view.viewWidth / (radius * 2)) * 2
                //需要画的总弧长度
                arcLine = arc * radius
                //左右需要偏移的长度
                d = (arcLine - view.viewWidth) / 2
                //圆心到X轴的距离
                sqrtX = Math.sqrt(radius * radius - (view.viewWidth * view.viewWidth / 4).toDouble())
                //圆心到Y轴的距离
                sqrtY = Math.sqrt(radius * radius - (view.viewHeight * view.viewHeight / 4).toDouble())
                //圆心需要偏移到距离
                val centerNeedOffset = view.viewHeight - (radius - sqrtX)
                //圆的中心点Y
                circleCenterY = -sqrtX + centerNeedOffset - delegate.mBaseLinePaint.strokeWidth / 2
                //圆的中心点X
                circleCenterX = view.viewWidth / 2.toDouble()
                //rectF
                circleRectF = RectF((circleCenterX - radius).toFloat(),
                        (circleCenterY - radius).toFloat(),
                        (circleCenterX + radius).toFloat(),
                        (circleCenterY + radius).toFloat())
            }
            RuleModel.HORIZONTAL_CIRCULAR_BOTTOM -> {
                middle = view.viewWidth / 2
                textWidth = getTextWidth(delegate.mMaxValue.toInt().toString(), delegate.mMaxTextPaint)
                textHeight = getTextHeight(delegate.mMaxValue.toInt().toString(), delegate.mMaxTextPaint)

                //根据勾股定理计算半径
                radius = getHorizontalRadius(delegate.mCircularRadius)
                //每个间隔的弧度 这里弧长约等于线的间隔
                spaceArc = delegate.mLineSpaceWidth / radius
                //需要画的总弧度
                arc = Math.asin(view.viewWidth / (radius * 2)) * 2
                //需要画的总弧长度
                arcLine = arc * radius
                //左右需要偏移的长度
                d = (arcLine - view.viewWidth) / 2
                //圆心到X轴的距离
                sqrtX = Math.sqrt(radius * radius - (view.viewWidth * view.viewWidth / 4).toDouble())
                //圆心到Y轴的距离
                sqrtY = Math.sqrt(radius * radius - (view.viewHeight * view.viewHeight / 4).toDouble())

                //圆心需要偏移到距离
                val centerNeedOffset = view.viewHeight - (radius - sqrtX)

                //圆的中心点Y
                circleCenterY = sqrtX + view.viewHeight - centerNeedOffset + delegate.mBaseLinePaint.strokeWidth / 2
                //圆的中心点X
                circleCenterX = view.viewWidth / 2.toDouble()
                //rectF
                circleRectF = RectF((circleCenterX - radius).toFloat(),
                        (circleCenterY - radius).toFloat(),
                        (circleCenterX + radius).toFloat(),
                        (circleCenterY + radius).toFloat())
            }
            RuleModel.VERTICAL_LEFT -> {
                middle = view.viewHeight / 2
                textWidth = getTextWidth(delegate.mMaxValue.toInt().toString(), delegate.mMaxTextPaint)
                textHeight = getTextHeight(delegate.mMaxValue.toInt().toString(), delegate.mMaxTextPaint)
            }
            RuleModel.VERTICAL_RIGHT -> {
                middle = view.viewHeight / 2
                textWidth = getTextWidth(delegate.mMaxValue.toInt().toString(), delegate.mMaxTextPaint)
                textHeight = getTextHeight(delegate.mMaxValue.toInt().toString(), delegate.mMaxTextPaint)
            }
            RuleModel.VERTICAL_CIRCULAR_LEFT -> {
                middle = view.viewHeight / 2
                textWidth = getTextWidth(delegate.mMaxValue.toInt().toString(), delegate.mMaxTextPaint)
                textHeight = getTextHeight(delegate.mMaxValue.toInt().toString(), delegate.mMaxTextPaint)

                //根据勾股定理计算半径
                radius = getVerticalRadius(delegate.mCircularRadius)
                //每个间隔的弧度 这里弧长约等于线的间隔
                spaceArc = delegate.mLineSpaceWidth / radius
                //需要画的总弧长度
                arc = Math.asin(view.viewHeight / (radius * 2)) * 2 * radius
                //左右需要偏移的长度
                d = (arc - view.viewHeight) / 2
                //圆心到X轴到距离
                sqrtX = Math.sqrt(radius * radius - (view.viewWidth * view.viewWidth / 4).toDouble())
                //圆心到Y轴到距离
                sqrtY = Math.sqrt(radius * radius - (view.viewHeight * view.viewHeight / 4).toDouble())

                val centerNeedOffset = view.viewWidth - (radius - sqrtY)

                //圆的中心点X
                circleCenterX = -sqrtY + centerNeedOffset - delegate.mBaseLinePaint.strokeWidth / 2
                //圆的中心点Y
                circleCenterY = view.viewHeight / 2.toDouble()
                //rectF
                circleRectF = RectF((circleCenterX - radius).toFloat(),
                        (circleCenterY - radius).toFloat(),
                        (circleCenterX + radius).toFloat(),
                        (circleCenterY + radius).toFloat())
            }
            RuleModel.VERTICAL_CIRCULAR_RIGHT -> {
                middle = view.viewHeight / 2
                textWidth = getTextWidth(delegate.mMaxValue.toInt().toString(), delegate.mMaxTextPaint)
                textHeight = getTextHeight(delegate.mMaxValue.toInt().toString(), delegate.mMaxTextPaint)

                //根据勾股定理计算半径
                radius = getVerticalRadius(delegate.mCircularRadius)
                //每个间隔的弧度 这里弧长约等于线的间隔
                spaceArc = delegate.mLineSpaceWidth / radius
                //需要画的总弧长度
                arc = Math.asin(view.viewHeight / (radius * 2)) * 2 * radius
                //左右需要偏移的长度
                d = (arc - view.viewHeight) / 2
                //圆心到X轴到距离
                sqrtX = Math.sqrt(radius * radius - (view.viewWidth * view.viewWidth / 4).toDouble())
                //圆心到Y轴到距离
                sqrtY = Math.sqrt(radius * radius - (view.viewHeight * view.viewHeight / 4).toDouble())

                val centerNeedOffset = view.viewWidth - (radius - sqrtY)

                //圆的中心点X
                circleCenterX = sqrtY + view.viewWidth - centerNeedOffset + delegate.mBaseLinePaint.strokeWidth / 2
                //圆的中心点Y
                circleCenterY = view.viewHeight / 2.toDouble()
                //rectF
                circleRectF = RectF((circleCenterX - radius).toFloat(),
                        (circleCenterY - radius).toFloat(),
                        (circleCenterX + radius).toFloat(),
                        (circleCenterY + radius).toFloat())
            }
        }
    }
}