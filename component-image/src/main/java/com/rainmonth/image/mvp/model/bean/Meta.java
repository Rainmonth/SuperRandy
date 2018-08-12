package com.rainmonth.image.mvp.model.bean;

import java.io.Serializable;

public class Meta implements Serializable {

    private String title;
    private String description;
    private boolean index;
    private String canonical;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setIndex(boolean index) {
        this.index = index;
    }

    public boolean getIndex() {
        return index;
    }

    public void setCanonical(String canonical) {
        this.canonical = canonical;
    }

    public String getCanonical() {
        return canonical;
    }

}