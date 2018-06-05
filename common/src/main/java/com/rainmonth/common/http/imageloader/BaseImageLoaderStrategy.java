package com.rainmonth.common.http.imageloader;

import android.content.Context;

/**
 * 图片加载策略接口
 * Created by RandyZhang on 2018/6/1.
 */

public interface BaseImageLoaderStrategy<T extends ImageConfig> {
    /**
     * 加载图片
     *
     * @param ctx
     * @param config
     */
    void loadImage(Context ctx, T config);

    /**
     * 停止加载
     *
     * @param ctx
     * @param config
     */
    void clear(Context ctx, T config);
}
