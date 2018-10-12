package com.rainmonth.image.mvp.model.bean;

import java.io.Serializable;
import java.util.List;

public class CollectionResultBean implements Serializable {
    private int total;
    private int total_pages;
    private List<CollectionBean> results;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTatal_pages() {
        return total_pages;
    }

    public void setTatal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<CollectionBean> getResults() {
        return results;
    }

    public void setResults(List<CollectionBean> results) {
        this.results = results;
    }
}
