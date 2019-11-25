package com.rainmonth.common.bean;

/**
 * @author 张豪成
 * @date 2019-11-25 14:32
 */
public class BannerBean extends BaseBean {
    public long id;
    public String title;
    public String imageUrl;
    public String redirctUrl;

    public BannerBean(long id, String title, String imageUrl) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
    }
}
