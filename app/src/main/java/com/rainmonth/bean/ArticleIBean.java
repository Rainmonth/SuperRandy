package com.rainmonth.bean;

import java.io.Serializable;

/**
 * 文章实体
 * Created by RandyZhang on 16/9/9.
 */
public class ArticleIBean implements Serializable {
    // id
    private String id;

    // 文章标题
    private String title;

    // 文章作者
    private String author;

    // 文章发布时间
    private String publishTime;

    // 文章喜欢人数
    private int likeNum;

    // 文章浏览人数
    private int viewNum;

    // 文章图片
    private String thumbUrl;

    // 摘要
    private String summarize;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getViewNum() {
        return viewNum;
    }

    public void setViewNum(int viewNum) {
        this.viewNum = viewNum;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getSummarize() {
        return summarize;
    }

    public void setSummarize(String summarize) {
        this.summarize = summarize;
    }
}
