package top.guuguo.myapplication.animation;

import android.view.View;

import com.guuguo.android.lib.utils.BasePageTransformer;

public class AlphaPageTransformer extends BasePageTransformer {



    public AlphaPageTransformer() {
    }


    @Override
    public void handleInvisiblePage(View view, float position) {
        view.setAlpha(0);
    }

    @Override
    public void handleLeftPage(View view, float position) {
        view.setAlpha(1 + position);
        view.setTranslationX(-view.getWidth() * position);
    }

    @Override
    public void handleRightPage(View view, float position) {
        view.setAlpha(1 - position);
        view.setTranslationX(-view.getWidth() * position);
    }
}  