package com.rainmonth.common.di.module;

import com.rabtman.common.imageloader.BaseImageLoaderStrategy;
import com.rabtman.common.imageloader.glide.GlideImageLoaderStrategy;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class ImageModule {

  @Singleton
  @Provides
  public BaseImageLoaderStrategy provideImageLoaderStrategy(
      GlideImageLoaderStrategy glideImageLoaderStrategy) {
    return glideImageLoaderStrategy;
  }

}
