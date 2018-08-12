package com.rainmonth.image.mvp.model.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 封面图片Bean
 */
public class PhotoBean implements Serializable {

    private String id;
    private Date created_at;
    private Date updated_at;
    private int width;
    private int height;
    private String color;
    private String description;
    private Urls urls;
    @SerializedName("links")
    private CoverLinks links;
    private List<String> categories;                    // 对应分类列表
    private boolean sponsored;
    private int likes;
    private boolean liked_by_user;
    private List<String> current_user_collections;
    private String slug;
    private UserBean user;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    public Urls getUrls() {
        return urls;
    }

    public void setLinks(CoverLinks links) {
        this.links = links;
    }

    public CoverLinks getLinks() {
        return links;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setSponsored(boolean sponsored) {
        this.sponsored = sponsored;
    }

    public boolean getSponsored() {
        return sponsored;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getLikes() {
        return likes;
    }

    public void setLiked_by_user(boolean liked_by_user) {
        this.liked_by_user = liked_by_user;
    }

    public boolean getLiked_by_user() {
        return liked_by_user;
    }

    public void setCurrent_user_collections(List<String> current_user_collections) {
        this.current_user_collections = current_user_collections;
    }

    public List<String> getCurrent_user_collections() {
        return current_user_collections;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSlug() {
        return slug;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public UserBean getUser() {
        return user;
    }

}