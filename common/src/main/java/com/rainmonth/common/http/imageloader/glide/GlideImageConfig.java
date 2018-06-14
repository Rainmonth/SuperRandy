package com.rainmonth.common.http.imageloader.glide;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.load.Transformation;
import com.rainmonth.common.http.imageloader.ImageConfig;

/**
 * Glide配置信息
 */
public class GlideImageConfig extends ImageConfig {

  /**
   * 缓存策略:
   * 0对应DiskCacheStrategy.all
   * 1对应DiskCacheStrategy.NONE
   * 2对应DiskCacheStrategy.RESOURCE
   * 3对应DiskCacheStrategy.DATA
   * 4对应DiskCacheStrategy.AUTOMATIC
   */
  private int cacheStrategy;
  private Transformation<Bitmap> transformation;//glide用它来改变图形的形状
  private int fallback;
  private int[] size;

  private GlideImageConfig(Builder builder) {
    this.url = builder.url;
    this.imageView = builder.imageView;
    this.placeholder = builder.placeholder;
    this.errorPic = builder.errorPic;
    this.fallback = builder.fallback;
    this.cacheStrategy = builder.cacheStrategy;
    this.transformation = builder.transformation;
    this.size = builder.size;
  }

  public static Builder builder() {
    return new Builder();
  }

  public int getCacheStrategy() {
    return cacheStrategy;
  }

  public int getFallback() {
    return fallback;
  }

  public Transformation<Bitmap> getTransformation() {
    return transformation;
  }

  public int[] getSize() {
    return size;
  }

  public static final class Builder {

    private String url;
    private ImageView imageView;
    private int placeholder;
    private int errorPic;
    private int fallback; //请求 url 为空,则使用此图片作为占位符
    private int cacheStrategy;
    private Transformation<Bitmap> transformation;//glide用它来改变图形的形状
    private int[] size;

    private Builder() {
    }

    public Builder url(String url) {
      this.url = url;
      return this;
    }

    public Builder placeholder(int placeholder) {
      this.placeholder = placeholder;
      return this;
    }

    public Builder errorPic(int errorPic) {
      this.errorPic = errorPic;
      return this;
    }

    public Builder fallback(int fallback) {
      this.fallback = fallback;
      return this;
    }


    public Builder imageView(ImageView imageView) {
      this.imageView = imageView;
      return this;
    }

    public Builder cacheStrategy(int cacheStrategy) {
      this.cacheStrategy = cacheStrategy;
      return this;
    }

    public Builder transformation(Transformation<Bitmap> transformation) {
      this.transformation = transformation;
      return this;
    }

    public Builder override(int width, int height) {
      this.size = new int[]{width, height};
      return this;
    }

    public GlideImageConfig build() {
      return new GlideImageConfig(this);
    }
  }
}
