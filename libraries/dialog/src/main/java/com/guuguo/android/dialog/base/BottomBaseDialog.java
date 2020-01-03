package com.guuguo.android.dialog.base;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

public abstract class BottomBaseDialog<T extends BottomBaseDialog<T>> extends BottomTopBaseDialog<T> {
    public BottomBaseDialog(Context context, View animateView) {
        super(context);
        mAnimateView = animateView;
    }

    public BottomBaseDialog(Context context) {
        this(context, null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mContentTop.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        mContentTop.setGravity(Gravity.BOTTOM);
        getWindow().setGravity(Gravity.BOTTOM);
        mContentTop.setPadding(mLeft, mTop, mRight, mBottom);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        showWithAnim();
    }

    @Override
    public void dismiss() {
        dismissWithAnim();
    }


}
