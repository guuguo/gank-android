package com.guuguo.learnsave.config;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.module.GlideModule;

import java.io.InputStream;

/**
 * Created by 大哥哥 on 2016/10/18 0018.
 */

public class CustomImageSizeGlideModule implements GlideModule {
    @Override public void applyOptions(Context context, GlideBuilder builder) {
        // nothing to do here
    }

    @Override public void registerComponents(Context context, Glide glide) {
        glide.register(CustomImageSizeModel.class, InputStream.class, new CustomImageSizeModelFactory());
    }
    private class CustomImageSizeModelFactory implements ModelLoaderFactory<CustomImageSizeModel, InputStream> {
        @Override
        public ModelLoader<CustomImageSizeModel, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new CustomImageSizeUrlLoader( context );
        }

        @Override
        public void teardown() {

        }
    }
}
