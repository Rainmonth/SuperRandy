package com.rainmonth.common.bean;

import android.content.Intent;

import java.io.Serializable;

/**
 * @author 张豪成
 * @date 2019-05-15 13:03
 */
public class ExampleBean implements Serializable {

    public static final int STATE_TODO = 0;         // 等待完成
    public static final int STATE_UNDER = 1;        // 正在做
    public static final int STATE_FINISH = 2;       // 已经完成

    public String title;// 标题
    public String desc; // 描述
    public int status;  // 完成状态
    public Class clazz; // 示例Activity

    public ExampleBean(String title, String desc, int status, Class clazz) {
        this.title = title;
        this.desc = desc;
        this.status = status;
        this.clazz = clazz;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
