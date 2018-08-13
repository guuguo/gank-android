package com.guuguo.android.lib.widget;

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import kotlin.concurrent.thread

class CustomMultiWaveView : View {
    class WaveBean(
            var waveLength: Float = 0f,
            var period: Int = 3000,
            var heightControl: Int = 0,
            var color: Int = Color.parseColor("#66ffffff")
    )

    //波浪画笔
    private lateinit var mPaint: Paint
    //波浪Path类
    private lateinit var mPath: Path
    //屏幕高度
    private var mScreenHeight = 0f
    //屏幕宽度
    private var mScreenWidth = 0f

    var waveMap: HashMap<WaveBean, Float> = hashMapOf()

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    fun addWave(waveBean: WaveBean) {
        waveMap.put(waveBean, 0f)
    }

    fun addWaves(waveBeans: Array<WaveBean>) {
        waveBeans.forEach {
            waveMap.put(it, 0f)
        }
    }

    private fun init() {
        mPath = Path()
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.style = Paint.Style.FILL_AND_STROKE
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mScreenHeight = h.toFloat()
        mScreenWidth = w.toFloat()
    }

    fun start() {
        isRuning = true
        thread {
            while (isRuning) {
                Thread.sleep(16)
                waveMap.entries.forEach {
                    var off = it.value + it.key.waveLength / it.key.period * 16
                    if (off >= it.key.waveLength)
                        off -= it.key.waveLength
                    it.setValue(off)
                }
                postInvalidate()
            }
        }
    }

    fun stop() {
        isRuning = false
    }

    private var isRuning = false
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        waveMap.entries.forEach {
            drawWave(canvas, it.value, it.key)
        }
    }

    private fun drawWave(canvas: Canvas, mOffset: Float, waveBean: WaveBean) {
        mPath.reset()
        val mCenterY = mScreenHeight - waveBean.heightControl.toFloat()
        //加1.5：至少保证波纹有2个，至少2个才能实现平移效果
        val mWaveCount = Math.round(mScreenWidth / waveBean.waveLength + 1.5).toInt()

        //移到屏幕外最左边
        mPath.moveTo(-waveBean.waveLength + mOffset, mCenterY)
        for (i in 0 until mWaveCount) {
            //正弦曲线
            mPath.quadTo(-waveBean.waveLength * 3 / 4 + i * waveBean.waveLength + mOffset, mCenterY + 30, -waveBean.waveLength / 2 + i * waveBean.waveLength + mOffset, mCenterY)
            mPath.quadTo(-waveBean.waveLength / 4 + i * waveBean.waveLength + mOffset, mCenterY - 30, i * waveBean.waveLength + mOffset, mCenterY)
            //贝塞尔坐标，测试红点
        }
        //填充矩形
        mPath.lineTo(mScreenWidth, mScreenHeight)
        mPath.lineTo(0f, mScreenHeight)
        mPath.close()
        mPaint.color = waveBean.color
        canvas.drawPath(mPath, mPaint)
    }
}