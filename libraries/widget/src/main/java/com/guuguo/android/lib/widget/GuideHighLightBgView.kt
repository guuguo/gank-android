package com.guuguo.android.lib.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.LinearLayout


class GuideHighLightBgView : LinearLayout {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)

    }

    fun init(attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.GuideHighLightBgView)
        bgColor = a.getColor(R.styleable.GuideHighLightBgView_ghv_backgroundColor, bgColor)
        bgAlpha = a.getInteger(R.styleable.GuideHighLightBgView_ghv_backgroundAlpha, bgAlpha)

        a.recycle()
        setWillNotDraw(false)
    }

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var bgColor = Color.BLACK
    var bgAlpha = 160

    init {
        setWillNotDraw(false)
        circlePaint.color = Color.WHITE
    }

    var targetCX = 0f
    var targetCY = 0f
    var targetRadius = 0f
    var targetWidth = 0
    var targetHeight = 0

    var isCircle = true
    fun drawCircle() {
        dstCanvas?.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        if (isCircle) {
            dstCanvas?.drawCircle(targetCX, targetCY, targetRadius, circlePaint)
        } else {
            dstCanvas?.drawRect(targetCX - targetWidth / 2, targetCY - targetHeight / 2, targetCX + targetWidth / 2, targetCY + targetHeight / 2, circlePaint)
        }
    }

    var srcBm: Bitmap? = null
    var dstBm: Bitmap? = null
    var dstCanvas: Canvas? = null

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w != 0 && h != 0) {
            srcBm = makeSrcRect(w, h)
            createDstBitmap(w, h)
        }
    }

    private fun createDstBitmap(w: Int, h: Int) {
        dstBm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        dstCanvas = Canvas(dstBm!!)
    }

    private fun makeSrcRect(w: Int, h: Int): Bitmap {
        val bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        val lCanvas = Canvas(bm)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = bgColor
        lCanvas.drawRect(RectF(0f, 0f, w.toFloat(), h.toFloat()), paint)
        return bm
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (srcBm == null) srcBm = makeSrcRect(width, height)
        if (dstBm == null) createDstBitmap(width, height)
        ///在dst bitmap中绘制洞洞
        drawCircle()

        ///把 dst bitmap 绘制到画布  洞洞
        canvas.drawBitmap(dstBm, 0f, 0f, mPaint)
        mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)
        mPaint.alpha = bgAlpha
        ///混合模式绘制背景，dst 挖空
        canvas.drawBitmap(srcBm, 0f, 0f, mPaint)
    }

}