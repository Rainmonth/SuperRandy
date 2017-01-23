package com.rainmonth.bean;

import java.io.Serializable;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class XunNavigationBean implements Serializable{

    private int type;
    private int navIconResId;
    private String navName;

    public XunNavigationBean(int type, int navIconResId, String navName) {
        this.type = type;
        this.navIconResId = navIconResId;
        this.navName = navName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNavIconResId() {
        return navIconResId;
    }

    public void setNavIconResId(int navIconResId) {
        this.navIconResId = navIconResId;
    }

    public String getNavName() {
        return navName;
    }

    public void setNavName(String navName) {
        this.navName = navName;
    }
}
