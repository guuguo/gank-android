package com.guuguo.learnsave.config;

/**
 * Created by 大哥哥 on 2016/10/18 0018.
 */

public class CustomImageSizeModelFutureStudio implements CustomImageSizeModel {
    String baseImageUrl;

    public CustomImageSizeModelFutureStudio(String baseImageUrl) {
        this.baseImageUrl = baseImageUrl;
    }

    @Override
    public String requestCustomSizeUrl(int width, int height) {

        return baseImageUrl + "/w/" + width ;
    }
}