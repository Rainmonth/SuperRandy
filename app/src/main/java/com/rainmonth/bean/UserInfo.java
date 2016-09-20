package com.rainmonth.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by RandyZhang on 16/8/2.
 */
public class UserInfo implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("username")
    private String username;
    @SerializedName("psw")
    private String psw;
    @SerializedName("email")
    private String email;
    @SerializedName("avatar")
    private String avatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
