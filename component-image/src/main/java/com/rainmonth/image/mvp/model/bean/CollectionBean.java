package com.rainmonth.image.mvp.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 合集Bean
 */
public class CollectionBean implements Serializable, Parcelable {

    private long id;                                // id
    private String title;                           // 标题
    private String description;                     //
    private String published_at;                    //
    private String updated_at;                      //
    private boolean curated;                        //
    private boolean featured;                       //
    private int total_photos;                       //
    @SerializedName("private")
    private boolean isPrivate;                      //
    private String share_key;                       //
    private List<Tags> tags;                        //
    private PhotoBean cover_photo;                  //
    private List<Preview_photos> preview_photos;    //
    private UserBean user;                          //
    @SerializedName("links")
    private CollectionLinks links;                  //
    private Meta meta;                              //

    protected CollectionBean(Parcel in) {
        id = in.readLong();
        title = in.readString();
        description = in.readString();
        published_at = in.readString();
        updated_at = in.readString();
        curated = in.readByte() != 0;
        featured = in.readByte() != 0;
        total_photos = in.readInt();
        isPrivate = in.readByte() != 0;
        share_key = in.readString();
        in.readTypedList(tags, Tags.CREATOR);
        cover_photo = in.readParcelable(PhotoBean.class.getClassLoader());
        in.readTypedList(preview_photos, Preview_photos.CREATOR);
        user = in.readParcelable(UserBean.class.getClassLoader());
        links = in.readParcelable(CollectionLinks.class.getClassLoader());
        meta = in.readParcelable(Meta.class.getClassLoader());
    }

    public static final Creator<CollectionBean> CREATOR = new Creator<CollectionBean>() {
        @Override
        public CollectionBean createFromParcel(Parcel in) {
            return new CollectionBean(in);
        }

        @Override
        public CollectionBean[] newArray(int size) {
            return new CollectionBean[size];
        }
    };

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

    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }

    public String getPublished_at() {
        return published_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getUpdated_at() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(published_at);
        dest.writeString(updated_at);
        dest.writeByte((byte) (curated ? 1 : 0));
        dest.writeByte((byte) (featured ? 1 : 0));
        dest.writeInt(total_photos);
        dest.writeByte((byte) (isPrivate ? 1 : 0));
        dest.writeString(share_key);
        dest.writeTypedList(tags);
        dest.writeParcelable(cover_photo, flags);
        dest.writeTypedList(preview_photos);
        dest.writeParcelable(user, flags);
        dest.writeParcelable(links, flags);
        dest.writeParcelable(meta, flags);
    }
}