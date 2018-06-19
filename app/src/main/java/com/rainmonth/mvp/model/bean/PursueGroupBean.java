package com.rainmonth.mvp.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Pursue Group
 * Created by RandyZhang on 2018/6/15.
 */
public class PursueGroupBean implements Serializable {

    private int groupType;
    private String groupTitle;
    private String publish_time = "2018-06-15";
    private List<PursueBean> list;

    public PursueGroupBean(int groupType, String groupTitle, List<PursueBean> list) {
        this.groupType = groupType;
        this.groupTitle = groupTitle;
        this.list = list;
    }

    public int getGroupType() {
        return groupType;
    }

    public void setGroupType(int groupType) {
        this.groupType = groupType;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public List<PursueBean> getList() {
        return list;
    }

    public void setList(List<PursueBean> list) {
        this.list = list;
    }
}
