package com.rainmonth.image.mvp.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 合集Links Bean
 */
public class CollectionLinks implements Serializable, Parcelable {

    private String self;
    private String html;
    private String photos;
    private String related;

    protected CollectionLinks(Parcel in) {
        self = in.readString();
        html = in.readString();
        photos = in.readString();
        related = in.readString();
    }

    public static final Creator<CollectionLinks> CREATOR = new Creator<CollectionLinks>() {
        @Override
        public CollectionLinks createFromParcel(Parcel in) {
            return new CollectionLinks(in);
        }

        @Override
        public CollectionLinks[] newArray(int size) {
            return new CollectionLinks[size];
        }
    };

    public void setSelf(String self) {
        this.self = self;
    }

    public String getSelf() {
        return self;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getHtml() {
        return html;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getPhotos() {
        return photos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(self);
        dest.writeString(html);
        dest.writeString(photos);
        dest.writeString(related);
    }
}