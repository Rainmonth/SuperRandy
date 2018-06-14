package com.rainmonth.common.http;

import java.util.List;

/**
 * Created by RandyZhang on 2018/6/14.
 */

public class PageData<T> {

    /**
     * total : 15
     * currentPage : 1
     * totalPage : 2
     * list : [{"id":15,"is_origin":0,"author":"百度","url":"http://www.baidu.com","title":"百度","category":"0","abstract":"常用网站导航","source_content":"非原创，暂无内容","content":"非原创，暂无内容","create_time":null,"last_update_time":null},{"id":14,"is_origin":0,"author":"11111","url":"12","title":"ceshissssss","category":"0","abstract":"ssssssss","source_content":"非原创，暂无内容","content":"非原创，暂无内容","create_time":null,"last_update_time":null}]
     */

    private int total;
    private String currentPage;
    private int totalPage;
    private List<T> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
