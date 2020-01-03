package com.guuguo.android.lib.widget.progress

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import androidx.annotation.IntRange
import android.util.AttributeSet
import android.view.View
import com.guuguo.android.R
import com.guuguo.android.lib.extension.dpToPx


/**
 * mimi 创造于 2017-07-20.
 * 项目 androidLib
 */

class ProgressRing : View {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val attributes = context.theme.obtainStyledAttributes(attrs, R.styleable.ProgressRing, defStyleAttr, 0)
        initByAttributes(attributes)
        attributes.recycle()
        initPaint()
    }


    private lateinit var mBgPaint: Paint
    private lateinit var mProgressPaint: Paint
    private var progressbarWidth: Int = 0
    var progress = 20
        set(@IntRange(from = 0, to = 100) value) {
            currentProgress = progress
            field = value
            invalidate()
        }

    private var startAngle = 0
    private var sweepAngle = 0
    private var bgEndColor = Color.GRAY
    private var bgStartColor = Color.GRAY
    private var bgMidColor = Color.GRAY
    private var progressStartColor = Color.YELLOW
    private var progressEndColor = Color.BLUE
    private var isAnimShow = true

    private var currentProgress = 0
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec = heightMeasureSpec
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val width = View.MeasureSpec.getSize(widthMeasureSpec) - paddingLeft - paddingRight
        var height = View.MeasureSpec.getSize(heightMeasureSpec) - paddingLeft - paddingRight

        if (widthMode == View.MeasureSpec.EXACTLY && heightMode != View.MeasureSpec.EXACTLY) {
            val minHeight = Math.min(-Math.sin(Math.toRadians(startAngle.toDouble())), -Math.sin(Math.toRadians((startAngle + sweepAngle).toDouble()))) * (width / 2)
            height = (width / 2 + progressbarWidth  - minHeight).toInt()
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height,
                    View.MeasureSpec.EXACTLY)
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    protected fun initByAttributes(attributes: TypedArray) {
        progressbarWidth = attributes.getDimensionPixelSize(R.styleable.ProgressRing_cr_progressbar_width, 5.dpToPx())
        progress = attributes.getInteger(R.styleable.ProgressRing_cr_progress, progress)
        bgStartColor = attributes.getColor(R.styleable.ProgressRing_cr_background_bar_start_color, Color.parseColor("#ffffff"))
        bgMidColor = attributes.getColor(R.styleable.ProgressRing_cr_background_bar_mid_color, Color.GRAY)
        bgEndColor = attributes.getColor(R.styleable.ProgressRing_cr_background_bar_end_color, Color.parseColor("#ffffff"))
        progressStartColor = attributes.getColor(R.styleable.ProgressRing_cr_progressbar_start_color, Color.YELLOW)
        progressEndColor = attributes.getColor(R.styleable.ProgressRing_cr_progressbar_end_color, Color.BLUE)
        startAngle = attributes.getInteger(R.styleable.ProgressRing_cr_start_angle, 150)
        sweepAngle = attributes.getInteger(R.styleable.ProgressRing_cr_sweep_angle, 240)
        isAnimShow = attributes.getBoolean(R.styleable.ProgressRing_cr_anim_show, true)
    }

    private fun initPaint() {
        mBgPaint = Paint()
        mBgPaint.strokeCap = Paint.Cap.ROUND
        mBgPaint.strokeWidth = progressbarWidth.toFloat()
        mBgPaint.isAntiAlias = true
        mBgPaint.style = Paint.Style.STROKE
        val mShader = SweepGradient(0f, 0f, intArrayOf(bgStartColor, bgMidColor, bgEndColor), floatArrayOf(0f, sweepAngle / 2f / 360, sweepAngle / 360f))
        mBgPaint.shader = mShader

        mProgressPaint = Paint()
        mProgressPaint.strokeCap = Paint.Cap.ROUND
        mProgressPaint.isAntiAlias = true
        mProgressPaint.strokeWidth = progressbarWidth.toFloat()
        mProgressPaint.style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!isAnimShow)
            currentProgress = progress
        canvas.save();//保存当前画布状态
        canvas.translate(width / 2f, width / 2f); //将坐标中心平移到要围绕的坐标点x,y
        canvas.rotate(startAngle.toFloat());//旋转角度

        drawBackground(canvas)
        drawProgress(canvas)

        canvas.restore();//恢复画图状态到保存前

        if (currentProgress < progress) {
            currentProgress++
            postInvalidate()
        } else if (currentProgress > progress) {
            currentProgress--
            postInvalidate()
        }
    }

    private fun drawBackground(canvas: Canvas) {
        val progressAngle = sweepAngle * currentProgress / 100
        val mRectF = getRoundRect(progressbarWidth)

        canvas.drawArc(mRectF, progressAngle.toFloat(), sweepAngle.toFloat() - progressAngle.toFloat(), false, mBgPaint)

    }

    private fun drawProgress(canvas: Canvas) {
        val progressAngle = sweepAngle * currentProgress / 100

        val mShader = SweepGradient(0f, 0f, intArrayOf(progressStartColor, progressEndColor, progressStartColor), floatArrayOf(0f, progressAngle / 360f, 1.1f))
        mProgressPaint.shader = null
        mProgressPaint.shader = mShader
        val mRectF = getRoundRect(progressbarWidth)

        canvas.drawArc(mRectF, 0f, progressAngle.toFloat(), false, mProgressPaint)
    }

    private fun getRoundRect(barWidth: Int): RectF {
        val mRectF = RectF()
        mRectF.left = barWidth / 2.toFloat() - width / 2 // 左上角x
        mRectF.top = barWidth / 2.toFloat() - width / 2 // 左上角y
        mRectF.right = width / 2 - barWidth / 2f // 左下角x
        mRectF.bottom = width / 2 - barWidth / 2f // 右下角y
        return mRectF
    }

    /**
     * 修改颜色透明度
     * @param color
     * @param alpha
     * @return
     */
    fun getGradient(fraction: Float, startColor: Int, endColor: Int): Int {
        val sAlpha = Color.alpha(startColor)
        val sRed = Color.red(startColor)
        val sGreen = Color.green(startColor)
        val sBlue = Color.blue(startColor)

        val eAlpha = Color.alpha(endColor)
        val eRed = Color.red(endColor)
        val eGreen = Color.green(endColor)
        val eBlue = Color.blue(endColor)

        val currentAlpha = getCurrent(sAlpha, eAlpha, fraction)
        val currentRed = getCurrent(sRed, eRed, fraction)
        val currentGreen = getCurrent(sGreen, eGreen, fraction)
        val currentBlue = getCurrent(sBlue, eBlue, fraction)
        return Color.argb(currentAlpha, currentRed, currentGreen, currentBlue)
    }

    fun getCurrent(sNum: Int, eNum: Int, fraction: Float): Int {
        return (sNum + (eNum - sNum) * fraction).toInt()
    }

}
