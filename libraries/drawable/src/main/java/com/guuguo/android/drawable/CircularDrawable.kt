package com.guuguo.android.drawable

import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.os.SystemClock
import android.view.animation.LinearInterpolator
import android.graphics.Bitmap


/**
 * 圆环圆圈转动
 * @author guuguo
 *
 */
class CircularDrawable : Drawable(), Animatable, IDrawableTheme {
    var mRoundColor = Color.argb(100, 255, 255, 255)
    var mIndicatorColor = Color.WHITE

    fun Int.dpToPx(): Int {
        return (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
    }

    override fun dark() {
        mRoundColor = Color.parseColor("#66FFFFFF"); mIndicatorColor = Color.WHITE
    }

    override fun light() {
        mRoundColor = Color.parseColor("#66000000"); mIndicatorColor = Color.BLACK
    }

    private var mPadding = 5f
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = color
        strokeWidth = 3.dpToPx().toFloat()
    }
    private var mIsRunning: Boolean = false
    private var mProgress = 0
    private var mAnimateAngle: Float = 0.toFloat()
    private val mInterpolator = LinearInterpolator()

    private var mBufferCircleBgBitmap: Bitmap? = null
    private var mBufferCircleFgBitmap: Bitmap? = null
    private val mUpdater = object : Runnable {
        override fun run() {
            if (mIsRunning) {
                if (mProgress == 49) {
                    mProgress = 0
                } else {
                    mProgress++
                }

                val input = Math.abs(mProgress * 0.02f)
                mAnimateAngle = 360 * mInterpolator.getInterpolation(input)
                scheduleSelf(this, SystemClock.uptimeMillis() + FRAME_DURATION)
                invalidateSelf()
            }
        }
    }

    override fun start() {
        if (!isRunning) {
            mIsRunning = true
            mProgress = 0
            scheduleSelf(mUpdater, SystemClock.uptimeMillis() + FRAME_DURATION)
            invalidateSelf()
        }
    }

    override fun stop() {
        if (isRunning) {
            mIsRunning = false
        }
    }

    override fun isRunning(): Boolean {
        return mIsRunning
    }

    override fun draw(canvas: Canvas) {

        if (mBufferCircleBgBitmap == null) {
            val radius = Math.min(canvas.width, canvas.height) / 2

            mPaint.color = mRoundColor
            mBufferCircleBgBitmap =
                Bitmap.createBitmap(canvas.width, canvas.height, Bitmap.Config.ARGB_8888);
            val mBufferCircleBgCanvas = Canvas(mBufferCircleBgBitmap!!)
            mBufferCircleBgCanvas.drawCircle(
                canvas.width * 0.5f,
                canvas.height * 0.5f,
                radius - mPadding,
                mPaint
            )


            val hExtend = (canvas.width - radius * 2) / 2
            val vExtend = (canvas.height - radius * 2) / 2
            mPaint.color = mIndicatorColor
            val rectF = RectF(
                mPadding + hExtend,
                mPadding + vExtend,
                canvas.width - mPadding - hExtend,
                canvas.height - mPadding - vExtend
            )
            mBufferCircleFgBitmap =
                Bitmap.createBitmap(canvas.width, canvas.height, Bitmap.Config.ARGB_8888)
            val mBufferCircleFgCanvas = Canvas(mBufferCircleFgBitmap!!)
            mBufferCircleFgCanvas.drawArc(rectF, 0f, 60f, false, mPaint)//第四个参数是否显示半径
        }
        mBufferCircleFgBitmap?.let { canvas.drawBitmap(it, 0f, 0f, mPaint) }

        canvas.save()
        canvas.translate(canvas.width / 2f, canvas.height / 2f)
        canvas.rotate(mAnimateAngle)
        canvas.translate(-canvas.width / 2f, -canvas.height / 2f)

        mBufferCircleFgBitmap?.let { canvas.drawBitmap(it, 0f, 0f, mPaint) }
        canvas.restore()
    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
        invalidateSelf()
    }

    override fun setColorFilter(cf: ColorFilter?) {
        mPaint.colorFilter = cf
        invalidateSelf()
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    companion object {

        private val FRAME_DURATION = (1000 / 60).toLong()
    }

}