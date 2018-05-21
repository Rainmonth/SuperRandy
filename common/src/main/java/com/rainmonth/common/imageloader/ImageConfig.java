package com.rainmonth.common.imageloader;

import android.widget.ImageView;

public class ImageConfig {

  protected String url;
  protected ImageView imageView;
  protected int placeholder;
  protected int errorPic;


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
