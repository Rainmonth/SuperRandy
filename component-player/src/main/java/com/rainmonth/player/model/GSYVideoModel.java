package com.rainmonth.player.model;

/**
 *
 */
public class GSYVideoModel {

    private String mUrl;                // 视频地址
    private String mTitle;              // 视频标题

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
