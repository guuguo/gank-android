package com.guuguo.android.lib.widget.roundview;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.util.AttributeSet;
import android.view.View;

/** 用于需要圆角矩形框背景的 ConstraintLayout 的情况,减少直接使用FrameLayout时引入的shape资源文件 */
public class RoundConstraintLayout extends ConstraintLayout {
    private RoundViewDelegate delegate;

    public RoundConstraintLayout(Context context) {
        this(context, null);
    }

    public RoundConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        delegate = new RoundViewDelegate(this, context, attrs);
    }

    /** use delegate to set attr */
    public RoundViewDelegate getDelegate() {
        return delegate;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (delegate.isWidthHeightEqual() && getWidth() > 0 && getHeight() > 0) {
            int max = Math.max(getWidth(), getHeight());
            int measureSpec = View.MeasureSpec.makeMeasureSpec(max, View.MeasureSpec.EXACTLY);
            super.onMeasure(measureSpec, measureSpec);
            return;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (delegate.isRadiusHalfHeight()) {
            delegate.setCornerRadius(getHeight() / 2);
        }else {
            delegate.setBgSelector();
        }
    }
}
