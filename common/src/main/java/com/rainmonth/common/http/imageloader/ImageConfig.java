package com.rainmonth.common.http.imageloader;

import android.widget.ImageView;

/**
 * 图片加载配置信息基类，这里定义一些所有加载框架都通用的参数
 * Created by RandyZhang on 2018/6/1.
 */

public class ImageConfig {
    protected String url;
    protected ImageView imageView;
    protected int placeholder;      // 加载占位符
    protected int errorPic;         // 加载错误占位符

    public String getUrl() {
        return url;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getPlaceholder() {
        return placeholder;
    }

    public int getErrorPic() {
        return errorPic;
    }
}
