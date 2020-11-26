package com.rainmonth.player.gsy.model;

/**
 *
 */
public class GSYVideoModel {

    private String mUrl;
    private String mTitle;

    public GSYVideoModel(String url, String title) {
        mUrl = url;
        mTitle = title;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }
}
