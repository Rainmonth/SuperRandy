package com.rainmonth.mvp.model.bean;

import java.io.Serializable;

/**
 * BannerBean 实体定义
 * Created by RandyZhang on 2017/1/23.
 */
public class BannerBean implements Serializable {

    /**
     * id : 10001
     * type : 1
     * title : 活动主页一
     * url : https://www.baidu.com
     * banner_thumb_url : http://pic2.cxtuku.com/00/02/31/b945758fd74d.jpg
     */

    private String id;
    private String type;
    private String title;
    private String url;
    private String banner_thumb_url;

    public BannerBean(String id, String type, String title, String url, String banner_thumb_url) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.url = url;
        this.banner_thumb_url = banner_thumb_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBanner_thumb_url() {
        return banner_thumb_url;
    }

    public void setBanner_thumb_url(String banner_thumb_url) {
        this.banner_thumb_url = banner_thumb_url;
    }
}
