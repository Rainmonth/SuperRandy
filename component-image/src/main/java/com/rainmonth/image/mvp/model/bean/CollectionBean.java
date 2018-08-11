package com.rainmonth.image.mvp.model.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 合集Bean
 */
public class CollectionBean implements Serializable {

    private long id;                                // id
    private String title;                           // 标题
    private String description;                     //
    private Date published_at;                      //
    private Date updated_at;                        //
    private boolean curated;                        //
    private boolean featured;                       //
    private int total_photos;                       //
    @SerializedName("private")
    private boolean isPrivate;                      //
    private String share_key;                       //
    private List<Tags> tags;                        //
    private PhotoBean cover_photo;                //
    private List<Preview_photos> preview_photos;    //
    private UserBean user;                              //
    @SerializedName("links")
    private CollectionLinks links;                  //
    private Meta meta;                              //

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPublished_at(Date published_at) {
        this.published_at = published_at;
    }

    public Date getPublished_at() {
        return published_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setCurated(boolean curated) {
        this.curated = curated;
    }

    public boolean getCurated() {
        return curated;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public boolean getFeatured() {
        return featured;
    }

    public void setTotal_photos(int total_photos) {
        this.total_photos = total_photos;
    }

    public int getTotal_photos() {
        return total_photos;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public void setShare_key(String share_key) {
        this.share_key = share_key;
    }

    public String getShare_key() {
        return share_key;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    public List<Tags> getTags() {
        return tags;
    }

    public void setCover_photo(PhotoBean cover_photo) {
        this.cover_photo = cover_photo;
    }

    public PhotoBean getCover_photo() {
        return cover_photo;
    }

    public void setPreview_photos(List<Preview_photos> preview_photos) {
        this.preview_photos = preview_photos;
    }

    public List<Preview_photos> getPreview_photos() {
        return preview_photos;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public UserBean getUser() {
        return user;
    }

    public void setLinks(CollectionLinks links) {
        this.links = links;
    }

    public CollectionLinks getLinks() {
        return links;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Meta getMeta() {
        return meta;
    }

}