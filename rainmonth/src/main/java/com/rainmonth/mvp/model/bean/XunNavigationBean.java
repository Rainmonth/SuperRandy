package com.rainmonth.mvp.model.bean;

import java.io.Serializable;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class XunNavigationBean implements Serializable {


    /**
     * type : 4
     * title : 电影
     * icon_url : http://localhost:3000/public/assets/extra/test3.jpg
     */

    private int type;
    private String title;
    private String icon_url;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    @Override
    public String toString() {
        return "XunNavigationBean{" +
                "type=" + type +
                ", title='" + title + '\'' +
                ", icon_url='" + icon_url + '\'' +
                '}';
    }
}
