package com.rainmonth.mvp.model.bean;

import java.io.Serializable;

/**
 * BannerBean 实体定义
 * Created by RandyZhang on 2017/1/23.
 */
public class BannerBean implements Serializable {


    /**
     * id : 1
     * title : 端午节2
     * type : 6
     * thumb : http://rainmonth.cn/public/assets/banner/346373.jpg
     * url : http://rainmonth.cn/public/assets/banner/346373.jpg
     */

    private int id;
    private String title;
    private int type;
    private String thumb;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "BannerBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", thumb='" + thumb + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
