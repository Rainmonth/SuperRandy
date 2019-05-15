package com.rainmonth.common.bean;

import java.io.Serializable;

/**
 * @author 张豪成
 * @date 2019-05-15 13:03
 */
public class ExampleBean implements Serializable {
    public String title;// 标题
    public String satus;// 完成状态

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSatus() {
        return satus;
    }

    public void setSatus(String satus) {
        this.satus = satus;
    }
}
