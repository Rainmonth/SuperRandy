package com.rainmonth.bean;

import java.io.Serializable;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class RenContentBean implements Serializable{

    private int id;
    private int imageResId;
    private String tagType;
    private String tagName;
    private String title;

    public RenContentBean(int id, int imageResId, String tagType, String tagName, String title) {
        this.id = id;
        this.imageResId = imageResId;
        this.tagType = tagType;
        this.tagName = tagName;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
