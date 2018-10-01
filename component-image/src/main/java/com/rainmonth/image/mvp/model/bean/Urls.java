package com.rainmonth.image.mvp.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 图片Url地址，每种图片对应5种地址（有可能有六种，还有一种是自定义的）
 */
public class Urls implements Serializable, Parcelable {

    private String raw;
    private String full;
    private String regular;
    private String small;
    private String thumb;

    protected Urls(Parcel in) {
        raw = in.readString();
        full = in.readString();
        regular = in.readString();
        small = in.readString();
        thumb = in.readString();
    }

    public static final Creator<Urls> CREATOR = new Creator<Urls>() {
        @Override
        public Urls createFromParcel(Parcel in) {
            return new Urls(in);
        }

        @Override
        public Urls[] newArray(int size) {
            return new Urls[size];
        }
    };

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getRaw() {
        return raw;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getFull() {
        return full;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public String getRegular() {
        return regular;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getSmall() {
        return small;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getThumb() {
        return thumb;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(raw);
        dest.writeString(full);
        dest.writeString(regular);
        dest.writeString(small);
        dest.writeString(thumb);
    }
}