package com.rainmonth.player.model;

import com.rainmonth.common.bean.BaseBean;

/**
 * @author RandyZhang
 * @date 2020/12/1 3:46 PM
 */
public class VideoListBean extends BaseBean {
    public String name;             // 视频名称
    public String url;              // 视频播放链接
    public String clarityLevel;     // 清晰度（标准、高清）

    public VideoListBean(String name, String url, String clarityLevel) {
        this.name = name;
        this.url = url;
        this.clarityLevel = clarityLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClarityLevel() {
        return clarityLevel;
    }

    public void setClarityLevel(String clarityLevel) {
        this.clarityLevel = clarityLevel;
    }

    @Override
    public String toString() {
        return this.clarityLevel;
    }
}
