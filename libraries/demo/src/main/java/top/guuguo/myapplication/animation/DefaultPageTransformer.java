package top.guuguo.myapplication.animation;

import android.view.View;

import com.guuguo.android.lib.utils.BasePageTransformer;

public class DefaultPageTransformer extends BasePageTransformer {


    @Override
    public void handleInvisiblePage(View view, float position) {
        view.setAlpha(1f);
        view.setTranslationX(0f);
    }

    @Override
    public void handleLeftPage(View view, float position) {
        view.setAlpha(1f);
        view.setTranslationX(0f);
    }

    @Override
    public void handleRightPage(View view, float position) {
        view.setAlpha(1f);
        view.setTranslationX(0f);
    }
}  