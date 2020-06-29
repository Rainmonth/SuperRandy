package com.rainmonth.mvp.model.bean;

import java.io.Serializable;

/**
 * Created by RandyZhang on 2018/6/15.
 */

public class PursueBean implements Serializable {
    private int type;               // 类型
    private String title;           // 标题
    private String thumb;           // 对应图片地址
    private int timestamp;          // 发布时的时间戳
    private String date;            // 改时间戳对应的日期格式

    public PursueBean(int type, String title, String thumb) {
        this.type = type;
        this.title = title;
        this.thumb = thumb;
    }

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

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
