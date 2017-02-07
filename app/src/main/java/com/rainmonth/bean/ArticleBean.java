package com.rainmonth.bean;

import java.io.Serializable;

/**
 * 文章实体
 * Created by RandyZhang on 16/9/9.
 */
public class ArticleBean implements Serializable {

    /**
     * id : 10042
     * type : 4
     * type_name : 音乐的力量
     * title : 话语
     * summarize : you can you up,no can no bb
     * thumb_url : https://image.baidu.com/sample.png
     * url : https://www.baidu.com
     * publish_time : 2017-02-07
     * author : Randy Zhang
     * like_num : 0
     * view_num : 0
     * collect_num : 0
     */

    private String id;                  // 文章id
    private String type;                // 类型
    private String type_name;           // 类型名称
    private String title;               // 标题
    private String summarize;           // 摘要
    private String thumb_url;           // 缩略图URL地址
    private String url;                 // 文章URL地址
    private String publish_time;        // 发布时间
    private String author;              // 作者
    private String like_num;            // 喜欢人数
    private String view_num;            // 浏览人数
    private String collect_num;         // 收藏人数

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummarize() {
        return summarize;
    }

    public void setSummarize(String summarize) {
        this.summarize = summarize;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLike_num() {
        return like_num;
    }

    public void setLike_num(String like_num) {
        this.like_num = like_num;
    }

    public String getView_num() {
        return view_num;
    }

    public void setView_num(String view_num) {
        this.view_num = view_num;
    }

    public String getCollect_num() {
        return collect_num;
    }

    public void setCollect_num(String collect_num) {
        this.collect_num = collect_num;
    }
}
