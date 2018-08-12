package com.rainmonth.image.mvp.model.bean;

/**
 * 预览图片
 */
public class Preview_photos {

    private long id;
    private Urls urls;
    public void setId(long id) {
         this.id = id;
     }
     public long getId() {
         return id;
     }

    public void setUrls(Urls urls) {
         this.urls = urls;
     }
     public Urls getUrls() {
         return urls;
     }

}