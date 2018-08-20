package com.guuguo.android.lib.widget.banner.anim.select;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

import com.guuguo.android.lib.widget.banner.anim.BaseAnimator;


public class ZoomInEnter extends BaseAnimator {
    public ZoomInEnter() {
        this.mDuration = 200;
    }

    @Override
    public void setAnimation(View view) {
        this.mAnimatorSet.playTogether(new Animator[]{
                ObjectAnimator.ofFloat(view, "scaleX", new float[]{1.0F, 1.5F}),
                ObjectAnimator.ofFloat(view, "scaleY", new float[]{1.0F, 1.5F})});
    }
}
