package com.rainmonth.image.mvp.model.bean;

import java.io.Serializable;

/**
 * 合集Links Bean
 */
public class CollectionLinks implements Serializable {

    private String self;
    private String html;
    private String photos;
    private String related;

    public void setSelf(String self) {
        this.self = self;
    }

    public String getSelf() {
        return self;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getHtml() {
        return html;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getPhotos() {
        return photos;
    }
}