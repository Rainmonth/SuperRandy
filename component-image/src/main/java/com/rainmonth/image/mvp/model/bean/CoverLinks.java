package com.rainmonth.image.mvp.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

class CoverLinks implements Serializable, Parcelable {
    private String self;
    private String html;
    private String download;
    private String download_location;

    protected CoverLinks(Parcel in) {
        self = in.readString();
        html = in.readString();
        download = in.readString();
        download_location = in.readString();
    }

    public static final Creator<CoverLinks> CREATOR = new Creator<CoverLinks>() {
        @Override
        public CoverLinks createFromParcel(Parcel in) {
            return new CoverLinks(in);
        }

        @Override
        public CoverLinks[] newArray(int size) {
            return new CoverLinks[size];
        }
    };

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getDownload_location() {
        return download_location;
    }

    public void setDownload_location(String download_location) {
        this.download_location = download_location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(self);
        dest.writeString(html);
        dest.writeString(download);
        dest.writeString(download_location);
    }
}
