package com.rainmonth.image.mvp.model.bean;

import com.rainmonth.common.bean.BaseBean;

/**
 * @author RandyZhang
 * @date 2020/10/14 2:23 PM
 */
public class SubscribeBean extends BaseBean {
    public boolean isRedirectInfo = false;

    public SubscribeBean(boolean isRedirectInfo) {
        this.isRedirectInfo = isRedirectInfo;
    }
}
