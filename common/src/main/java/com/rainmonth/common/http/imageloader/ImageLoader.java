package com.rainmonth.common.http.imageloader;

import android.content.Context;

import javax.inject.Inject;

/**
 * 图片加载核心类
 * 采用策略模式和建造者模式实现，方便之后动态切换图片请求框架
 * Created by RandyZhang on 2018/6/1.
 */

public class ImageLoader {
    @Inject
    BaseImageLoaderStrategy mStrategy;

    @Inject
    public ImageLoader() {
    }

    /**
     * 加载图片
     *
     * @param context
     * @param imageConfig
     * @param <T>
     */
    public <T extends ImageConfig> void loadImage(Context context, T imageConfig) {
        this.mStrategy.loadImage(context, imageConfig);
    }

    public <T extends ImageConfig> void clear(Context context, T imageConfig) {
        this.mStrategy.clear(context, imageConfig);
    }

    public BaseImageLoaderStrategy getImageLoadStrategy() {
        return mStrategy;
    }

    /**
     * 设置图片加载策略
     *
     * @param mStrategy
     */
    public void setImageLoadStrategy(BaseImageLoaderStrategy mStrategy) {
        this.mStrategy = mStrategy;
    }
}
