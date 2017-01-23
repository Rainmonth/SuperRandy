package com.rainmonth.bean;

import java.io.Serializable;

/**
 * Created by RandyZhang on 16/7/1.
 */
public class SplashBean implements Serializable {
    private boolean hasRemoteSplash;
    // todo update with url later
    private int remoteSplashUrl;
    private String remoteSplashText;
    private String naveTo = "main";

    public boolean isHasRemoteSplash() {
        return hasRemoteSplash;
    }

    public void setHasRemoteSplash(boolean hasRemoteSplash) {
        this.hasRemoteSplash = hasRemoteSplash;
    }

    public int getRemoteSplashUrl() {
        return remoteSplashUrl;
    }

    public void setRemoteSplashUrl(int remoteSplashUrl) {
        this.remoteSplashUrl = remoteSplashUrl;
    }

    public String getRemoteSplashText() {
        return remoteSplashText;
    }

    public void setRemoteSplashText(String remoteSplashText) {
        this.remoteSplashText = remoteSplashText;
    }

    public String getNaveTo() {
        return naveTo;
    }

    public void setNaveTo(String naveTo) {
        this.naveTo = naveTo;
    }
}
