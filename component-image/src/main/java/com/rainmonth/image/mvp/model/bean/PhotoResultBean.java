package com.rainmonth.image.mvp.model.bean;

import java.io.Serializable;
import java.util.List;

public class PhotoResultBean implements Serializable {
    private int total;
    private int total_pages;
    private List<PhotoBean> results;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<PhotoBean> getResults() {
        return results;
    }

    public void setResults(List<PhotoBean> results) {
        this.results = results;
    }
}
