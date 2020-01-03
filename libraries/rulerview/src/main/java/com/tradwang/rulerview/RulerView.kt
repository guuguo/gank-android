package com.tradwang.rulerview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.widget.Scroller

/**
 * 刻度尺
 * 支持[RuleModel]种刻度类型 注：垂直和水平方向的直线刻度未实现基线，垂直和水平方向的弧形刻度尺未实现选中的刻度样式
 * [RulerDecorator]类处理全部的绘制及滑动
 * [RulerDelegate]类处理自定义属性的管理
 * [RulerView]类处理触摸事件
 * @version 0.0.1
 * @author TradWang
 * @date 2019-4-25
 */
class RulerView : View {

    private val defaultWidth = 200
    private val defaultHeight = 200

    //按下的X
    private var downX = 0f
    //按下的Y
    private var downY = 0f

    var viewWidth = 0
        private set
    var viewHeight = 0
        private set
    //是否可用
    var enable = true
    //移动的X距离
    private var moveX = 0f
    //移动的Y距离
    private var moveY = 0f

    private val mScroller: Scroller by lazy { Scroller(context) }

    private val mRulerDelegate by lazy { RulerDelegate() }

    private val mVelocityTracker: VelocityTracker by lazy { VelocityTracker.obtain() }

    private lateinit var mRulerDecorator: RulerDecorator

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {

        mRulerDelegate.setup(context, attrs)
        mRulerDecorator = RulerDecorator(this, mRulerDelegate, mScroller, mVelocityTracker)

        initValue(mRulerDelegate.mMaxValue, mRulerDelegate.mMinValue, mRulerDelegate.mDefaultValue, mRulerDelegate.mUnit)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w > 0 && h > 0) {
            viewWidth = w - (paddingLeft + paddingRight)
            viewHeight = h - (paddingTop + paddingBottom)
            mRulerDecorator.onSizeChanged()
        }
    }

    override fun setEnabled(enabled: Boolean) {
        enable = enabled
    }

    override fun isEnabled(): Boolean {
        return enable
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(getMeasureLength(widthMeasureSpec, true), getMeasureLength(heightMeasureSpec, false))
    }

    private fun getMeasureLength(length: Int, isWidth: Boolean): Int {

        val mode = MeasureSpec.getMode(length)
        val measureSize = MeasureSpec.getSize(length)

        var size: Int
        val padding: Int

        when {
            isWidth -> {
                padding = paddingLeft + paddingRight

                if (mode == MeasureSpec.EXACTLY) {
                    size = measureSize
                } else {
                    size = padding + defaultWidth
                    if (mode == MeasureSpec.AT_MOST) {
                        size = Math.min(size, measureSize)
                    }
                }
            }
            else -> {
                padding = paddingTop + paddingBottom
                if (mode == MeasureSpec.EXACTLY) {
                    size = measureSize
                } else {
                    size = padding + defaultHeight
                    if (mode == MeasureSpec.AT_MOST) {
                        size = Math.min(size, measureSize)
                    }
                }
            }
        }
        return size
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {

        if (!enable) return true

        val touchX = event.x
        val touchY = event.y

        mVelocityTracker.addMovement(event)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mScroller.forceFinished(true)

                downX = event.x
                downY = event.y

                moveX = 0f
                moveY = 0f
            }
            MotionEvent.ACTION_MOVE -> {
                moveX = downX - touchX
                moveY = downY - touchY
                onScroll(moveX, moveY)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                downX = 0f
                downY = 0f
                onFlingStop(moveX, moveY)
                countVelocityTracker()
                //后面就交给惯性滑动了 这里直接不消费事件
                return false
            }
        }
        downX = touchX
        downY = touchY
        return true
    }

    //计算要惯性滑动的距离
    private fun countVelocityTracker() {
        mRulerDecorator.handleFling()
    }

    override fun computeScroll() {
        super.computeScroll()


        if (mScroller.computeScrollOffset()) {
            //X方向惯性滚动停止
            if (mScroller.currX != mScroller.finalX) {
                moveX = downX - mScroller.currX
                onScroll(moveX, moveY)
                downX = mScroller.currX.toFloat()
            } else {
                onFlingStop(moveX, moveY)
            }
            //Y方向惯性滚动停止
            if (mScroller.currY != mScroller.finalY) {
                moveY = downY - mScroller.currY
                onScroll(moveX, moveY)
                downY = mScroller.currY.toFloat()
            } else {
                onFlingStop(moveX, moveY)
            }
        }
    }

    private fun onFlingStop(moveX: Float, moveY: Float) {
        mRulerDecorator.handleFlingStop(moveX, moveY)
    }

    override fun onDraw(canvas: Canvas) {
        mRulerDecorator.handleDraw(canvas)
        super.onDraw(canvas)
    }

    fun setValue(maxValue: Float, minValue: Float, defaultValue: Float, unit: Float) {
        mRulerDecorator.setValue(maxValue, minValue, defaultValue, unit)
    }

    private fun initValue(maxValue: Float, minValue: Float, defaultValue: Float, unit: Float) {
        mRulerDecorator.initValue(maxValue, minValue, defaultValue, unit)
    }

    fun resetTouch() {
        moveX = 0f
        moveY = 0f
    }

    fun setSelectListener(listener: ((value: Float) -> Unit)?) {
        mRulerDecorator.setSelectListener(listener)
    }

    private fun onScroll(x: Float, y: Float) {
        mRulerDecorator.handleScroll(x, y)
    }
}
