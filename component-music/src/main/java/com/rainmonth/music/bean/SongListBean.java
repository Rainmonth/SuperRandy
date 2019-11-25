package com.rainmonth.music.bean;

import com.rainmonth.common.bean.BaseBean;

/**
 * @author 张豪成
 * @date 2019-11-25 14:41
 */
public class SongListBean extends BaseBean {
    public long id;
    public String title;
    public String imageUrl;

    public SongListBean(long id, String title, String imageUrl) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
    }
}
