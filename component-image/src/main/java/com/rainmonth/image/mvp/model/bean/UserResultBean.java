package com.rainmonth.image.mvp.model.bean;

import java.io.Serializable;
import java.util.List;

public class UserResultBean implements Serializable {
    private int total;
    private int total_pages;
    private List<UserBean> results;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int tatal_pages) {
        this.total_pages = tatal_pages;
    }

    public List<UserBean> getResults() {
        return results;
    }

    public void setResults(List<UserBean> results) {
        this.results = results;
    }
}
