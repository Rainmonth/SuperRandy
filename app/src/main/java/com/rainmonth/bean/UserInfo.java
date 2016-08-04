package com.rainmonth.bean;

import java.io.Serializable;

/**
 * Created by RandyZhang on 16/8/2.
 */
public class UserInfo implements Serializable {
    private int id;
    private String username;
    private String psw;
    private String avatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
