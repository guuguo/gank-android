package com.guuguo.learnsave.config;

import android.content.Context;

import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;

/**
 * Created by 大哥哥 on 2016/10/18 0018.
 */

public class CustomImageSizeUrlLoader extends BaseGlideUrlLoader<CustomImageSizeModel> {
    public CustomImageSizeUrlLoader(Context context) {
        super( context );
    }

    @Override
    protected String getUrl(CustomImageSizeModel model, int width, int height) {
        return model.requestCustomSizeUrl( width, height );
    }
}