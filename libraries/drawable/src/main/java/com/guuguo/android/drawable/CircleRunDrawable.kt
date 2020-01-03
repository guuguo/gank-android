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
class CircleRunDrawable : Drawable(), Animatable, IDrawableTheme {
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
        style = Paint.Style.FILL
        color = color
        strokeWidth = 3.dpToPx().toFloat()
    }
    private var mIsRunning: Boolean = false
    private var mProgress = 0f
    private var mAnimateAngle: Float = 0.toFloat()
    private val mInterpolator = LinearInterpolator()

    private var mBufferCircleFgBitmap: Bitmap? = null
    private val mUpdater = object : Runnable {
        override fun run() {
            val speed = 0.011f
            val tend = 1 / speed
            if (mIsRunning) {
                if (mProgress >= tend) {
                    mProgress -= tend
                } else {
                    mProgress++
                }

                val input = Math.abs(mProgress * speed)
                mAnimateAngle = 360 * mInterpolator.getInterpolation(input)
                scheduleSelf(this, SystemClock.uptimeMillis() + FRAME_DURATION)
                invalidateSelf()
            }
        }
    }

    override fun start() {
        if (!isRunning) {
            mIsRunning = true
            mProgress = 0f
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

    val size = 7
    override fun draw(canvas: Canvas) {

        if (mBufferCircleFgBitmap == null) {
            val cx = bounds.width() / 2
            val cy = bounds.height() / 2
            val radius = Math.min(bounds.width(), bounds.height()) / 2 - 18

            mPaint.color = mIndicatorColor
            mBufferCircleFgBitmap = Bitmap.createBitmap(bounds.width(), bounds.height(), Bitmap.Config.ARGB_8888)
            val mBufferCircleFgCanvas = Canvas(mBufferCircleFgBitmap!!)

            (0 until size).forEach {
                val lcy = cy - Math.sin(2 * Math.PI / size * it) * radius
                val lcx = cx + Math.cos(2 * Math.PI / size * it) * radius
                val lRadius = 17 - 1.2 * it
                mBufferCircleFgCanvas.drawCircle(lcx.toFloat(), lcy.toFloat(), lRadius.toFloat(), mPaint)//第四个参数是否显示半径
            }
        }

        canvas.save()
        canvas.translate(bounds.width() / 2f, bounds.height() / 2f)
        canvas.rotate(mAnimateAngle)
        canvas.translate(-bounds.width() / 2f, -bounds.height() / 2f)

        canvas.drawBitmap(mBufferCircleFgBitmap, 0f, 0f, mPaint)
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