package com.rainmonth.image.mvp.model.bean;

import java.util.List;

/**
 * Created by RandyZhang on 2018/8/14.
 */
public class SearchResult<T> {

    private int total;
    private int total_pages;
    private List<T> results;

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

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
