package com.rainmonth.image.mvp.model.bean;

import com.rainmonth.common.bean.BaseBean;

/**
 * @author RandyZhang
 * @date 2020/10/14 2:23 PM
 */
public class SubscribeBean extends BaseBean {
    public int index;
    public boolean isRedirectInfo = false;

    public SubscribeBean(int index, boolean isRedirectInfo) {
        this.index = index;
        this.isRedirectInfo = isRedirectInfo;
    }
}
