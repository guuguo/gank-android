package com.guuguo.android.lib.widget.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.guuguo.android.R;
import com.guuguo.android.lib.utils.DisplayUtil;


/**
 * Created by guodeqing on 16/4/5.
 */
public class CircleProgressBar extends View {
    private Drawable icon;
    /**
     * id:&:R.color.
     */
    private Paint mPaint;
    private int progress;
    private int max;
    private int animateProgress = 0;

    //width
    private int progressbarWidth;
    private int backgroundBarWidth;

    //color
    private int bgColor = Color.GRAY;
    private int progressColor = Color.BLUE;

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public void setMax(int max) {
        this.max = max;
    }

    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleProgressBar, defStyleAttr, 0);
        initByAttributes(attributes);
        attributes.recycle();
        initPaint();
    }

    protected void initByAttributes(TypedArray attributes) {
        progressbarWidth = attributes.getInteger(R.styleable.CircleProgressBar_cpb_progressbar_width, DisplayUtil.dip2px(4));
        backgroundBarWidth = attributes.getInteger(R.styleable.CircleProgressBar_cpb_background_bar_width, progressbarWidth);

        progress = attributes.getInteger(R.styleable.CircleProgressBar_cpb_progress, 0);
        max = attributes.getInteger(R.styleable.CircleProgressBar_cpb_max, 100);

        bgColor = attributes.getColor(R.styleable.CircleProgressBar_cpb_background_bar_color, Color.GRAY);
        progressColor = attributes.getInteger(R.styleable.CircleProgressBar_cpb_progressbar_color, Color.BLUE);
    }

    private void initPaint() {
        // 初始化paint
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true);
        drawBackground(canvas);
        drawProgress(canvas);
    }

    private void drawBackground(Canvas canvas) {
        // 设置画笔相关属性
        mPaint.setColor(bgColor);
        mPaint.setStrokeWidth(backgroundBarWidth);
        mPaint.setStyle(Paint.Style.STROKE);

        RectF mRectF = getRoundRect(backgroundBarWidth);
        // 绘制圆圈，进度条背景
        canvas.drawArc(mRectF, -90, 360, false, mPaint);
    }

    private void drawProgress(Canvas canvas) {
        // 设置画笔相关属性
        mPaint.setColor(progressColor);
        mPaint.setStrokeWidth(progressbarWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        // 位置
        RectF mRectF = getRoundRect(progressbarWidth);

        // 绘制圆圈，进度条背景
        canvas.drawArc(mRectF, -90, ((float) progress) / max * 360, false, mPaint);
    }

    private RectF getRoundRect(int barWidth) {
        RectF mRectF = new RectF();
        mRectF.left = barWidth / 2; // 左上角x
        mRectF.top = barWidth / 2; // 左上角y
        mRectF.right = getWidth() - barWidth / 2; // 左下角x
        mRectF.bottom = getHeight() - barWidth / 2; // 右下角y
        return mRectF;
    }

}
