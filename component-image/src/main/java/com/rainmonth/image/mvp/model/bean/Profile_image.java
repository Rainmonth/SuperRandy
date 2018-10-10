/**
  * Copyright 2018 bejson.com 
  */
package com.rainmonth.image.mvp.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Auto-generated: 2018-08-11 14:16:51
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Profile_image implements Serializable , Parcelable {

    private String small;
    private String medium;
    private String large;

    protected Profile_image(Parcel in) {
        small = in.readString();
        medium = in.readString();
        large = in.readString();
    }

    public static final Creator<Profile_image> CREATOR = new Creator<Profile_image>() {
        @Override
        public Profile_image createFromParcel(Parcel in) {
            return new Profile_image(in);
        }

        @Override
        public Profile_image[] newArray(int size) {
            return new Profile_image[size];
        }
    };

    public void setSmall(String small) {
         this.small = small;
     }
     public String getSmall() {
         return small;
     }

    public void setMedium(String medium) {
         this.medium = medium;
     }
     public String getMedium() {
         return medium;
     }

    public void setLarge(String large) {
         this.large = large;
     }
     public String getLarge() {
         return large;
     }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(small);
        dest.writeString(medium);
        dest.writeString(large);
    }
}