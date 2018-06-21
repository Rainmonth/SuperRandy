package com.rainmonth.mvp.model.bean;

import java.io.Serializable;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class MemAlbumBean implements Serializable {

    /**
     * id : 2
     * author : randy
     * cover_url : http://localhost:3000/public/assets/banner/test0.jpg
     * publish_time : 1529564102
     * category : 手势
     * description : 手势
     * like_num : null
     * photo_num : 0
     */

    private int id;
    private String author;
    private String cover_url;
    private int publish_time;
    private String category;
    private String description;
    private Object like_num;
    private int photo_num;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCover_url() {
        return cover_url;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }

    public int getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(int publish_time) {
        this.publish_time = publish_time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getLike_num() {
        return like_num;
    }

    public void setLike_num(Object like_num) {
        this.like_num = like_num;
    }

    public int getPhoto_num() {
        return photo_num;
    }

    public void setPhoto_num(int photo_num) {
        this.photo_num = photo_num;
    }
}
