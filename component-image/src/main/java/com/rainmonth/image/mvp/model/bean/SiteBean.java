package com.rainmonth.image.mvp.model.bean;

import java.io.Serializable;

/**
 * @desprition: 个人网站对应的Bean
 * @author: RandyZhang
 * @date: 2018/8/12 下午2:08
 */
public class SiteBean implements Serializable {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}