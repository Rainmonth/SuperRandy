package com.rainmonth.mvp.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RandyZhang on 2018/6/22.
 */

public class TestBean implements Serializable {
    private List<ArticleBean> articleBeanList;
    private List<BannerBean> bannerBeanList;

    public TestBean() {
        this.articleBeanList = new ArrayList<>();
        this.bannerBeanList = new ArrayList<>();
    }

    public List<ArticleBean> getArticleBeanList() {
        return articleBeanList;
    }

    public void setArticleBeanList(List<ArticleBean> articleBeanList) {
        this.articleBeanList = articleBeanList;
    }

    public List<BannerBean> getBannerBeanList() {
        return bannerBeanList;
    }

    public void setBannerBeanList(List<BannerBean> bannerBeanList) {
        this.bannerBeanList = bannerBeanList;
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "articleBeanList=" + articleBeanList +
                ", bannerBeanList=" + bannerBeanList +
                '}';
    }
}
