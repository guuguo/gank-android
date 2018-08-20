package com.guuguo.android.lib.widget.banner.anim.select;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

import com.guuguo.android.lib.widget.banner.anim.BaseAnimator;


public class RotateEnter extends BaseAnimator {
    public RotateEnter() {
        this.mDuration = 200;
    }

    @Override
    public void setAnimation(View view) {
        this.mAnimatorSet.playTogether(new Animator[]{
                ObjectAnimator.ofFloat(view, "rotation", 0, 180)});
    }
}
