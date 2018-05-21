package com.rainmonth.mvp.model.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by RandyZhang on 2017/2/7.
 */

public class ArticleGroupBean implements Serializable {

    /**
     * type : 0
     * type_name : 热门推荐
     * list : [{"id":"10011","type":"1","type_name":"行走的力量","title":"行走","summarize":"you can you up,no can no bb","thumb_url":"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg","url":"https://www.baidu.com","publish_time":"2017-02-07","author":"Randy Zhang","like_num":"0","view_num":"0","collect_num":"0"},{"id":"10021","type":"2","type_name":"分享的力量","title":"分享","summarize":"you can you up,no can no bb","thumb_url":"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_share.jpg","url":"https://www.baidu.com","publish_time":"2017-02-07","author":"Randy Zhang","like_num":"0","view_num":"0","collect_num":"0"},{"id":"10031","type":"3","type_name":"阅读的力量","title":"阅读","summarize":"you can you up,no can no bb","thumb_url":"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_read.jpg","url":"https://www.baidu.com","publish_time":"2017-02-07","author":"Randy Zhang","like_num":"0","view_num":"0","collect_num":"0"},{"id":"10041","type":"4","type_name":"音乐的力量","title":"音乐","summarize":"you can you up,no can no bb","thumb_url":"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_sing.jpg","url":"https://www.baidu.com","publish_time":"2017-02-07","author":"Randy Zhang","like_num":"0","view_num":"0","collect_num":"0"}]
     */

    private String type;
    private String type_name;
    @SerializedName("list")
    private List<ArticleBean> articleBeanList;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public List<ArticleBean> getList() {
        return articleBeanList;
    }

    public void setList(List<ArticleBean> articleBeanList) {
        this.articleBeanList = articleBeanList;
    }
}
