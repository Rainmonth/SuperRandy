package com.rainmonth.image.mvp.model.bean;

import java.io.Serializable;

/**
 * 图片Url地址，每种图片对应5种地址（有可能有六种，还有一种是自定义的）
 */
public class Urls implements Serializable {

    private String raw;
    private String full;
    private String regular;
    private String small;
    private String thumb;

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

}