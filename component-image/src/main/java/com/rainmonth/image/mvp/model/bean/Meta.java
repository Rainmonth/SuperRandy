package com.rainmonth.image.mvp.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Meta implements Serializable, Parcelable {

    private String title;
    private String description;
    private boolean index;
    private String canonical;

    protected Meta(Parcel in) {
        title = in.readString();
        description = in.readString();
        index = in.readByte() != 0;
        canonical = in.readString();
    }

    public static final Creator<Meta> CREATOR = new Creator<Meta>() {
        @Override
        public Meta createFromParcel(Parcel in) {
            return new Meta(in);
        }

        @Override
        public Meta[] newArray(int size) {
            return new Meta[size];
        }
    };

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

    public void setIndex(boolean index) {
        this.index = index;
    }

    public boolean getIndex() {
        return index;
    }

    public void setCanonical(String canonical) {
        this.canonical = canonical;
    }

    public String getCanonical() {
        return canonical;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeByte((byte) (index ? 1 : 0));
        dest.writeString(canonical);
    }
}