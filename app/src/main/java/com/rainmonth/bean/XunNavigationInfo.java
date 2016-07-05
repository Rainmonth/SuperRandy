package com.rainmonth.bean;

import java.io.Serializable;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class XunNavigationInfo implements Serializable{

    private int navIconResId;
    private String navName;

    public XunNavigationInfo(int navIconResId, String navName) {
        this.navIconResId = navIconResId;
        this.navName = navName;
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
