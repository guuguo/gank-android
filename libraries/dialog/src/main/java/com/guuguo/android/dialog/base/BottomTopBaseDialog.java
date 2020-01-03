package com.guuguo.android.dialog.base;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;


public abstract class BottomTopBaseDialog<T extends BottomTopBaseDialog<T>> extends BaseDialog<T> {
    protected View mAnimateView;
    protected long mInnerAnimDuration = 350;
    protected boolean mIsInnerShowAnim;
    protected boolean mIsInnerDismissAnim;
    protected int mLeft, mTop, mRight, mBottom;

    public BottomTopBaseDialog(Context context) {
        super(context);
    }

    /** set duration for inner com.flyco.animation of mAnimateView(设置animateView内置动画时长) */
    public T innerAnimDuration(long innerAnimDuration) {
        mInnerAnimDuration = innerAnimDuration;
        return (T) this;
    }

    public T padding(int left, int top, int right, int bottom) {
        mLeft = left;
        mTop = top;
        mRight = right;
        mBottom = bottom;
        return (T) this;
    }

    /** show dialog and mAnimateView with inner show com.flyco.animation(设置dialog和animateView显示动画) */
    protected void showWithAnim() {
    }

    /** dimiss dialog and mAnimateView with inner dismiss com.flyco.animation(设置dialog和animateView消失动画) */
    protected void dismissWithAnim() {
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mIsInnerDismissAnim || mIsInnerShowAnim) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onBackPressed() {
        if (mIsInnerDismissAnim || mIsInnerShowAnim) {
            return;
        }
        super.onBackPressed();
    }
}
