package com.rainmonth.image.mvp.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 封面图片Bean
 */
public class PhotoBean implements Serializable, Parcelable {

    private String id;
    private String created_at;
    private String updated_at;
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
    private List<CollectionBean> current_user_collections;
    private String slug;
    private UserBean user;

    protected PhotoBean(Parcel in) {
        id = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
        width = in.readInt();
        height = in.readInt();
        color = in.readString();
        description = in.readString();
        urls = in.readParcelable(getClass().getClassLoader());
        links = in.readParcelable(getClass().getClassLoader());
        categories = in.createStringArrayList();
        sponsored = in.readByte() != 0;
        likes = in.readInt();
        liked_by_user = in.readByte() != 0;
        if(current_user_collections == null)
            current_user_collections = new ArrayList<>();
        in.readTypedList(current_user_collections, CollectionBean.CREATOR);
        slug = in.readString();
        user = in.readParcelable(getClass().getClassLoader());
    }

    public static final Creator<PhotoBean> CREATOR = new Creator<PhotoBean>() {
        @Override
        public PhotoBean createFromParcel(Parcel in) {
            return new PhotoBean(in);
        }

        @Override
        public PhotoBean[] newArray(int size) {
            return new PhotoBean[size];
        }
    };

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getUpdated_at() {
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

    public void setCurrent_user_collections(List<CollectionBean> current_user_collections) {
        this.current_user_collections = current_user_collections;
    }

    public List<CollectionBean> getCurrent_user_collections() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(created_at);
        dest.writeString(updated_at);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeString(color);
        dest.writeString(description);
        dest.writeParcelable(urls, flags);
        dest.writeParcelable(links, flags);
        dest.writeStringList(categories);
        dest.writeByte((byte) (sponsored ? 1 : 0));
        dest.writeInt(likes);
        dest.writeByte((byte) (liked_by_user ? 1 : 0));
        dest.writeTypedList(current_user_collections);
        dest.writeString(slug);
        dest.writeParcelable(user, flags);
    }


}