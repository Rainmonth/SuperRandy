package com.rainmonth.mvp.model.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 文章实体
 * Created by RandyZhang on 16/9/9.
 */
public class ArticleBean implements Serializable {
    /**
     * id : 14
     * is_origin : 0
     * author : 11111
     * url : 12
     * title : ceshissssss
     * category : 0
     * breif_content : ssssssss
     * source_content : 非原创，暂无内容
     * content : 非原创，暂无内容
     * create_time : null
     * last_update_time : null
     */

    private int id;
    private int is_origin;
    private String author;
    private String url;
    private String title;
    private String category;
    @SerializedName("abstract")
    private String breif;
    private String source_content;
    private String content;
    private String create_time;
    private String last_update_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIs_origin() {
        return is_origin;
    }

    public void setIs_origin(int is_origin) {
        this.is_origin = is_origin;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBreif() {
        return breif;
    }

    public void setBreif(String brief) {
        this.breif = brief;
    }

    public String getSource_content() {
        return source_content;
    }

    public void setSource_content(String source_content) {
        this.source_content = source_content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getLast_update_time() {
        return last_update_time;
    }

    public void setLast_update_time(String last_update_time) {
        this.last_update_time = last_update_time;
    }
}
