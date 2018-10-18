package com.rainmonth.image.mvp.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 预览图片
 */
public class Preview_photos implements Serializable, Parcelable {

    private long id;
    private Urls urls;

    protected Preview_photos(Parcel in) {
        id = in.readLong();
        urls = in.readParcelable(Urls.class.getClassLoader());
    }

    public static final Creator<Preview_photos> CREATOR = new Creator<Preview_photos>() {
        @Override
        public Preview_photos createFromParcel(Parcel in) {
            return new Preview_photos(in);
        }

        @Override
        public Preview_photos[] newArray(int size) {
            return new Preview_photos[size];
        }
    };

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    public Urls getUrls() {
        return urls;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeParcelable(urls, flags);
    }
}