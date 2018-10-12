package com.rainmonth.image.mvp.model.bean;

import java.io.Serializable;

public class MetaBean implements Serializable {

    /**
     * keyword : mountain
     * text : null
     * title : Mountain Pictures | Download Free Images on Unsplash
     * description : null
     * suffix : null
     * index : true
     * h1 : null
     * canonical : null
     */

    private String keyword;
    private String text;
    private String title;
    private String description;
    private String suffix;
    private boolean index;
    private Object h1;
    private Object canonical;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public boolean isIndex() {
        return index;
    }

    public void setIndex(boolean index) {
        this.index = index;
    }

    public Object getH1() {
        return h1;
    }

    public void setH1(Object h1) {
        this.h1 = h1;
    }

    public Object getCanonical() {
        return canonical;
    }

    public void setCanonical(Object canonical) {
        this.canonical = canonical;
    }
}
