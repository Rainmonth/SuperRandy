package com.rainmonth.common.http;

/**
 * Created by RandyZhang on 2018/6/14.
 */

public class PageResult<T> extends BaseResponse {
    private PageData<T> data;

    public PageData<T> getData() {
        return data;
    }

    public void setData(PageData<T> data) {
        this.data = data;
    }
}
