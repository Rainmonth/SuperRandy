package com.rainmonth.image.mvp.model.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户Bean
 */
public class UserBean implements Serializable {

    private String id;
    private Date updated_at;
    private String username;
    private String name;
    private String first_name;
    private String last_name;
    private String twitter_username;
    private String portfolio_url;
    private String bio;
    private String location;
    @SerializedName("links")
    private UserLinks links;
    private Profile_image profile_image;
    private String instagram_username;
    private int total_collections;
    private int total_likes;
    private int total_photos;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setTwitter_username(String twitter_username) {
        this.twitter_username = twitter_username;
    }

    public String getTwitter_username() {
        return twitter_username;
    }

    public void setPortfolio_url(String portfolio_url) {
        this.portfolio_url = portfolio_url;
    }

    public String getPortfolio_url() {
        return portfolio_url;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBio() {
        return bio;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLinks(UserLinks links) {
        this.links = links;
    }

    public UserLinks getLinks() {
        return links;
    }

    public void setProfile_image(Profile_image profile_image) {
        this.profile_image = profile_image;
    }

    public Profile_image getProfile_image() {
        return profile_image;
    }

    public void setInstagram_username(String instagram_username) {
        this.instagram_username = instagram_username;
    }

    public String getInstagram_username() {
        return instagram_username;
    }

    public void setTotal_collections(int total_collections) {
        this.total_collections = total_collections;
    }

    public int getTotal_collections() {
        return total_collections;
    }

    public void setTotal_likes(int total_likes) {
        this.total_likes = total_likes;
    }

    public int getTotal_likes() {
        return total_likes;
    }

    public void setTotal_photos(int total_photos) {
        this.total_photos = total_photos;
    }

    public int getTotal_photos() {
        return total_photos;
    }

}